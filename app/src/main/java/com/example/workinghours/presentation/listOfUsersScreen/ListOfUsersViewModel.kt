package com.example.workinghours.presentation.listOfUsersScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListOfUsersViewModel @Inject constructor() : ViewModel() {

    val state = mutableStateOf(ViewModelState())

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
        val showTopAppBarMoreAction: Boolean = false,
        val showUserActionsDialog: Boolean = false,
        val userList: List<String> = listOf("Kasia - Higienistka",
            "Zosia - Recepcjonistka",
            "Małgosia - Recepcjonistka",
            "Dominika- Higienistka",
            "Karol- Higienistka"),
    )
}