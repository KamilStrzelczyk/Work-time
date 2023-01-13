package com.example.workinghours.presentation.addWorkTimeScreen

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
import org.joda.time.DateTime

@Composable

fun AddWorkTimeScreen(
    viewModel: AddWorkTimeViewModel,
) {
    val dataTime = DateTime.now()
    val pattern = "MM/dd/yyyy HH:mm"
    val state = viewModel.state.value

    Column(modifier = Modifier
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.padding(20.dp)) {
            Text(dataTime.toString(pattern))
        }

        Divider()

        ClockComponent(
            text = "Rozpoczęcie pracy",
            state.startWorkClock,
            viewModel::updateStartWorkClock)
        ClockComponent(
            text = "Zakończenie pracy",
            state.endWorkClock,
            viewModel::updateEndWorkClock)
        ClockComponent(
            text = "Higieny",
            state.hygieneClock,
            viewModel::updateHygieneWorkClock)


        Box(modifier = Modifier
            .padding(20.dp))
        {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { viewModel.onButtonClicked() }) {
                Text(text = "Zapisz", color = Color.White)
                SaveTimeDialog(
                    viewModel = viewModel,
                    state = state,
//                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ClockComponent(
    text: String,
    clock: AddWorkTimeViewModel.Clock,
    updateClock: (AddWorkTimeViewModel.Clock) -> Unit,
) {

    Box(modifier = Modifier
        .padding(20.dp)) {
        Column(modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = text)

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
                    .clickable { updateClock(clock.showClockHour()) }) {
                    Row {
                        Text(clock.setHour.toString())
                        Icon(imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null)
                        HourClock(clockData = clock, updateClock = updateClock)
                    }
                }

                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = ":")

                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { updateClock(clock.showClockMinute()) }) {
                    Row {
                        Text(clock.setMinute.toString())
                        Icon(imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null)
                        MinuteClock(clockData = clock, updateClock = updateClock)
                    }
                }
            }
        }
    }
    Divider()

}

@Composable
fun ClockDropDown(
    dismissClock: () -> Unit,
    onItemSelected: (String) -> Unit,
    options: List<String>,
) {
    DropdownMenu(expanded = true, onDismissRequest = dismissClock) {
        options.forEach {
            DropdownMenuItem(onClick = { onItemSelected(it) }) {
                Text(text = it)
            }
        }
    }
}

@Composable
fun HourClock(
    clockData: AddWorkTimeViewModel.Clock,
    updateClock: (AddWorkTimeViewModel.Clock) -> Unit,
) {
    if (clockData.showClock) {
        ClockDropDown(
            dismissClock = {
                updateClock(clockData.hideClock())
            },
            onItemSelected = { updateClock(clockData.onHourSelected(it.toInt())) },
            options = clockData.timeHourScope)
//        DropdownMenu(expanded = true, onDismissRequest = {
//            updateClock(clockData.hideClock())
//        }) {
//            clockData.timeHourScope.forEach {
//                DropdownMenuItem(onClick = { updateClock(clockData.onHourSelected(it.toInt())) }) {
//                    Text(text = it)
//                }
//            }
//        }
    }
}

@Composable
fun MinuteClock(
    clockData: AddWorkTimeViewModel.Clock,
    updateClock: (AddWorkTimeViewModel.Clock) -> Unit,
) {
    if (clockData.showMinuteClock) {
        ClockDropDown(
            dismissClock = {
                updateClock(clockData.hideClock())
            },
            onItemSelected = { updateClock(clockData.onMinuteSelected(it.toInt())) },
            options = clockData.timeMinuteScope)
//        DropdownMenu(expanded = true, onDismissRequest = {
//            updateClock(clockData.hideClock())
//        }) {
//            clockData.timeMinuteScope.forEach {
//                DropdownMenuItem(onClick = { updateClock(clockData.onMinuteSelected(it.toInt())) }) {
//                    Text(text = it)
//                }
//            }
//        }
    }
}

@Composable
fun SaveTimeDialog(
    viewModel: AddWorkTimeViewModel,
    state: AddWorkTimeViewModel.ViewModelState,
) {
    val patternForCalculateTime = "HH:mm"
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
                        state.workTime?.let { Text(it.toString(patternForCalculateTime)) }
                        Button(onClick =
                        {
//                            navController.navigate(Screen.ListOfUsersScreen.route)
                        }) {
                            Text(text = "OK")
                        }
                    }
                }
            }
        }
}


