package com.example.workinghours.presentation

sealed class Screen(val route: String) {
    object ListOfUsersScreen : Screen("ListOfUsersScreen")
    object AddWorkTimeScreen : Screen("AddWorkTimeScreen")
    object PreviousDayScreen : Screen("PreviousDayScreen")
    object AdminScreen : Screen("AdminScreen")
}
