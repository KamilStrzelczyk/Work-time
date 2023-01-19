package com.example.workinghours.presentation.previousDaysScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.workinghours.R


@Composable
fun PreviousDaysScreen(
    viewModel: PreviousDaysViewModel,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(text = "Zestawienie Dzienne")
                },
                actions = {
                    IconButton(onClick = { viewModel.onTopAppBarFilterClicked() }
                    ) {
                        Image(painterResource(id = R.drawable.filter_image),
                            contentDescription = null)
                    }
                    FilterTopAppBar(
                        showFilterTopAppBar = viewModel.state.value.showFilterTopAppBar,
                        onDismissFilterTopAppBar = { viewModel.onDismissFilterTopAppBar() },
                        onWorkDaysClicked = { viewModel.onTopAppBarFilterWorkdaysClicked() },
                        onAllDaysClicked = { viewModel.onTopAppBarFilterAllDaysClicked() })
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(viewModel.state.value.dayInCalendar) {
                DayCard(
                    workDate = it.workDate,
                    startWorkTime = it.workAmount,
                    endWorkTime = it.endWorkTime,
                    workAmount = it.workAmount,
                    hygieneTime = it.hygieneTime,
                    showWorkAmount = it.showWorkAmount,
                    showStartWorkTime = it.showStartWorkTime,
                    showHygieneTime = it.showHygieneTime,
                    visible = it.showOnlyWorkDay,
                    showIfIsSunday = it.showIfIsSunday,
                    showOnlyWorkDays = viewModel.state.value.showOnlyWorkDays,
                    showIfIsSaturday = it.showIfIsSaturday,
                )
            }
        }
    }
}

@Composable
fun DayCard(
    workDate: String,
    startWorkTime: String,
    endWorkTime: String,
    workAmount: String,
    hygieneTime: String,
    showWorkAmount: Boolean,
    visible: Boolean,
    showStartWorkTime: Boolean,
    showHygieneTime: Boolean,
    showIfIsSunday: Boolean,
    showOnlyWorkDays: Boolean,
    showIfIsSaturday: Boolean,
) {
    if (showOnlyWorkDays) {
        if (!visible) return
        val background = if (showIfIsSunday) Color(0xFF252e5c) else Color.White
        Card(
            modifier = Modifier.padding(8.dp),
            elevation = 10.dp,
            backgroundColor = background,
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = workDate)
                if (showWorkAmount) {
                    Text(text = "Czas pracy $workAmount")
                }
                if (showStartWorkTime)
                    Text(text = "Praca w godzinach $startWorkTime - $endWorkTime")
                if (showHygieneTime)
                    Text(text = "Czas trwania higien $hygieneTime")
            }
        }
    } else {
        val background =
//            if (showIfIsSunday) Color(R.color.main_color) else if (showIfIsSaturday) Color(R.color.second_color) else Color.White
        if (showIfIsSunday) Color(0xFF131b44) else if (showIfIsSaturday) Color(0xFF252e5c) else Color.White
        Card(
            modifier = Modifier.padding(8.dp),
            elevation = 10.dp,
            backgroundColor = background,
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = workDate)
                if (showWorkAmount) {
                    Text(text = "Czas pracy $workAmount")
                }
                if (showStartWorkTime)
                    Text(text = "Praca w godzinach $startWorkTime - $endWorkTime")
                if (showHygieneTime)
                    Text(text = "Czas trwania higien $hygieneTime")
            }
        }
    }
}

@Composable
private fun FilterTopAppBar(
    showFilterTopAppBar: Boolean,
    onDismissFilterTopAppBar: () -> Unit,
    onWorkDaysClicked: () -> Unit,
    onAllDaysClicked: () -> Unit,
) {
    if (showFilterTopAppBar)
        DropdownMenu(
            expanded = true,
            onDismissRequest = onDismissFilterTopAppBar
        ) {
            DropdownMenuItem(
                onClick = {
                    onDismissFilterTopAppBar()
                    onWorkDaysClicked()
                }
            ) {
                Text(text = "Przepracowane dni")

            }
            DropdownMenuItem(
                onClick = {
                    onDismissFilterTopAppBar()
                    onAllDaysClicked()
                }
            ) {
                Text(text = "Wszystkie dni")
            }
        }
}