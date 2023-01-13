package com.example.workinghours.presentation.adminScreen.sendMonthReport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.workinghours.presentation.adminScreen.admin.AdminViewModel
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SendMonthReportActivity : ComponentActivity() {
    private val adminScreenViewModel: AdminViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                SendMonthReportScreen()
            }
        }
    }
}