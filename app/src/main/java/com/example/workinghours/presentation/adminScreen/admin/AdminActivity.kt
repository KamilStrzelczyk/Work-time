package com.example.workinghours.presentation.adminScreen.admin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : ComponentActivity(
) {
    private val adminScreenViewModel: AdminViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                AdminScreen(viewModel = adminScreenViewModel)
            }
        }
    }
}