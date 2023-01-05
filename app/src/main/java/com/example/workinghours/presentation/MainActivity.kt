package com.example.workinghours.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workinghours.presentation.addWorkTimeScreen.AddWorkTimeScreen
import com.example.workinghours.presentation.addWorkTimeScreen.AddWorkTimeViewModel
import com.example.workinghours.presentation.adminScreen.AdminScreen
import com.example.workinghours.presentation.adminScreen.AdminViewModel
import com.example.workinghours.presentation.listOfUsersScreen.ListOfUsersScreen
import com.example.workinghours.presentation.listOfUsersScreen.ListOfUsersViewModel
import com.example.workinghours.presentation.previousDaysScreen.PreviousDaysScreen
import com.example.workinghours.presentation.previousDaysScreen.PreviousDaysViewModel
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val listOfUsersScreenViewModel: ListOfUsersViewModel by viewModels()
    private val addWorkTimeViewModel: AddWorkTimeViewModel by viewModels()
    private val previousDaysViewModel: PreviousDaysViewModel by viewModels()
    private val adminViewModel: AdminViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = Screen.ListOfUsersScreen.route) {
                        composable(
                            route = Screen.ListOfUsersScreen.route
                        ) {
                            ListOfUsersScreen(viewModel = listOfUsersScreenViewModel, navController)
                        }
                        composable(
                            route = Screen.AddWorkTimeScreen.route
                        ) {
                            AddWorkTimeScreen(viewModel = addWorkTimeViewModel, navController)
                        }
                        composable(
                            route = Screen.PreviousDayScreen.route
                        ) {
                            PreviousDaysScreen(viewModel = previousDaysViewModel)
                        }
                        composable(
                            route = Screen.AdminScreen.route
                        ) {
                            AdminScreen(viewModel = adminViewModel)
                        }

                    }
                }
            }
        }
    }
}