package com.example.workinghours.presentation.AddWorkTimeScreen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.workinghours.presentation.Screen
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration.Companion.minutes

@Composable

fun AddWorkTimeScreen(
    viewModel: AddWorkTimeViewModel,
    navController: NavController,
) {
    val dataTime = LocalDate.now()
    val datatimetiem = LocalTime.now()
    val state = viewModel.state.value

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.padding(20.dp)) {
            Text("${ dataTime.toString() }  ${datatimetiem.hourOfDay} : ${datatimetiem.minuteOfHour}")
        }

        Divider()

        Box(modifier = Modifier
            .padding(20.dp)) {
            Column(modifier = Modifier,
                horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Rozpoczęcie pracy")

                Spacer(modifier = Modifier.height(15.dp))

                Row(modifier = Modifier
                    .border(1.dp, Color.LightGray)
                    .padding(20.dp)
                    .padding(start = 60.dp)
                    .padding(end = 40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clickable { viewModel.onDropArrowStartHourClicked() }) {
                        Row {
                            Text(state.startHour.toString())
                            Icon(imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null)
                            StartClockBoxHour(state = state, viewModel = viewModel)
                        }
                    }

                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = ":")

                    Box(modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clickable { viewModel.onDropArrowStartMinuteClicked() }) {
                        Row {
                            Text(state.startMinute.toString())
                            Icon(imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null)
                            StartClockBoxMinute(state = state, viewModel = viewModel)
                        }
                    }
                }
            }
        }
        Divider()

        Box(modifier = Modifier
            .padding(20.dp))
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Zakończenie pracy")

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .border(1.dp, Color.LightGray)
                        .padding(20.dp)
                        .padding(start = 60.dp)
                        .padding(end = 40.dp),
                ) {
                    Box(modifier = Modifier
                        .clickable { viewModel.onDropArrowEndHourClicked() }
                        .weight(1.1f)
                        .fillMaxWidth()) {
                        Row {
                            Text(state.endHour.toString())
                            Icon(imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null)
                            EndClockBoxHour(state = state, viewModel = viewModel)
                        }
                    }

                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = ":")

                    Box(modifier = Modifier
                        .clickable { viewModel.onDropArrowEndMinuteClicked() }
                        .weight(1f)
                        .fillMaxWidth()) {
                        Row {
                            Text(state.endMinute.toString())
                            Icon(imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null)
                            EndClockBoxMinute(state = state, viewModel = viewModel)
                        }
                    }
                }
            }
        }

        Divider()

        Box(modifier = Modifier
            .padding(20.dp))
        {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text(text = "Higieny")

                Spacer(modifier = Modifier.height(15.dp))

                Row(
                    modifier = Modifier
                        .border(1.dp, Color.LightGray)
                        .padding(20.dp)
                        .padding(start = 60.dp)
                        .padding(end = 40.dp),
                ) {
                    Box(modifier = Modifier
                        .clickable { viewModel.onDropArrowSecondHourClicked() }
                        .weight(1.1f)
                        .fillMaxWidth()) {
                        Row {
                            Text(state.secondHour.toString())
                            Icon(imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null)
                            SecondClockBoxHour(state = state, viewModel = viewModel)
                        }
                    }

                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = ":")

                    Box(modifier = Modifier
                        .clickable { viewModel.onDropArrowSecondMinuteClicked() }
                        .weight(1f)
                        .fillMaxWidth()) {
                        Row {
                            Text(state.secondMinute.toString())
                            Icon(imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null)
                            SecondClockBoxMinute(state = state, viewModel = viewModel)
                        }
                    }
                }
            }
        }

        Divider()

        Box(modifier = Modifier
            .padding(20.dp))
        {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { viewModel.onButtonClicked() }) {
                Text(text = "Zapisz", color = Color.White)
                SaveTimeDialog(viewModel = viewModel, state = state, navController = navController)
            }
        }
    }
}

@Composable
fun StartClockBoxHour(state: AddWorkTimeViewModel.ViewModelState, viewModel: AddWorkTimeViewModel) {
    if (state.showStartClockHour) {
        DropdownMenu(expanded = true, onDismissRequest = { state.startHour }) {
            state.clockHour.forEach {
                DropdownMenuItem(onClick = { viewModel.onStartHourClicked(it) }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun StartClockBoxMinute(
    state: AddWorkTimeViewModel.ViewModelState,
    viewModel: AddWorkTimeViewModel,
) {
    if (state.showStartClockMinute) {
        DropdownMenu(expanded = true, onDismissRequest = { state.startMinute }) {
            state.clockMinute.forEach {
                DropdownMenuItem(onClick = { viewModel.onStartMinuteClicked(it) }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun EndClockBoxHour(state: AddWorkTimeViewModel.ViewModelState, viewModel: AddWorkTimeViewModel) {
    if (state.showEndClockHour) {
        DropdownMenu(expanded = true, onDismissRequest = { state.endHour }) {
            state.clockHour.forEach {
                DropdownMenuItem(onClick = { viewModel.onEndHourClicked(it) }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun EndClockBoxMinute(state: AddWorkTimeViewModel.ViewModelState, viewModel: AddWorkTimeViewModel) {
    if (state.showEndClockMinute) {
        DropdownMenu(expanded = true, onDismissRequest = { state.endMinute }) {
            state.clockMinute.forEach {
                DropdownMenuItem(onClick = { viewModel.onEndMinuteClicked(it) }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun SecondClockBoxHour(
    state: AddWorkTimeViewModel.ViewModelState,
    viewModel: AddWorkTimeViewModel,
) {
    if (state.showSecondClockHour) {
        DropdownMenu(expanded = true, onDismissRequest = { state.secondHour }) {
            state.secondClockHour.forEach {
                DropdownMenuItem(onClick = { viewModel.onSecondHourClicked(it) }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun SecondClockBoxMinute(
    state: AddWorkTimeViewModel.ViewModelState,
    viewModel: AddWorkTimeViewModel,
) {
    if (state.showSecondClockMinute) {
        DropdownMenu(expanded = true, onDismissRequest = { state.secondMinute }) {
            state.secondClockMinute.forEach {
                DropdownMenuItem(onClick = { viewModel.onSecondMinuteClicked(it) }) {
                    Text(text = it)
                }
            }
        }
    }
}

@Composable
fun SaveTimeDialog(
    viewModel: AddWorkTimeViewModel,
    state: AddWorkTimeViewModel.ViewModelState,
    navController: NavController,
) {
    if (state.showSaveDialog)
        Dialog(onDismissRequest = { viewModel.onDismissSaveDialog() }) {
            Surface(modifier = Modifier
                .clip(RoundedCornerShape(16.dp))) {
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                    contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Twoj czas pracy")
                        Text("${state.workTime?.hourOfDay} : GODZIN ${state.workTime?.minuteOfHour} : MINUT")
                        Button(onClick = { navController.navigate(Screen.ListOfUsersScreen.route) }) {
                            Text(text = "OK")
                        }
                    }
                }
            }
        }
}


