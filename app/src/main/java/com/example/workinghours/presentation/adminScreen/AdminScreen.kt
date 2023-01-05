package com.example.workinghours.presentation.adminScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.workinghours.R
import com.example.workinghours.ui.AdminGirdOptionType
import com.example.workinghours.ui.AdminGridOption

@Composable
fun AdminScreen(viewModel: AdminViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()

    ) {
        Grids(
            onItemClick = {
                when(it){
                    AdminGirdOptionType.SEND_DAILY_REPORT -> {
                        println("SEND_DAILY_REPORT")
                    }
                    AdminGirdOptionType.SEND_MONTH_REPORT -> {
                        println("SEND_MONTH_REPORT")
                    }
                    AdminGirdOptionType.DELETE_USER -> {
                        println("DELETE_USER")
                    }
                    AdminGirdOptionType.ADDUSER -> {
                        println("ADDUSER")
                    }
                    AdminGirdOptionType.USER_PREVIEW ->{
                        println("USER_PREVIEW")
                    }
                    AdminGirdOptionType.CHANGE_PASSWORD -> {
                        println("CHANGE_PASSWORD")
                    }
                }
            }
        )
    }
}

@Composable
fun Grids(
    onItemClick: (AdminGirdOptionType) -> Unit,
) {
    val data = listOf(
        AdminGridOption("dsas", AdminGirdOptionType.ADDUSER, R.drawable.accounticon),
        AdminGridOption("qe", AdminGirdOptionType.CHANGE_PASSWORD, R.drawable.calendaraddimage),
        AdminGridOption("gffvd",
            AdminGirdOptionType.DELETE_USER,
            R.drawable.ic_launcher_background))
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 162.dp),
        contentPadding = PaddingValues(8.dp)) {
        items(data) { item ->
            Card(onItemClick = onItemClick,
                adminGridOption = item)
        }

    }
}

@Composable
fun Card(
    onItemClick: (AdminGirdOptionType) -> Unit,
    adminGridOption: AdminGridOption,
) {
    Surface(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier
            .height(112.dp)
            .clickable { onItemClick(adminGridOption.type) },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(text = adminGridOption.text)
            Image(painter = painterResource(id = adminGridOption.imageResId),
                contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdminScreenPreview() = AdminScreen(viewModel = AdminViewModel())
