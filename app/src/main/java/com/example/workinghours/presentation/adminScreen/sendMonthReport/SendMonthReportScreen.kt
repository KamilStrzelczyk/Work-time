package com.example.workinghours.presentation.adminScreen.sendMonthReport

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.workinghours.ui.MonthPickerGridOption
import com.example.workinghours.ui.MonthPickerGridOptionType
import com.example.workinghours.ui.MonthPickerGridOptionType.*
import org.joda.time.LocalDate

@Composable
fun SendMonthReportScreen() {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
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
                            .clickable { },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                    )

                    Text(text = "2023")

                    Icon(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { },
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                    )
                }

                Grids(
                    onItemClick = {
                        when (it) {
                            MONTH1 -> TODO()
                            MONTH2 -> TODO()
                            MONTH3 -> TODO()
                            MONTH4 -> TODO()
                            MONTH5 -> TODO()
                            MONTH6 -> TODO()
                            MONTH7 -> TODO()
                            MONTH8 -> TODO()
                            MONTH9 -> TODO()
                            MONTH10 -> TODO()
                            MONTH11 -> TODO()
                            MONTH12 -> TODO()
                        }
                    })
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Wyślij miesięczny raport")
                }
            }
        }
    }
}

@Composable
fun Grids(
    onItemClick: (MonthPickerGridOptionType) -> Unit,
) {
    val date = LocalDate()

    val data = listOf(
        MonthPickerGridOption(
            "sty",
            1,
            MONTH1
        ),
        MonthPickerGridOption(
            "lut",
            2,
            MONTH2
        ),
        MonthPickerGridOption(
            "mar",
            3,
            MONTH3
        ),
        MonthPickerGridOption(
            "kwi",
            4,
            MONTH4
        ),
        MonthPickerGridOption(
            "maj",
            5,
            MONTH5
        ),
        MonthPickerGridOption(
            "cze",
            6,
            MONTH6
        ),
        MonthPickerGridOption(
            "lip",
            7,
            MONTH7
        ),
        MonthPickerGridOption(
            "sie",
            8,
            MONTH8
        ),
        MonthPickerGridOption(
            "wrz",
            9,
            MONTH9
        ),
        MonthPickerGridOption(
            "paź",
            10,
            MONTH10
        ),
        MonthPickerGridOption(
            "lis",
            11,
            MONTH11
        ),
        MonthPickerGridOption(
            "gru",
            12,
            MONTH12
        ),
    )
    val isActiveColor = data.firstOrNull() {
        (date.monthOfYear == it.numberOfMonth)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(data) { item ->
            Card(
                onItemClick = onItemClick,
                monthPickerGridOption = item,
                isActiveColor = item == isActiveColor
            )
        }
    }
}

@Composable
fun Card(
    onItemClick: (MonthPickerGridOptionType) -> Unit,
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
                .clickable { onItemClick(monthPickerGridOption.type) }
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = monthPickerGridOption.text)
        }
    }
}