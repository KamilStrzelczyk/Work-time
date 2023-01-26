package com.example.workinghours.presentation.adminScreen.admin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.usecase.AddNewUserUseCase
import com.example.workinghours.domain.usecase.ChangeAdminPasswordUseCase
import com.example.workinghours.domain.usecase.GetAdminPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val addNewUserUseCase: AddNewUserUseCase,
    private val changeAdminPassword: ChangeAdminPasswordUseCase,
    private val getAdminPassword: GetAdminPasswordUseCase,
) : ViewModel() {
    val state = mutableStateOf(ViewModelState())

    init {
        viewModelScope.launch {
            updateState(
                state.value.copy(
                    adminPasswordFromDataBase = getAdminPassword(),
                    adminOption = true
                )
            )
        }
    }

    fun onSaveUserClicked() {
        viewModelScope.launch {
            addNewUserUseCase(
                state.value.userName,
                state.value.userPassword
            )
            updateState(
                state.value.copy(
                    showAddUserActionsDialog = false,
                )
            )
        }
    }

    fun onUserNameChange(userName: String) {
        updateState(
            state.value.copy(
                userName = userName,
            )
        )
    }

    fun onUserPasswordChange(userPassword: String) {
        updateState(
            state.value.copy(
                userPassword = userPassword,
            )
        )
    }

    fun onDismissAddUserActionsDialog() {
        updateState(
            state.value.copy(
                showAddUserActionsDialog = false,
            )
        )
    }

    fun showAddUserActionsDialog() {
        updateState(
            state.value.copy(
                showAddUserActionsDialog = true,
            )
        )
    }

    fun showChangeAdminPasswordDialog() {
        updateState(
            state.value.copy(
                showAdminChangePasswordDialog = true
            )
        )
    }

    fun onDismissAdminChangePasswordDialog() {
        updateState(
            state.value.copy(
                showAdminChangePasswordDialog = false
            )
        )
    }

    fun onPasswordChange(adminPassword: String) {
        updateState(
            state.value.copy(
                adminPassword = adminPassword,
            )
        )
    }

    fun onNewPasswordChange(newAdminPassword: String) {
        updateState(
            state.value.copy(
                newAdminPassword = newAdminPassword
            )
        )
    }

    fun onOKClicked() {
        if (state.value.adminPasswordFromDataBase == state.value.adminPassword) {
            viewModelScope.launch {
                changeAdminPassword(state.value.newAdminPassword)
            }
            updateState(
                state.value.copy(
                    showAdminChangePasswordDialog = false,
                    correctPassword = true,
                    adminPassword = Utils.EMPTY_STRING,
                    newAdminPassword = Utils.EMPTY_STRING,
                    isError = false,
                )
            )
        } else {
            updateState(
                state.value.copy(
                    isError = true
                )
            )
        }
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(
        val isError: Boolean = false,
        val correctPassword: Boolean = false,
        val showAdminChangePasswordDialog: Boolean = false,
        val adminOption: Boolean = false,
        val showAddUserActionsDialog: Boolean = false,
        val userName: String = Utils.EMPTY_STRING,
        val userPassword: String = Utils.EMPTY_STRING,
        val adminPasswordFromDataBase: String = Utils.EMPTY_STRING,
        val adminPassword: String = Utils.EMPTY_STRING,
        val newAdminPassword: String = Utils.EMPTY_STRING,
    ){}
}