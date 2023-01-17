package com.example.workinghours.presentation.previousDaysScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.model.DaysOfMonth
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.GetDayOfMonthUseCase
import com.example.workinghours.domain.usecase.GetUserDateUseCase
import com.example.workinghours.presentation.model.DayInCalendar
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

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
                daysOfMonth = daysOfMonth()
            ))
        }
    }

    val state = mutableStateOf(ViewModelState())

    @AssistedFactory
    interface Factory {
        fun create(userId: Int): PreviousDaysViewModel
    }

    fun onTopAppBarFilterClicked() {
        updateState(state.value.copy(
            showFilterTopAppBar = true
        ))
    }

    fun onDismissFilterTopAppBar() {
        updateState(state.value.copy(
            showFilterTopAppBar = false,
        ))
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(
        val showFilterTopAppBar: Boolean = false,
        val nameOfDay: String = Utils.EMPTY_STRING,
        private val userDate: List<WorkData> = emptyList(),
        private val daysOfMonth: List<DaysOfMonth> = emptyList(),
        val listOfNameDay: List<String> = listOf(
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday",
            "Sunday",
        ),
    ) {
        val dayInCalendar: List<DayInCalendar> = daysOfMonth.map { dayOfMonth ->
            val patternForDate = "MM/dd/yyyy"
            val patternForTime = "HH:mm"
            val hygieneWorkTime: String =
                userDate.firstOrNull { it.userWorkDate.dayOfMonth == dayOfMonth.numberOfDay }
                    ?.hygieneWorkTime
                    ?.toString(patternForTime)
                    ?: ""
            val endWorkTime: String =
                userDate.firstOrNull { it.userWorkDate.dayOfMonth == dayOfMonth.numberOfDay }
                    ?.endWorkTime
                    ?.toString(patternForTime)
                    ?: ""
            val startWorkTime: String =
                userDate.firstOrNull { it.userWorkDate.dayOfMonth == dayOfMonth.numberOfDay }
                    ?.startWorkTime
                    ?.toString(patternForTime)
                    ?: ""
            val workAmount: String =
                userDate.firstOrNull { it.userWorkDate.dayOfMonth == dayOfMonth.numberOfDay }
                    ?.amountWorkTime
                    ?.toString(patternForTime)
                    ?: ""
            val showIfIsSunday: Boolean =
                "Sunday" == dayOfMonth.date.dayOfWeek().asText

            DayInCalendar(
                workDate = "${dayOfMonth.numberOfDay}/${dayOfMonth.numberOfMonth}/${dayOfMonth.numberOfYear}",
                workAmount = workAmount,
                startWorkTime = startWorkTime,
                endWorkTime = endWorkTime,
                hygieneTime = hygieneWorkTime,
                showWorkAmount = workAmount.isNotBlank(),
                showOnlyWorkDay = workAmount.isNotEmpty(),
                showStartWorkTime = startWorkTime.isNotBlank(),
                showHygieneTime = hygieneWorkTime.isNotBlank(),
                showIfIsSunday = showIfIsSunday)
        }
    }
}