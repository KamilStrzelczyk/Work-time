package com.example.workinghours.presentation.adminScreen.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.workinghours.presentation.listOfUsersScreen.ListOfUsersViewModel
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : ComponentActivity(
) {
    private val adminScreenViewModel: AdminViewModel by viewModels()
    private val listOfUsersViewModel: ListOfUsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                AdminScreen(viewModel = adminScreenViewModel)
            }
        }
    }
}