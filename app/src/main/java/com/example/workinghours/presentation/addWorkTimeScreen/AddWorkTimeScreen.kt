package com.example.workinghours.presentation.addWorkTimeScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentManager
import com.example.workinghours.R
import com.example.workinghours.presentation.listOfUsersScreen.ListOfUsersActivity
import com.google.android.material.datepicker.MaterialDatePicker

@Composable
fun AddWorkTimeScreen(
    viewModel: AddWorkTimeViewModel,
    fragment: FragmentManager,
) {
    val pattern = "MM/dd/yyyy HH:mm"
    val state = viewModel.state.collectAsState().value

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = state.run { currentDataAndTime!!.toString(pattern) },
            )
            Icon(
                modifier = Modifier
                    .clickable { viewModel.onTopAppBarMoreActionClicked() }
                    .align(Alignment.CenterEnd),
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
            )
        }

        Divider()

        ClockComponent(
            text = stringResource(id = R.string.StartWorkingTime),
            clock = state.startWorkClock,
            updateClock = viewModel::updateStartWorkClock
        )
        ClockComponent(
            text = stringResource(id = R.string.EndWorkTime),
            clock = state.endWorkClock,
            updateClock = viewModel::updateEndWorkClock
        )
        ClockComponent(
            text = stringResource(id = R.string.Hygiene),
            clock = state.hygieneClock,
            updateClock = viewModel::updateHygieneWorkClock
        )

        Box(
            modifier = Modifier.padding(20.dp),
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onFirstSaveButtonClicked() }) {
                Text(
                    text = stringResource(id = R.string.Save),
                    color = Color.White,
                )
                SaveTimeDialog(
                    viewModel = viewModel,
                    state = state,
                )
            }
        }
    }
    moreDialogAddDayOff(
        showUserActionsDialog = state.showUserActionsDialog,
        typeDateOfSelector = state.showTypeDateOfSelector,
        onDismissTopAppBarMoreDialog = { viewModel.onDismissTopAppBarMoreDialogClicked() },
        onConfirmRangeOfDateClicked = viewModel::onConfirmRangeOfDateClicked,
        onShowCalendar = viewModel::showCalendar,
        fragment = fragment,
        onTypeDayOfClicked = viewModel::onTypeDayOfClicked,
        showCalendar = state.showCalendar,
        list = state.typesOfDaysOff,
        onDismissDayOfDropDownMenu = viewModel::onDismissDayOfDropDownMenu,
        showTypeDateOfSelector = viewModel::showTypeDateOfSelector,
    )
}

@Composable
private fun ClockComponent(
    text: String,
    clock: AddWorkTimeViewModel.Clock,
    updateClock: (AddWorkTimeViewModel.Clock) -> Unit,
) {
    Box(
        modifier = Modifier.padding(20.dp),
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = text)

            Spacer(modifier = Modifier.height(15.dp))

            Row(
                modifier = Modifier
                    .border(1.dp, Color.LightGray)
                    .padding(20.dp)
                    .padding(start = 60.dp)
                    .padding(end = 40.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { updateClock(clock.showClockHour()) }) {
                    Row {

                        Text(clock.setHour.toString())

                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                        )

                        HourClock(
                            clockData = clock,
                            updateClock = updateClock,
                        )
                    }
                }

                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.ClockColon),
                )

                Box(modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { updateClock(clock.showClockMinute()) }) {
                    Row {
                        Text(clock.setMinute.toString())
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = null,
                        )
                        MinuteClock(
                            clockData = clock,
                            updateClock = updateClock,
                        )
                    }
                }
            }
        }
    }
    Divider()
}

@Composable
private fun dropDownMenu(
    dismissClock: () -> Unit,
    onItemSelected: (String) -> Unit,
    options: List<String>,
) {
    DropdownMenu(
        expanded = true, onDismissRequest = dismissClock
    ) {
        options.forEach {
            DropdownMenuItem(onClick = { onItemSelected(it) }) {
                Text(text = it)
            }
        }
    }
}

@Composable
private fun HourClock(
    clockData: AddWorkTimeViewModel.Clock,
    updateClock: (AddWorkTimeViewModel.Clock) -> Unit,
) {
    if (clockData.showClock) {
        dropDownMenu(
            dismissClock = { updateClock(clockData.hideClock()) },
            onItemSelected = { updateClock(clockData.onHourSelected(it.toInt())) },
            options = clockData.timeHourScope,
        )
    }
}

@Composable
private fun MinuteClock(
    clockData: AddWorkTimeViewModel.Clock,
    updateClock: (AddWorkTimeViewModel.Clock) -> Unit,
) {
    if (clockData.showMinuteClock) {
        dropDownMenu(
            dismissClock = { updateClock(clockData.hideClock()) },
            onItemSelected = { updateClock(clockData.onMinuteSelected(it.toInt())) },
            options = clockData.timeMinuteScope,
        )
    }
}

