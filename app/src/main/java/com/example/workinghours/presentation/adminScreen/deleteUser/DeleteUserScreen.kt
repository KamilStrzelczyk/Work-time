package com.example.workinghours.presentation.adminScreen.deleteUser

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workinghours.R


@Composable
fun DeleteUserScreen(
    deleteUserViewModel: DeleteUserViewModel,
) {
    val state = deleteUserViewModel.state.collectAsState().value
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = stringResource(id = R.string.DeleteUser)
                    )
                },
                actions = {}
            )
            AcceptingDialog(
                showAcceptingDialog = state.showAcceptingDialog,
                onDismissShowAcceptingDialog = deleteUserViewModel::onDismissShowAcceptingDialog,
                onConfirmDeleteUser = deleteUserViewModel::onConfirmDeleteUser,
                userName = state.userName,
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(state.userList) {
                    UserNameBox(
                        userName = it.userName,
                        onDeleteUser = {
                            deleteUserViewModel.onDeleteUserClicked(
                                it.id,
                                it.userName
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun UserNameBox(
    userName: String,
    onDeleteUser: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable(onClick = onDeleteUser)
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
private fun AcceptingDialog(
    showAcceptingDialog: Boolean,
    onDismissShowAcceptingDialog: () -> Unit,
    onConfirmDeleteUser: () -> Unit,
    userName: String,
) {
    if (showAcceptingDialog)
        Dialog(onDismissRequest = { onDismissShowAcceptingDialog() }
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
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = stringResource(id = R.string.DidRemoveUser))
                        Text(text = userName)
                        Button(
                            onClick = {
                                onConfirmDeleteUser()
                                onDismissShowAcceptingDialog()
                            }
                        ) {
                            Text(text = stringResource(id = R.string.OK))
                        }
                    }
                }
            }
        }
}