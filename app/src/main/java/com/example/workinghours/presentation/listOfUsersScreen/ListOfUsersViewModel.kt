package com.example.workinghours.presentation.listOfUsersScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.usecase.GetAdminPasswordUseCase
import com.example.workinghours.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListOfUsersViewModel @Inject constructor(
    private val getAllUsers: GetAllUsersUseCase,
    private val getAdminPassword: GetAdminPasswordUseCase,
) : ViewModel() {

    val state = mutableStateOf(ViewModelState())

    init {
        viewModelScope.launch {
            state.value = state.value.copy(
                userList = getAllUsers(),
                adminPassword = getAdminPassword()
            )
        }
    }

    fun onUserNameBoxClicked(userId: Int) {
        updateState(
            state.value.copy(
                userId = userId
            )
        )
    }

    fun onTopAppBarMoreActionClicked() {
        updateState(
            state.value.copy(
                showTopAppBarMoreAction = true
            )
        )
    }

    fun onDismissTopAppBarMoreAction() {
        updateState(
            state.value.copy(
                showTopAppBarMoreAction = false
            )
        )
    }

    fun onDismissUserActionsDialog() {
        updateState(
            state.value.copy(
                showUserActionsDialog = false
            )
        )
    }

    fun onUsersClicked() {
        updateState(
            state.value.copy(
                showUserActionsDialog = true
            )
        )
    }

    fun onAdminClicked() {
        updateState(
            state.value.copy(
                showAdminPasswordDialog = true
            )
        )
    }

    fun onDismissAdminPasswordDialog() {
        updateState(
            state.value.copy(
                showAdminPasswordDialog = false
            )
        )
    }

    fun onPasswordChange(password: String) {
        updateState(
            state.value.copy(
                password = password
            )
        )
    }

    fun onOkClicked(onValidationPassed: () -> Unit) {
        if (state.value.adminPassword == state.value.password) {
            onValidationPassed()
            updateState(
                state.value.copy(
                    showAdminPasswordDialog = false,
                    correctPassword = true,
                    password = Utils.EMPTY_STRING,
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
        val showAdminPasswordDialog: Boolean = false,
        val showTopAppBarMoreAction: Boolean = false,
        val showUserActionsDialog: Boolean = false,
        val correctPassword: Boolean = false,
        val userList: List<User> = emptyList(),
        val userId: Int = Utils.EMPTY_INT,
        val adminPassword: String = Utils.EMPTY_STRING,
        val password: String = Utils.EMPTY_STRING,
    )
}