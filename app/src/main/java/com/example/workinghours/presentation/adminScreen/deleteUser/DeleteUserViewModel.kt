package com.example.workinghours.presentation.adminScreen.deleteUser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.usecase.DeleteUserUseCase
import com.example.workinghours.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteUserViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewModelState())
    val state: StateFlow<ViewModelState> = _state

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                userList = getAllUsersUseCase()
            )
        }
    }

    fun onDismissShowAcceptingDialog() {
        updateState(
            state.value.copy(
                showAcceptingDialog = false,
            )
        )
    }

    fun onDeleteUserClicked(id: Int, name: String) {
        updateState(
            _state.value.copy(
                showAcceptingDialog = true,
                userId = id,
                userName = name,
            )
        )
    }


    fun onConfirmDeleteUser() {
        viewModelScope.launch {
            deleteUserUseCase(_state.value.userId)
            updateState(
                _state.value.copy(
                    userList = getAllUsersUseCase(),
                )
            )
        }
    }

    private fun updateState(state: ViewModelState) {
        this._state.value = state
    }

    data class ViewModelState(
        val userList: List<User> = emptyList(),
        val showAcceptingDialog: Boolean = false,
        val userId: Int = Utils.EMPTY_INT,
        val userName: String = Utils.EMPTY_STRING,
    )
}
