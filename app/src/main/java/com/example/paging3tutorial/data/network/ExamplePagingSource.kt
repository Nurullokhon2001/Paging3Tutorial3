package com.example.paging3tutorial.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paging3tutorial.data.network.dto.ResultDto
import com.example.paging3tutorial.domain.use_case.GetCharactersUseCase
import retrofit2.HttpException
import javax.inject.Inject

class ExamplePagingSource @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : PagingSource<Int, ResultDto>() {
    override fun getRefreshKey(state: PagingState<Int, ResultDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, ResultDto> {
        return try {
            val pageNumber = params.key ?: 1
            val response = getCharactersUseCase.invoke(pageNumber)
            val nextPageNumber = if (response.infoDto.next.isNullOrBlank()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            LoadResult.Page(
                data = response.resultDtos,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
