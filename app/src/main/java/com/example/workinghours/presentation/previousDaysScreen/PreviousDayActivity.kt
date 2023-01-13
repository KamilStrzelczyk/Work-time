package com.example.workinghours.presentation.previousDaysScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviousDayActivity : ComponentActivity(
) {
    private val previousDaysViewModel: PreviousDaysViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                PreviousDaysScreen(viewModel = previousDaysViewModel)
            }
        }
    }
}