@Composable
private fun SaveTimeDialog(
    viewModel: AddWorkTimeViewModel,
    state: AddWorkTimeViewModel.ViewModelState,
) {
    val context = LocalContext.current
    val patternForCalculateTime = "HH:mm"
    if (state.showSaveDialog) Dialog(onDismissRequest = { viewModel.onDismissSaveDialog() }) {
        Surface(
            modifier = Modifier.clip(RoundedCornerShape(16.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = stringResource(id = R.string.YourTimeJob))
                    state.workTime?.amountWorkTime?.let {
                        Text(it.toString(patternForCalculateTime))
                    }
                    Button(onClick = {
                        viewModel.onDialogSaveButtonClicked()
                        context.startActivity(
                            Intent(
                                context, ListOfUsersActivity::class.java
                            )
                        )
                    }) {
                        Text(text = "OK")
                    }
                }
            }
        }
    }
}

@Composable
fun moreDialogAddDayOff(
    showUserActionsDialog: Boolean,
    typeDateOfSelector: Boolean,
    onDismissTopAppBarMoreDialog: () -> Unit,
    onConfirmRangeOfDateClicked: (Long, Long) -> Unit,
    onShowCalendar: () -> Unit,
    fragment: FragmentManager,
    showCalendar: Boolean,
    showTypeDateOfSelector: () -> Unit,
    onDismissDayOfDropDownMenu: () -> Unit,
    onTypeDayOfClicked: (String) -> Unit,
    list: List<String>,
) {
    if (showUserActionsDialog)

        Dialog(
            onDismissRequest = { onDismissTopAppBarMoreDialog() }
        ) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Box(
                    modifier = Modifier
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Surface(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clickable { showTypeDateOfSelector() },
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        modifier = Modifier.padding(5.dp),
                                        text = "Wybierz wolne",
                                    )
                                    Icon(
                                        imageVector = Icons.Filled.ArrowDropDown,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.padding(15.dp))

                        Surface(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(112.dp)
                                    .fillMaxWidth()
                                    .clickable {
                                        onShowCalendar()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(5.dp),
                                        text = "Wybierz datę",
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.calendar_add_image),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        TypeDateOfSelector(
                            typeDateOfSelector = typeDateOfSelector,
                            onDismissDayOfDropDownMenu = onDismissDayOfDropDownMenu,
                            onTypeDayOfClicked = onTypeDayOfClicked,
                            list = list,
                        )
                        AndroidCalendar(
                            fragmentManager = fragment,
                            onConfirmRangeOfDateClicked = onConfirmRangeOfDateClicked,
                            showCalendar = showCalendar,
                        )
                    }
                }
            }
        }
}

@Composable
private fun TypeDateOfSelector(
    typeDateOfSelector: Boolean,
    onDismissDayOfDropDownMenu: () -> Unit,
    onTypeDayOfClicked: (String) -> Unit,
    list: List<String>,
) {
    if (typeDateOfSelector) {
        dropDownMenu(
            dismissClock = onDismissDayOfDropDownMenu,
            onItemSelected = onTypeDayOfClicked,
            options = list,
        )
    }
}

@Composable
private fun AndroidCalendar(
    fragmentManager: FragmentManager,
    onConfirmRangeOfDateClicked: (Long, Long) -> Unit,
    showCalendar: Boolean,
) {
    if (showCalendar) {
        val datePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText("Wybierz okres")
            .build()
        datePicker.show(fragmentManager, "calendar")
        datePicker.addOnPositiveButtonClickListener {
            val startDate = it.first
            val endDate = it.second
            onConfirmRangeOfDateClicked(startDate, endDate)
        }
    }
}

//@Preview
//@Composable
//fun moreDialog_Preview() {
//    val fragmentManager = (LocalContext.current as AppCompatActivity).supportFragmentManager
//    moreDialog(
//        showUserActionsDialog = true,
//        onDismissTopAppBarMoreDialog = {},
//        onConfirmRangeOfDateClicked = { _, _ -> },
//        onShowCalendar = {},
//        fragment = {},
//        showCalendar = false,
//    )
//}

@Preview(showBackground = true)
@Composable
fun TypeDateOfSelector_Preview() {
    TypeDateOfSelector(
        onDismissDayOfDropDownMenu = {},
        typeDateOfSelector = false,
        onTypeDayOfClicked = {},
        list = mutableListOf("Dupa", "Dupa", "Dupa", "Dupa")
    )
}

@Preview(showBackground = true)
@Composable
fun ClockComponent_Preview() {
    ClockComponent(
        text = "Zegarek",
        clock = AddWorkTimeViewModel.Clock(
            showMinuteClock = false,
            showClock = false,
            timeHourScope = listOf(
                "0",
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10"
            ),
            timeMinuteScope = listOf(
                "00",
                "10",
                "20",
                "30",
                "40",
                "50",
                "60",
            ),
            setHour = 0,
            setMinute = 0,
        ),
        updateClock = {})
}