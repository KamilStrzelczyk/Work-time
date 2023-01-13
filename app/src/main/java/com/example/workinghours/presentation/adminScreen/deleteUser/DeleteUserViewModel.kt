package com.example.workinghours.presentation.adminScreen.deleteUser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.usecase.DeleteUserUseCase
import com.example.workinghours.domain.usecase.GetAllUsersUseCase
import com.example.workinghours.presentation.listOfUsersScreen.ListOfUsersViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteUserViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel() {

    val state = mutableStateOf(ViewModelState())

    init {
        viewModelScope.launch {
            state.value = state.value.copy(
                userList = getAllUsersUseCase()
            )
        }
    }

    fun onDeleteUserClicked(id: Int) {
        viewModelScope.launch {
            deleteUserUseCase(id)
            updateState(state.value.copy(
                userList = getAllUsersUseCase(),
            ))
        }
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(
        //value
        val userList: List<User> = emptyList(),
    )
}
