package com.example.workinghours.presentation.listOfUsersScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.example.workinghours.domain.model.User
import com.example.workinghours.presentation.addWorkTimeScreen.AddWorkTimeActivity
import com.example.workinghours.presentation.adminScreen.admin.AdminActivity
import com.example.workinghours.presentation.adminScreen.admin.AdminViewModel
import com.example.workinghours.presentation.previousDaysScreen.PreviousDayActivity

@Composable
fun ListOfUsersScreen(
    listOfUsersViewModel: ListOfUsersViewModel,
    adminViewModel: AdminViewModel,
) {
    val listOfUsersState = listOfUsersViewModel.state.collectAsState().value
    val adminState = adminViewModel.state.value
    val context = LocalContext.current
    ListOfUsersScreen(
        userList = listOfUsersState.userList,
        showTopAppBarMoreAction = listOfUsersState.showTopAppBarMoreAction,
        adminOption = adminState.adminOption,
        showUserActionsDialog = listOfUsersState.showUserActionsDialog,
        isError = listOfUsersState.isError,
        onUsersClicked = { listOfUsersViewModel.onUsersClicked() },
        onTopAppBarMoreActionClicked = listOfUsersViewModel::onTopAppBarMoreActionClicked,
        onDismissTopAppBarMoreAction = listOfUsersViewModel::onDismissTopAppBarMoreAction,
        onDismissUserActionsDialog = listOfUsersViewModel::onDismissUserActionsDialog,
        showAdminPasswordDialog = listOfUsersViewModel::onAdminClicked,
        showAdminPasswordDialogBoolean = listOfUsersState.showAdminPasswordDialog,
        onUserNameBoxClicked = listOfUsersViewModel::onUserNameBoxClicked,
        password = listOfUsersState.password,
        onOkClicked = listOfUsersViewModel::onOkClicked,
        onPasswordChange = { listOfUsersViewModel.onPasswordChange(it) },
        onDismissAdminPasswordDialog = { listOfUsersViewModel.onDismissAdminPasswordDialog() },
        navigateToAddWorkTimeScreen = {
            context.startActivity(
                AddWorkTimeActivity.createStartIntent(
                    context,
                    listOfUsersState.userId,
                    listOfUsersState.userName,
                )
            )
        },
        navigateToPreviousDayScreen = {
            context.startActivity(
                PreviousDayActivity.createStartIntent(
                    context,
                    listOfUsersState.userId
                )
            )
        })
}

@Composable
private fun ListOfUsersScreen(
    userList: List<User>,
    showTopAppBarMoreAction: Boolean,
    adminOption: Boolean,
    showUserActionsDialog: Boolean,
    showAdminPasswordDialogBoolean: Boolean,
    isError: Boolean,
    onUsersClicked: () -> Unit,
    onTopAppBarMoreActionClicked: () -> Unit,
    onDismissTopAppBarMoreAction: () -> Unit,
    showAdminPasswordDialog: () -> Unit,
    onDismissUserActionsDialog: () -> Unit,
    onDismissAdminPasswordDialog: () -> Unit,
    navigateToAddWorkTimeScreen: () -> Unit,
    navigateToPreviousDayScreen: () -> Unit,
    onUserNameBoxClicked: (Int, String) -> Unit,
    password: String,
    onOkClicked: (() -> Unit) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(text = stringResource(id = R.string.WorkTime))
                },
                actions = {
                    IconButton(
                        onClick = onTopAppBarMoreActionClicked
                    ) {
                        Icon(Icons.Filled.MoreVert, contentDescription = null)
                    }
                    MoreAction(
                        showTopAppBarMoreAction = showTopAppBarMoreAction,
                        onDismissTopAppBarMoreAction = onDismissTopAppBarMoreAction,
                        showAdminPasswordDialog = showAdminPasswordDialog
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
            items(userList) {
                UserNameBox(
                    userName = it.userName,
                    userId = it.id,
                    showUserActionDialog = onUsersClicked,
                    onUserNameBoxClicked = onUserNameBoxClicked,
                )
            }
        }
    }
    UserActionsDialog(
        showUserActionsDialog = showUserActionsDialog,
        onDismissUserActionsDialog = onDismissUserActionsDialog,
        navigateToAddWorkTimeScreen = navigateToAddWorkTimeScreen,
        navigateToPreviousDayScreen = navigateToPreviousDayScreen
    )
    AdminPasswordDialog(
        showAdminPasswordDialog = showAdminPasswordDialogBoolean,
        onDismissAdminPasswordDialog = onDismissAdminPasswordDialog,
        password = password,
        onOkClicked = onOkClicked,
        onPasswordChange = onPasswordChange,
        isError = isError,
    )
}

