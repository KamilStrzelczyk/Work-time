package com.example.workinghours.presentation.previousDaysScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.GetDayOfMonthUseCase
import com.example.workinghours.domain.usecase.GetUserDateUseCase
import com.example.workinghours.presentation.addWorkTimeScreen.AddWorkTimeViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import javax.inject.Inject

class PreviousDaysViewModel @AssistedInject constructor(
    private val getUserDate: GetUserDateUseCase,
    private val getDayOfMonth: GetDayOfMonthUseCase,
    @Assisted
    private val userId: Int,
) : ViewModel() {

    init {

        viewModelScope.launch {
            val daysOfMonth = getDayOfMonth
            val userDate = getUserDate(
                userId = userId
            )
            updateState(state.value.copy(
                userDate = userDate,
                listDaysOfMonth = daysOfMonth()
            ))
        }
    }

    val state = mutableStateOf(ViewModelState())

    @AssistedFactory
    interface Factory {
        fun create(userId: Int): PreviousDaysViewModel
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(
        val userDate: List<WorkData> = emptyList(),
        val listDaysOfMonth: List<Int> = emptyList(),
    )
}