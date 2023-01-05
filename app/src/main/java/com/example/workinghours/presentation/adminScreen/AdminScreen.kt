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
    Column(modifier = Modifier.fillMaxSize()

    ) {
        Grids(onItemClick = {
            when (it) {
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
                AdminGirdOptionType.USER_PREVIEW -> {
                    println("USER_PREVIEW")
                }
                AdminGirdOptionType.CHANGE_PASSWORD -> {
                    println("CHANGE_PASSWORD")
                }
            }
        })
    }
}

@Composable
fun Grids(
    onItemClick: (AdminGirdOptionType) -> Unit,
) {
    val data = listOf(
        AdminGridOption("Dzienny raport ",
            AdminGirdOptionType.SEND_DAILY_REPORT,
            R.drawable.calendartodayimage),
        AdminGridOption("Raport miesięczny",
            AdminGirdOptionType.SEND_MONTH_REPORT,
            R.drawable.calendar_image),
        AdminGridOption("Lista użytkowników",
            AdminGirdOptionType.USER_PREVIEW,
            R.drawable.account_icon),
        AdminGridOption("Zmień hasło",
            AdminGirdOptionType.CHANGE_PASSWORD,
            R.drawable.change_password_image),
        AdminGridOption("Dodaj użytkownika",
            AdminGirdOptionType.ADDUSER,
            R.drawable.add_user_image),
        AdminGridOption("Usuń użytkownika",
            AdminGirdOptionType.DELETE_USER,
            R.drawable.remove_user_image),
    )

    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 162.dp),
        contentPadding = PaddingValues(8.dp)) {
        items(data) { item ->
            Card(onItemClick = onItemClick, adminGridOption = item)
        }

    }
}

@Composable
fun Card(
    onItemClick: (AdminGirdOptionType) -> Unit,
    adminGridOption: AdminGridOption,
) {
    Surface(modifier = Modifier.padding(10.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)) {
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
