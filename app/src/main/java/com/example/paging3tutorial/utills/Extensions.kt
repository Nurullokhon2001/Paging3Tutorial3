package com.example.paging3tutorial.utills

import androidx.navigation.NavController
import androidx.navigation.NavDirections

object Extensions {
    fun NavController.sadeNavigate(directions: NavDirections){
        currentDestination?.getAction(directions.actionId)?.run{
            navigate(directions)
        }
    }
}