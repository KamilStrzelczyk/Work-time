package com.example.workinghours.presentation.adminScreen.sendDailyReport

import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import org.joda.time.LocalDate
import com.example.workinghours.R
import java.io.File

@Composable
fun SendDailyReportScreen(
    viewModel: SendDailyReportViewModel,
    onSendClicked: (File?) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(text = stringResource(id = R.string.DailyReport))
                },
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally)
                {
                    AndroidCalendar(
                        viewModel = viewModel,
                        onSendClicked = {
                            viewModel.onSendReportClicked {
                                onSendClicked(it)
                            }
                        },
                    )
                }
            }
        }
    }
}

@Composable
private fun AndroidCalendar(
    viewModel: SendDailyReportViewModel,
    onSendClicked: () -> Unit,
) {
    AndroidView(
        {
            CalendarView(ContextThemeWrapper(it, R.style.CustomCalendar))
        },
        update = {
            it.setOnDateChangeListener { _, year, month, day ->
                viewModel.onDateClicked(
                    LocalDate(year, (month + 1), day)
                )
            }
        })
    Button(
        onClick = { onSendClicked() }
    ) {
        Text(text = stringResource(id = R.string.SendDailyReport))
    }
}

@Preview
@Composable
private fun SendDailyReportScreen() {

}