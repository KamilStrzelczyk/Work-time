package com.example.workinghours.presentation.listOfUsersScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.usecase.GetAllUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
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
        val x = DateTime.now()
        val b = x.dayOfWeek()
        println("dupa${x.dayOfWeek().asText}")
        println("dupa${x.withDayOfMonth(2)}")
    }


    fun onUserNameBoxClicked(userId: Int) {
        updateState(state.value.copy(
            userId = userId
        ))
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
        val userId: Int = 0,
    )
}