package com.example.workinghours.presentation.adminScreen.admin


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workinghours.R
import com.example.workinghours.presentation.adminScreen.deleteUser.DeleteUserActivity
import com.example.workinghours.presentation.adminScreen.sendDailyReport.SendDailyReportActivity
import com.example.workinghours.presentation.listOfUsersScreen.ListOfUsersActivity
import com.example.workinghours.ui.AdminGirdOptionType
import com.example.workinghours.ui.AdminGridOption

@Composable
fun AdminScreen(
    viewModel: AdminViewModel,
) {
    val state = viewModel.state.value
    Column(modifier = Modifier.fillMaxSize()

    ) {
        val context = LocalContext.current
        Grids(onItemClick = {
            when (it) {
                AdminGirdOptionType.SEND_DAILY_REPORT -> {
                    context.startActivity(Intent(context, SendDailyReportActivity::class.java))
                }
                AdminGirdOptionType.SEND_MONTH_REPORT -> {
                    println("SEND_MONTH_REPORT")
                }
                AdminGirdOptionType.DELETE_USER -> {
                    context.startActivity(Intent(context, DeleteUserActivity::class.java))
                }
                AdminGirdOptionType.ADDUSER -> {
                    viewModel.showAddUserActionsDialog()
                }
                AdminGirdOptionType.USER_PREVIEW -> {
                    context.startActivity(Intent(context, ListOfUsersActivity::class.java))
                }
                AdminGirdOptionType.CHANGE_PASSWORD -> {
                    viewModel.showChangeAdminPasswordDialog()
                }
            }
        })
    }
    AddUserDialog(
        showAddUserDialog = viewModel.state.value.showAddUserActionsDialog,
        state = state,
        onUserNameChange = { viewModel.onUserNameChange(it) },
        onUserPasswordChange = { viewModel.onUserPasswordChange(it) },
        onSaveUserClicked = { viewModel.onSaveUserClicked() },
        onDismissAddUserActionsDialog = { viewModel.onDismissAddUserActionsDialog() }
    )
    AdminChangePasswordDialog(password = viewModel.state.value.adminPassword,
        showAdminChangePasswordDialog = viewModel.state.value.showAdminChangePasswordDialog,
        onDismissAdminChangePasswordDialog = { viewModel.onDismissAdminChangePasswordDialog() },
        onOKClicked = { viewModel.onOKClicked() },
        onNewPasswordChange = { viewModel.onNewPasswordChange(it) })
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

@Composable
private fun AddUserDialog(
    showAddUserDialog: Boolean,
    state: AdminViewModel.ViewModelState,
    onUserNameChange: (String) -> Unit,
    onUserPasswordChange: (String) -> Unit,
    onSaveUserClicked: () -> Unit,
    onDismissAddUserActionsDialog: () -> Unit,
) {
    if (showAddUserDialog)
        Dialog(onDismissRequest = { onDismissAddUserActionsDialog() }) {
            Surface(modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
            ) {
                Box(
                    modifier = Modifier.padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        OutlinedTextField(
                            value = state.userName,
                            onValueChange = { onUserNameChange(it) },
                            label = { Text(text = "Nazwa użytkownika") })
                        OutlinedTextField(
                            value = state.userPassword,
                            onValueChange = { onUserPasswordChange(it) },
                            label = { Text(text = "Nazwa Hasło") })
                        Button(onClick = { onSaveUserClicked() }) {
                            Text(text = "Zapisz użytkownika")
                        }
                    }

                }
            }
        }
}

@Composable
private fun AdminChangePasswordDialog(
    password: String,
    showAdminChangePasswordDialog: Boolean,
    onDismissAdminChangePasswordDialog: () -> Unit,
    onOKClicked: () -> Unit,
    onNewPasswordChange: (String) -> Unit,
) {
    val context = LocalContext.current
    if (showAdminChangePasswordDialog)
        Dialog(onDismissRequest = { onDismissAdminChangePasswordDialog() }) {
            Surface(modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
            ) {
                Box(
                    modifier = Modifier.padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { onNewPasswordChange(it) },
                            visualTransformation = PasswordVisualTransformation(),
                            label = { Text(text = "Podaj hasło") })
                        OutlinedTextField(
                            value = password,
                            onValueChange = { onNewPasswordChange(it) },
                            visualTransformation = PasswordVisualTransformation(),
                            label = { Text(text = "Podaj nowe hasło") })
                        Button(
                            onClick = {
                                onOKClicked()
                                context.startActivity(Intent(context, AdminActivity::class.java))
                            }) {
                            Text(text = "Ok")
                        }
                    }

                }
            }
        }
}

//@Preview(showBackground = true)
//@Composable
//fun AdminScreenPreview() = AdminScreen(viewModel = AdminViewModel(),
//    navController = NavController(context = LocalContext.current))

