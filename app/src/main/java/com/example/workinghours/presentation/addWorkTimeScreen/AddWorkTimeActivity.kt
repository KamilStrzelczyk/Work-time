package com.example.workinghours.presentation.addWorkTimeScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddWorkTimeActivity : ComponentActivity(

) {
    private val addWorkTimeViewModel: AddWorkTimeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                AddWorkTimeScreen(viewModel = addWorkTimeViewModel)
            }
        }
    }
}