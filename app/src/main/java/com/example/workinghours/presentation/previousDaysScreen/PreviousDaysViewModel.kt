package com.example.workinghours.presentation.previousDaysScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.model.Day
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.GetDaysOfCurrentMonthUseCase
import com.example.workinghours.domain.usecase.GetUserDateUseCase
import com.example.workinghours.presentation.model.DayInCalendar
import com.example.workinghours.ui.MonthPickerGridOption
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.LocalDate

class PreviousDaysViewModel @AssistedInject constructor(
    private val getUserDate: GetUserDateUseCase,
    private val getDaysOfCurrentMonth: GetDaysOfCurrentMonthUseCase,
    @Assisted
    private val userId: Int,
) : ViewModel() {
    private val _state = MutableStateFlow(ViewModelState())
    val state: StateFlow<ViewModelState> = _state

    init {
        viewModelScope.launch {
            val daysOfMonth = getDaysOfCurrentMonth()
            val userDate = getUserDate(
                userId = userId
            )
            updateState(
                _state.value.copy(
                    userDate = userDate,
                    daysOfMonth = daysOfMonth
                )
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(userId: Int): PreviousDaysViewModel
    }

    fun onTopAppBarFilterAllDaysClicked() {
        updateState(
            _state.value.copy(
                showOnlyWorkDays = false
            )
        )
    }

    fun onTopAppBarFilterWorkdaysClicked() {
        updateState(
            _state.value.copy(
                showOnlyWorkDays = true
            )
        )
    }

    fun onTopAppBarFilterClicked() {
        updateState(
            _state.value.copy(
                showFilterTopAppBar = true
            )
        )
    }

    fun onDismissFilterTopAppBar() {
        updateState(
            _state.value.copy(
                showFilterTopAppBar = false,
            )
        )
    }

    fun showCalendar() {
        updateState(
            _state.value.copy(
                showCalendar = true
            )
        )
    }

    fun onDismissCalendar() {
        updateState(
            _state.value.copy(
                showCalendar = false
            )
        )
    }

    fun minusYear() {
        val newYear = _state.value.year
        updateState(
            _state.value.copy(
                year = LocalDate(newYear.minusYears(1))
            )
        )
    }

    fun addYear() {
        val newYear = _state.value.year
        updateState(
            _state.value.copy(
                year = LocalDate(newYear.plusYears(1))
            )
        )
    }

    fun onOtherMonthClicked(year: Int, month: Int) {
        val newMonth: LocalDate = LocalDate().withYearOfEra(year).withMonthOfYear(month)
        updateState(
            _state.value.copy(
                calendarDate = newMonth,
            )
        )
    }

    fun onShowDateClicked() {
        viewModelScope.launch {
            updateState(
                _state.value.copy(
                    showCalendar = false,
                )
            )
        }
    }

    private fun updateState(state: ViewModelState) {
        this._state.value = state
    }

    data class ViewModelState(
        val currentDate: LocalDate = LocalDate.now(),
        val year: LocalDate = LocalDate(),
        val calendarDate: LocalDate = LocalDate(),
        val showCalendar: Boolean = false,
        val showOnlyWorkDays: Boolean = false,
        val showFilterTopAppBar: Boolean = false,
        val nameOfDay: String = Utils.EMPTY_STRING,
        private val userDate: List<WorkData> = emptyList(),
        private val daysOfMonth: List<Day> = emptyList(),
    ) {
        val dayInCalendar: List<DayInCalendar> = daysOfMonth.map { dayOfMonth ->
            val hygieneWorkTime: String = getWorkDate(dayOfMonth)?.hygieneWorkTime.toStringOrEmpty()
            val endWorkTime: String = getWorkDate(dayOfMonth)?.endWorkTime.toStringOrEmpty()
            val startWorkTime: String = getWorkDate(dayOfMonth)?.startWorkTime.toStringOrEmpty()
            val workAmount = getWorkDate(dayOfMonth)?.amountWorkTime.toStringOrEmpty()
            val showIfIsSaturday: Boolean = Utils.SATURDAY == dayOfMonth.date.dayOfWeek
            val showIfIsSunday: Boolean = Utils.SUNDAY == dayOfMonth.date.dayOfWeek

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
                showIfIsSunday = showIfIsSunday,
                showIfIsSaturday = showIfIsSaturday,
            )
        }.filter {
            if (showOnlyWorkDays) {
                it.showOnlyWorkDay
            } else {
                true
            }
        }

        private fun getWorkDate(dayOfMonth: Day) =
            userDate.firstOrNull { it.userWorkDate.dayOfMonth == dayOfMonth.numberOfDay && it.userWorkDate.year == dayOfMonth.numberOfYear }

        private fun DateTime?.toStringOrEmpty(): String {
            val patternForTime = "HH:mm"
            return this?.toString(patternForTime) ?: ""
        }

        val gridList = listOf(
            MonthPickerGridOption(
                "sty",
                1,
            ),
            MonthPickerGridOption(
                "lut",
                2,
            ),
            MonthPickerGridOption(
                "mar",
                3,
            ),
            MonthPickerGridOption(
                "kwi",
                4,
            ),
            MonthPickerGridOption(
                "maj",
                5,
            ),
            MonthPickerGridOption(
                "cze",
                6,
            ),
            MonthPickerGridOption(
                "lip",
                7,
            ),
            MonthPickerGridOption(
                "sie",
                8,
            ),
            MonthPickerGridOption(
                "wrz",
                9,
            ),
            MonthPickerGridOption(
                "paź",
                10,
            ),
            MonthPickerGridOption(
                "lis",
                11,
            ),
            MonthPickerGridOption(
                "gru",
                12,
            ),
        )
    }
}