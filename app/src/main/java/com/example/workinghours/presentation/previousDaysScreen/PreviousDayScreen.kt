package com.example.workinghours.presentation.previousDaysScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.TextUnit
import org.w3c.dom.Text

@Composable

fun PreviousDaysScreen(
    viewModel: PreviousDaysViewModel,
) {
    Box() {
        Text(text = viewModel.state.value.userDate.toString())
    }
}