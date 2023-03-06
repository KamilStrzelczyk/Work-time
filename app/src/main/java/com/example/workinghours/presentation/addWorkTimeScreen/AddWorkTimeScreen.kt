package com.example.workinghours.presentation.addWorkTimeScreen

import android.app.DatePickerDialog
import android.content.Intent
import android.view.ContextThemeWrapper
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.workinghours.presentation.listOfUsersScreen.ListOfUsersActivity
import com.example.workinghours.R
import com.example.workinghours.presentation.adminScreen.sendDailyReport.SendDailyReportViewModel
import com.example.workinghours.presentation.model.DataToExcelFile
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.qualifiers.ApplicationContext
import hilt_aggregated_deps._dagger_hilt_android_flags_HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule
import org.joda.time.LocalDate

@Composable

fun AddWorkTimeScreen(
    viewModel: AddWorkTimeViewModel,
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
                modifier = Modifier
                    .align(Alignment.Center),
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
            state.startWorkClock,
            viewModel::updateStartWorkClock
        )
        ClockComponent(
            text = stringResource(id = R.string.EndWorkTime),
            state.endWorkClock,
            viewModel::updateEndWorkClock
        )
        ClockComponent(
            text = stringResource(id = R.string.Hygiene),
            state.hygieneClock,
            viewModel::updateHygieneWorkClock
        )

        Box(
            modifier = Modifier.padding(20.dp),
        ) {
            Button(modifier = Modifier.fillMaxWidth(), onClick = { viewModel.onButtonClicked() }) {
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
    moreDialog(showUserActionsDialog = state.showUserActionsDialog, onDismissUserActionsDialog = {})
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
private fun ClockDropDown(
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
        ClockDropDown(
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
        ClockDropDown(
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
                        viewModel.onSaveClicked()
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
fun moreDialog(
    showUserActionsDialog: Boolean,
    onDismissUserActionsDialog: () -> Unit,
) {
    val fragment = LocalContext.current as? AppCompatActivity
    if (showUserActionsDialog)
        Dialog(
            onDismissRequest = { onDismissUserActionsDialog() }
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
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Surface(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(112.dp)
                                    .clickable {
//                                        navigateToPreviousDayScreen()
//                                        onDismissUserActionsDialog()
                                    },
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(5.dp),
                                        text = stringResource(id = R.string.PreviousDays),
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.calendar_image),
                                        contentDescription = null
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(0.1f))

                        Surface(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(112.dp)
                                    .clickable {

//                                        navigateToAddWorkTimeScreen()
//                                        onDismissUserActionsDialog()
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(5.dp),
                                        text = stringResource(id = R.string.AddNewDay),
                                    )
                                    Image(
                                        painter = painterResource(id = R.drawable.calendar_add_image),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                        if (fragment != null) {
                            AndroidCalendar(fragment.supportFragmentManager)
                        }
                    }
                }

            }
        }
}

@Composable
private fun AndroidCalendar(
    fragmentManager: FragmentManager
) {
    MaterialDatePicker.Builder.dateRangePicker().setTitleText("Wybierz okres").build().show(fragmentManager,"calendar")
}

