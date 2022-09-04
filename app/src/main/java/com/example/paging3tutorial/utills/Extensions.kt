package com.example.paging3tutorial.utills

import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.NavDirections

object Extensions {
    fun NavController.sadeNavigate(directions: NavDirections) {
        currentDestination?.getAction(directions.actionId)?.run {
            navigate(directions)
        }
    }
}

typealias OnQueryListener = (text: String) -> Unit

fun SearchView.setOnQueryListener(onQueryListener: OnQueryListener) {
    isSubmitButtonEnabled = false
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?) = false
        override fun onQueryTextChange(newText: String?): Boolean {
            val text = newText ?: return false
            onQueryListener(text)
            return true
        }
    })
}