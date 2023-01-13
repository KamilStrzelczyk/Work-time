package com.example.workinghours.presentation.listOfUsersScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.usecase.AddNewUserUseCase
import com.example.workinghours.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfUsersViewModel @Inject constructor(
    private val getAllUsers: GetAllUsersUseCase,
) : ViewModel() {

    val state = mutableStateOf(ViewModelState())

    init {
        viewModelScope.launch {
            state.value = state.value.copy(
                userList = getAllUsers())
        }
    }



    fun onTopAppBarMoreActionClicked() {
        updateState(state.value.copy(
            showTopAppBarMoreAction = true
        ))
    }

    fun onDismissTopAppBarMoreAction() {
        updateState(state.value.copy(
            showTopAppBarMoreAction = false
        ))
    }

    fun onDismissUserActionsDialog() {
        updateState(state.value.copy(
            showUserActionsDialog = false
        ))
    }

    fun onUsersClicked() {
        updateState(state.value.copy(
            showUserActionsDialog = true
        ))
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(

        // showComposeComponent
        val showTopAppBarMoreAction: Boolean = false,
        val showUserActionsDialog: Boolean = false,
        //value
        val userList: List<User> = emptyList(),

    )
}