package com.example.workinghours.presentation.ListOfUsersScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.workinghours.R
import com.example.workinghours.presentation.Screen

@Composable

fun ListOfUsersScreen(
    viewModel: ListOfUsersViewModel,
    navController: NavController,
) {
    val state = viewModel.state.value
    val scaffoldState: ScaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = "Czas pracy"
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.onTopAppBarMoreActionClicked() }
                    ) {
                        Icon(Icons.Filled.MoreVert, contentDescription = null)
                    }
                    MoreAction(state = state, viewModel = viewModel)
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
            ) {
                items(state.userList) {
                    UserNameBox(
                        userName = it,
                        onClick = { viewModel.onUsersClicked() },
                        state = state,
                    )
                }
            }
        }
    }
    UserActionsDialog(state = state, viewModel, navController)

}

@Composable

fun UserNameBox(
    userName: String,
    onClick: (Boolean) -> Unit,
    state: ListOfUsersViewModel.ViewModelState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { onClick(state.showUserActionsDialog) }
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

fun UserActionsDialog(
    state: ListOfUsersViewModel.ViewModelState,
    viewModel: ListOfUsersViewModel,
    navController: NavController,
) {

    if (state.showUserActionsDialog)
        Dialog(onDismissRequest = { viewModel.onDismissUserActionsDialog() }) {
            Surface(modifier = Modifier
                .clip(RoundedCornerShape(16.dp))) {
                Box(modifier = Modifier
                    .padding(20.dp),
                    contentAlignment = Alignment.Center)
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Surface(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(112.dp)
                                    .clickable { navController.navigate(Screen.PreviousDayScreen.route) },
                            ) {
                                Column(modifier = Modifier
                                    .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center)
                                {

                                    Text(text = "Poprzednie dni", Modifier.padding(5.dp))
                                    Image(painter = painterResource(id = R.drawable.calendarimage),
                                        contentDescription = null)
                                }

                            }
                        }

                        Spacer(modifier = Modifier.weight(0.1f))

                        Surface(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
                            Box(modifier = Modifier
                                .weight(1f)
                                .height(112.dp)
                                .clickable { navController.navigate(Screen.AddWorkTimeScreen.route) })
                            {
                                Column(modifier = Modifier
                                    .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "Dodaj nowy dzień", Modifier.padding(5.dp))
                                    Image(painter = painterResource(id = R.drawable.calendaraddimage),
                                        contentDescription = null)
                                }


                            }
                        }
                    }
                }
            }
        }
}

@Composable
fun MoreAction(
    state: ListOfUsersViewModel.ViewModelState,
    viewModel: ListOfUsersViewModel,
) {
    if (state.showTopAppBarMoreAction)
        DropdownMenu(
            expanded = true,
            onDismissRequest = { viewModel.onDismissTopAppBarMoreAction() }
        ) {
            DropdownMenuItem(
                onClick = { }
            ) {
                Image(painter = painterResource(id = R.drawable.accounticon),
                    contentDescription = null)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Użytkownik")
            }
        }
}
