package com.example.workinghours.presentation.listOfUsersScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.usecase.GetAdminPasswordUseCase
import com.example.workinghours.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.apache.poi.xdgf.util.Util
import javax.inject.Inject

@HiltViewModel
class ListOfUsersViewModel @Inject constructor(
    private val getAllUsers: GetAllUsersUseCase,
    private val getAdminPassword: GetAdminPasswordUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewModelState())
    val state: StateFlow<ViewModelState> = _state

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                userList = getAllUsers(),
                adminPassword = getAdminPassword()
            )
        }
    }

    fun onUserNameBoxClicked(userId: Int, userName: String) {
        updateState(
            _state.value.copy(
                userId = userId,
                userName = userName,
            )
        )
    }

    fun onTopAppBarMoreActionClicked() {
        updateState(
            _state.value.copy(
                showTopAppBarMoreAction = true
            )
        )
    }

    fun onDismissTopAppBarMoreAction() {
        updateState(
            _state.value.copy(
                showTopAppBarMoreAction = false
            )
        )
    }

    fun onDismissUserActionsDialog() {
        updateState(
            _state.value.copy(
                showUserActionsDialog = false
            )
        )
    }

    fun onUsersClicked() {
        updateState(
            _state.value.copy(
                showUserActionsDialog = true
            )
        )
    }

    fun onAdminClicked() {
        updateState(
            _state.value.copy(
                showAdminPasswordDialog = true
            )
        )
    }

    fun onDismissAdminPasswordDialog() {
        updateState(
            _state.value.copy(
                showAdminPasswordDialog = false
            )
        )
    }

    fun onPasswordChange(password: String) {
        updateState(
            _state.value.copy(
                password = password
            )
        )
    }

    fun onOkClicked(onValidationPassed: () -> Unit) {
        if (_state.value.adminPassword == _state.value.password) {
            onValidationPassed()
            updateState(
                _state.value.copy(
                    showAdminPasswordDialog = false,
                    correctPassword = true,
                    password = Utils.EMPTY_STRING,
                    isError = false,
                )
            )
        } else {
            updateState(
                _state.value.copy(
                    isError = true
                )
            )
        }
    }

    private fun updateState(state: ViewModelState) {
        this._state.value = state
    }

    data class ViewModelState(
        val isError: Boolean = false,
        val showAdminPasswordDialog: Boolean = false,
        val showTopAppBarMoreAction: Boolean = false,
        val showUserActionsDialog: Boolean = false,
        val correctPassword: Boolean = false,
        val userList: List<User> = emptyList(),
        val userId: Int = Utils.EMPTY_INT,
        val userName: String = Utils.EMPTY_STRING,
        val adminPassword: String = Utils.EMPTY_STRING,
        val password: String = Utils.EMPTY_STRING,
    )
}