package com.example.workinghours.presentation.previousDaysScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workinghours.R
import com.example.workinghours.ui.MonthPickerGridOption
import org.joda.time.LocalDate

@Composable
fun PreviousDaysScreen(
    viewModel: PreviousDaysViewModel,
) {
    val state = viewModel.state.collectAsState().value
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(text = stringResource(id = R.string.DailyStatement))
                },
                actions = {
                    IconButton(onClick = { viewModel.onTopAppBarFilterClicked() }
                    ) {
                        Image(
                            painterResource(id = R.drawable.filter_image),
                            contentDescription = null
                        )
                    }
                    FilterTopAppBar(
                        showFilterTopAppBar = state.showFilterTopAppBar,
                        onDismissFilterTopAppBar = { viewModel.onDismissFilterTopAppBar() },
                        onWorkDaysClicked = { viewModel.onTopAppBarFilterWorkdaysClicked() },
                        onAllDaysClicked = { viewModel.onTopAppBarFilterAllDaysClicked() },
                        showCalendar = { viewModel.showCalendar() })
                    Calendar(
                        onDismissShowCalendar = viewModel::onDismissCalendar,
                        showCalendar = state.showCalendar,
                        onItemClick = {
                            viewModel.onOtherMonthClicked(
                                year = state.year.year,
                                month = it.numberOfMonth
                            )
                        },
                        gridList = state.gridList,
                        localDate = state.calendarDate,
                        addYear = viewModel::addYear,
                        minusYear = viewModel::minusYear,
                        year = state.year.year.toString(),
                        onShowDateClicked = viewModel::onShowDateClicked
                    )
                }
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(state.dayInCalendar) {
                DayCard(
                    workDate = it.workDate,
                    startWorkTime = it.startWorkTime,
                    endWorkTime = it.endWorkTime,
                    workAmount = it.workAmount,
                    hygieneTime = it.hygieneTime,
                    showWorkAmount = it.showWorkAmount,
                    showStartWorkTime = it.showStartWorkTime,
                    showHygieneTime = it.showHygieneTime,
                    showIfIsSunday = it.showIfIsSunday,
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
    showStartWorkTime: Boolean,
    showHygieneTime: Boolean,
    showIfIsSunday: Boolean,
    showIfIsSaturday: Boolean,
) {
    val background =
        if (showIfIsSunday) Color(0xFF131B44) else if (showIfIsSaturday) Color(0xFF131B44) else Color.White
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
            Text(
                text = workDate,
            )
            if (showWorkAmount) {
                Text(text = "${stringResource(id = R.string.WorkTime)} $workAmount")
            }
            if (showStartWorkTime)
                Text(text = "${stringResource(id = R.string.WorkAtTime)} $startWorkTime - $endWorkTime")
            if (showHygieneTime)
                Text(text = "${stringResource(id = R.string.PeriodOfHygiene)} $hygieneTime")
        }
    }
}

@Composable
private fun FilterTopAppBar(
    showFilterTopAppBar: Boolean,
    onDismissFilterTopAppBar: () -> Unit,
    onWorkDaysClicked: () -> Unit,
    onAllDaysClicked: () -> Unit,
    showCalendar: () -> Unit,
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
                Text(text = stringResource(id = R.string.WorkedDays))
            }
            DropdownMenuItem(
                onClick = {
                    onDismissFilterTopAppBar()
                    onAllDaysClicked()
                }
            ) {
                Text(text = stringResource(id = R.string.AllDays))
            }
            DropdownMenuItem(
                onClick = {
                    onDismissFilterTopAppBar()
                    showCalendar()
                }
            ) {
                Text(text = stringResource(id = R.string.PreviousMonth))
            }
        }
}

@Composable
private fun Calendar(
    onShowDateClicked: () -> Unit,
    onDismissShowCalendar: () -> Unit,
    showCalendar: Boolean,
    onItemClick: (MonthPickerGridOption) -> Unit,
    gridList: List<MonthPickerGridOption>,
    localDate: LocalDate,
    addYear: () -> Unit,
    minusYear: () -> Unit,
    year: String,
) {
    if (showCalendar)
        Dialog(onDismissRequest = { onDismissShowCalendar() }
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
                    Column {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Icon(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { minusYear() },
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                            )

                            Text(text = year)

                            Icon(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { addYear() },
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                            )
                        }
                        Grids(onItemClick = onItemClick, gridList = gridList, localDate = localDate)

                        Button(modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onShowDateClicked()
                            }
                        ) {
                            Text(text = stringResource(id = R.string.ShowData))
                        }
                    }
                }
            }
        }
}

@Composable
private fun Grids(
    onItemClick: (MonthPickerGridOption) -> Unit,
    gridList: List<MonthPickerGridOption>,
    localDate: LocalDate,
) {
    val isActiveColor = gridList.firstOrNull() {
        (localDate.monthOfYear == it.numberOfMonth)
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(gridList) { item ->
            MonthCard(
                onItemClick = onItemClick,
                monthPickerGridOption = item,
                isActiveColor = item == isActiveColor
            )
        }
    }
}

@Composable
private fun MonthCard(
    onItemClick: (MonthPickerGridOption) -> Unit,
    monthPickerGridOption: MonthPickerGridOption,
    isActiveColor: Boolean,
) {
    val gridColor =
        if (isActiveColor) Color(0xFF131B44) else Color.White
    Surface(
        modifier = Modifier.padding(10.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = gridColor,
    ) {
        Column(
            modifier = Modifier
                .clickable { onItemClick(monthPickerGridOption) }
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = monthPickerGridOption.text)
        }
    }
}
