package com.example.workinghours.presentation.adminScreen.admin

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.workinghours.R
import com.example.workinghours.presentation.adminScreen.deleteUser.DeleteUserActivity
import com.example.workinghours.presentation.adminScreen.sendDailyReport.SendDailyReportActivity
import com.example.workinghours.presentation.adminScreen.sendMonthReport.SendMonthReportActivity
import com.example.workinghours.presentation.listOfUsersScreen.ListOfUsersActivity
import com.example.workinghours.ui.AdminGridOption.AdminGirdOptionType
import com.example.workinghours.ui.AdminGridOption.AdminGridOption

@Composable
fun AdminScreen(
    viewModel: AdminViewModel,
) {
    val state = viewModel.state.value
    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        val context = LocalContext.current
        Grids(onItemClick = {
            when (it) {
                AdminGirdOptionType.SEND_DAILY_REPORT -> {
                    context.startActivity(Intent(context, SendDailyReportActivity::class.java))
                }
                AdminGirdOptionType.SEND_MONTH_REPORT -> {
                    context.startActivity(Intent(context, SendMonthReportActivity::class.java))
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
    AdminChangePasswordDialog(
        password = viewModel.state.value.adminPassword,
        newPassword = viewModel.state.value.newAdminPassword,
        showAdminChangePasswordDialog = viewModel.state.value.showAdminChangePasswordDialog,
        onDismissAdminChangePasswordDialog = { viewModel.onDismissAdminChangePasswordDialog() },
        onOKClicked = { viewModel.onOKClicked() },
        onNewPasswordChange = { viewModel.onNewPasswordChange(it) },
        onPasswordChange = { viewModel.onPasswordChange(it) },
        isError = viewModel.state.value.isError,
    )
}

@Composable
private fun Grids(
    onItemClick: (AdminGirdOptionType) -> Unit,
) {
    val dataGrids = listOf(
        AdminGridOption(
            stringResource(id = R.string.DailyReport),
            AdminGirdOptionType.SEND_DAILY_REPORT,
            R.drawable.calendartodayimage
        ),
        AdminGridOption(
            stringResource(id = R.string.MonthReport),
            AdminGirdOptionType.SEND_MONTH_REPORT,
            R.drawable.calendar_image
        ),
        AdminGridOption(
            stringResource(id = R.string.ListOfUsers),
            AdminGirdOptionType.USER_PREVIEW,
            R.drawable.account_icon
        ),
        AdminGridOption(
            stringResource(id = R.string.ChangePassword),
            AdminGirdOptionType.CHANGE_PASSWORD,
            R.drawable.change_password_image
        ),
        AdminGridOption(
            stringResource(id = R.string.AddUser),
            AdminGirdOptionType.ADDUSER,
            R.drawable.add_user_image
        ),
        AdminGridOption(
            stringResource(id = R.string.DeleteUser),
            AdminGirdOptionType.DELETE_USER,
            R.drawable.remove_user_image
        ),
    )

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 162.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(dataGrids) { item ->
            Card(
                onItemClick = onItemClick,
                adminGridOption = item
            )
        }
    }
}

@Composable
private fun Card(
    onItemClick: (AdminGirdOptionType) -> Unit,
    adminGridOption: AdminGridOption,
) {
    Surface(
        modifier = Modifier.padding(10.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .height(112.dp)
                .clickable { onItemClick(adminGridOption.type) },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = adminGridOption.text)
            Image(
                painter = painterResource(id = adminGridOption.imageResId),
                contentDescription = null
            )
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
        Dialog(
            onDismissRequest = { onDismissAddUserActionsDialog() }
        ) {
            Surface(
                modifier = Modifier
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
                            label = { Text(stringResource(id = R.string.UserName)) }
                        )
                        OutlinedTextField(
                            value = state.userPassword,
                            onValueChange = { onUserPasswordChange(it) },
                            label = { Text(text = stringResource(id = R.string.UserPassword)) }
                        )
                        Button(onClick = { onSaveUserClicked() }
                        ) {
                            Text(text = stringResource(id = R.string.SaveUser))
                        }
                    }
                }
            }
        }
}

@Composable
private fun AdminChangePasswordDialog(
    isError: Boolean,
    password: String,
    newPassword: String,
    showAdminChangePasswordDialog: Boolean,
    onDismissAdminChangePasswordDialog: () -> Unit,
    onOKClicked: () -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }
    if (showAdminChangePasswordDialog)
        Dialog(
            onDismissRequest = { onDismissAdminChangePasswordDialog() }
        ) {
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Box(
                    modifier = Modifier.padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { onPasswordChange(it) },
                            label = { Text(text = stringResource(id = R.string.GetOldPassword)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (passwordVisible) {
                                    R.drawable.visivility_image
                                } else {
                                    R.drawable.visivilityoff_image
                                }
                                IconButton(onClick = { passwordVisible = !passwordVisible }
                                ) {
                                    Image(
                                        painterResource(id = image),
                                        contentDescription = null
                                    )
                                }
                            },
                            isError = isError
                        )
                        if (isError)
                            Text(
                                text = stringResource(id = R.string.WrongPassword),
                                fontSize = 10.sp,
                                color = MaterialTheme.colors.error
                            )

                        OutlinedTextField(
                            value = newPassword,
                            onValueChange = { onNewPasswordChange(it) },
                            label = { Text(text = stringResource(id = R.string.GetNewPassword)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (newPasswordVisible) {
                                    R.drawable.visivility_image
                                } else {
                                    R.drawable.visivilityoff_image
                                }
                                IconButton(onClick = { newPasswordVisible = !newPasswordVisible }
                                ) {
                                    Image(
                                        painterResource(id = image),
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onOKClicked() }
                        ) {
                            Text(text = stringResource(id = R.string.OK))
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

