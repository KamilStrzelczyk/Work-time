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
import com.example.workinghours.presentation.AddWorkTimeScreen.AddWorkTimeScreen
import com.example.workinghours.presentation.AddWorkTimeScreen.AddWorkTimeViewModel
import com.example.workinghours.presentation.ListOfUsersScreen.ListOfUsersScreen
import com.example.workinghours.presentation.ListOfUsersScreen.ListOfUsersViewModel
import com.example.workinghours.presentation.PreviousDaysScreen.PreviousDaysScreen
import com.example.workinghours.presentation.PreviousDaysScreen.PreviousDaysViewModel
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val listOfUsersScreenViewModel: ListOfUsersViewModel by viewModels()
    private val addWorkTimeViewModel: AddWorkTimeViewModel by viewModels()
    private val previousDaysViewModel: PreviousDaysViewModel by viewModels()
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

                    }
                }
            }
        }
    }
}