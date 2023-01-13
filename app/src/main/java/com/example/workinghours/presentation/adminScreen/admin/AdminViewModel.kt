package com.example.workinghours.presentation.adminScreen.admin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.usecase.AddNewUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val addNewUserUseCase: AddNewUserUseCase,
) : ViewModel() {
    val state = mutableStateOf(ViewModelState())

    init {
        updateState(state.value.copy(
            adminOption = true
        ))
    }

    fun onSaveUserClicked() {
        viewModelScope.launch {
            addNewUserUseCase(
                state.value.userName,
                state.value.userPassword
            )
            updateState(state.value.copy(
                showAddUserActionsDialog = false,
            ))
        }
    }

    fun onUserNameChange(userName: String) {
        updateState(state.value.copy(
            userName = userName,
        ))
    }

    fun onUserPasswordChange(userPassword: String) {
        updateState(state.value.copy(
            userPassword = userPassword,
        ))
    }

    fun onDismissAddUserActionsDialog(){
        updateState(state.value.copy(
            showAddUserActionsDialog = false,
        ))
    }

    fun showAddUserActionsDialog() {
        updateState(state.value.copy(
            showAddUserActionsDialog = true,
        ))
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(
        val adminOption: Boolean = false,
        val showAddUserActionsDialog: Boolean = false,
        val userName: String = Utils.EMPTY_STRING,
        val userPassword: String = Utils.EMPTY_STRING,
    )
}