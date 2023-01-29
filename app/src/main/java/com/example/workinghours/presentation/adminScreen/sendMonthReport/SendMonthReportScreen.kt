package com.example.workinghours.presentation.adminScreen.sendMonthReport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.workinghours.R
import com.example.workinghours.ui.MonthPickerGridOption
import org.joda.time.LocalDate
import java.io.File

@Composable
fun SendMonthReportScreen(
    viewModel: SendMonthReportViewModel,
    onMonthReportGenerated: (File?) -> Unit,
) {
    val state = viewModel.state.collectAsState().value
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(text = stringResource(id = R.string.MonthReport))
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
            )
            {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Icon(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.minusYear() },
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )

                        Text(text = state.yearOnScreen.year.toString())

                        Icon(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { viewModel.addYear() },
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                        )
                    }
                    Grids(
                        onItemClick = {
                            viewModel.onMonthClicked(
                                year = state.yearOnScreen.year,
                                monthOfYear = it.numberOfMonth
                            )
                        },
                        gridList = state.gridList,
                        localDate = state.date,
                    )
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            viewModel.onSendMonthReportClicked {
                                onMonthReportGenerated(it)
                            }
                        }
                    ) {
                        Text(text = stringResource(id = R.string.SendMonthReport))
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
            Card(
                onItemClick = onItemClick,
                monthPickerGridOption = item,
                isActiveColor = item == isActiveColor
            )
        }
    }
}

@Composable
private fun Card(
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