@Composable
private fun UserNameBox(
    userId: Int,
    userName: String,
    showUserActionDialog: () -> Unit,
    onUserNameBoxClicked: (Int, String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp,
            )
            .clickable {
                showUserActionDialog.invoke()
                onUserNameBoxClicked(userId, userName)
            }
    ) {
        Card(elevation = 10.dp) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .height(56.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = userName)
            }
        }
    }
}


@Composable
private fun UserActionsDialog(
    showUserActionsDialog: Boolean,
    onDismissUserActionsDialog: () -> Unit,
    navigateToPreviousDayScreen: () -> Unit,
    navigateToAddWorkTimeScreen: () -> Unit,
) {

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
                                        navigateToPreviousDayScreen()
                                        onDismissUserActionsDialog()
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
                                        navigateToAddWorkTimeScreen()
                                        onDismissUserActionsDialog()
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
                    }
                }
            }
        }
}

@Composable
private fun MoreAction(
    showTopAppBarMoreAction: Boolean,
    onDismissTopAppBarMoreAction: () -> Unit,
    showAdminPasswordDialog: () -> Unit,
) {
    if (showTopAppBarMoreAction)
        DropdownMenu(
            expanded = true,
            onDismissRequest = onDismissTopAppBarMoreAction
        ) {
            DropdownMenuItem(
                onClick = {
                    onDismissTopAppBarMoreAction()
                    showAdminPasswordDialog()
                }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.account_icon),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = stringResource(id = R.string.Admin))
            }
        }
}

@Composable
private fun AdminPasswordDialog(
    isError: Boolean,
    password: String,
    showAdminPasswordDialog: Boolean,
    onDismissAdminPasswordDialog: () -> Unit,
    onOkClicked: (() -> Unit) -> Unit,
    onPasswordChange: (String) -> Unit,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current
    if (showAdminPasswordDialog)
        Dialog(onDismissRequest = { onDismissAdminPasswordDialog() }) {
            Surface(
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
            ) {
                Box(
                    modifier = Modifier.padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        OutlinedTextField(
                            value = password,
                            onValueChange = { onPasswordChange(it) },
                            label = { Text(text = stringResource(id = R.string.GetPassword)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                val image = if (passwordVisible) {
                                    R.drawable.visivility_image
                                } else {
                                    R.drawable.visivilityoff_image
                                }
                                IconButton(
                                    onClick = { passwordVisible = !passwordVisible }
                                ) {
                                    Image(
                                        painterResource(id = image),
                                        contentDescription = null
                                    )
                                }
                            },
                            isError = isError
                        )
                        if (isError) {
                            Text(
                                text = stringResource(id = R.string.WrongPassword),
                                fontSize = 10.sp,
                                color = MaterialTheme.colors.error
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                onOkClicked {
                                    context.startActivity(
                                        Intent(context, AdminActivity::class.java)
                                    )
                                }
                            }
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
//private fun ListOfUsersScreenPreview(
//) = ListOfUsersScreen(userList = listOf("Dominik"),
//    showTopAppBarMoreAction = false,
//    adminOption = true,
//    showUserActionsDialog = false,
//    onUsersClicked = { },
//    onTopAppBarMoreActionClicked = { },
//    onDismissTopAppBarMoreAction = { },
//    onDismissUserActionsDialog = { },
//    navigateToAddWorkTimeScreen = { }) {
//
//}
