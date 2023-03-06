package com.example.workinghours.presentation.addWorkTimeScreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.model.CalculateAmountOfHours
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.CalculateAmountOfHoursUseCase
import com.example.workinghours.domain.usecase.GetCurrentDateAndTimeUseCase
import com.example.workinghours.domain.usecase.SaveUserWorkDataUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class AddWorkTimeViewModel @AssistedInject constructor(
    private val calculateAmountOfHours: CalculateAmountOfHoursUseCase,
    private val saveUserWorkData: SaveUserWorkDataUseCase,
    private val getCurrentDateAndTime: GetCurrentDateAndTimeUseCase,
    @Assisted
    private val userId: Int,
    @Assisted
    private val userName: String,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewModelState())
    val state: StateFlow<ViewModelState> = _state

    @AssistedFactory
    interface Factory {
        fun create(userId: Int, userName: String): AddWorkTimeViewModel
    }

    init {
        viewModelScope.launch {
            getCurrentDateAndTime().collect {
                updateState(
                    _state.value.copy(
                        currentDataAndTime = it
                    )
                )
            }
        }
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            _state.value.workTime?.let {
                WorkData(
                    userId = userId,
                    userName = userName,
                    userWorkDate = it.userWorkDate,
                    startWorkTime = it.startWorkTime,
                    endWorkTime = it.endWorkTime,
                    hygieneWorkTime = it.hygieneWorkTime,
                    amountWorkTime = it.amountWorkTime,
                    year = it.userWorkDate.year,
                    monthNumber = it.userWorkDate.monthOfYear,
                )
            }?.let { saveUserWorkData(it) }
        }
        onDismissSaveDialog()
    }

    fun onDismissSaveDialog() {
        updateState(
            _state.value.copy(
                showSaveDialog = false,
            )
        )
    }

    fun onButtonClicked() {
        val workTime = calculateAmountOfHours(
            startHour = _state.value.startWorkClock.setHour,
            startMinute = _state.value.startWorkClock.setMinute,
            endHour = _state.value.endWorkClock.setHour,
            endMinute = _state.value.endWorkClock.setMinute,
            hygieneHour = _state.value.hygieneClock.setHour,
            hygieneMinute = _state.value.hygieneClock.setMinute,
        )
        updateState(
            _state.value.copy(
                workTime = workTime,
                showSaveDialog = true,
            )
        )
    }

    fun updateStartWorkClock(clock: Clock) {
        updateState(
            _state.value.copy(
                startWorkClock = clock
            )
        )
    }

    fun updateEndWorkClock(clock: Clock) {
        updateState(
            _state.value.copy(
                endWorkClock = clock
            )
        )
    }

    fun updateHygieneWorkClock(clock: Clock) {
        updateState(
            _state.value.copy(
                hygieneClock = clock
            )
        )
    }

    fun onTopAppBarMoreActionClicked() {
        updateState(
            _state.value.copy(
                showUserActionsDialog = true,
            )
        )
    }

    private fun updateState(state: ViewModelState) {
        this._state.value = state
    }

    data class ViewModelState(
        val showUserActionsDialog: Boolean = false,
        val currentDataAndTime: DateTime? = null,
        val showSaveDialog: Boolean = false,
        val extraTime: Int = Utils.EMPTY_INT,
        val workTime: CalculateAmountOfHours? = null,
        val clockHour: List<String> = listOf(
            "00",
            "01",
            "02",
            "03",
            "04",
            "05",
            "06",
            "07",
            "08",
            "09",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20",
            "21",
            "22",
            "23"
        ),
        val clockMinute: List<String> = listOf(
            "00",
            "15",
            "30",
            "45"
        ),
        val hygieneClockHour: List<String> = listOf(
            "0",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "10"
        ),
        val hygieneClockMinute: List<String> = listOf(
            "00",
            "10",
            "20",
            "30",
            "40",
            "50",
            "60"
        ),
        val startWorkClock: Clock = Clock(
            showMinuteClock = false,
            showClock = false,
            timeHourScope = clockHour,
            timeMinuteScope = clockMinute,
            setHour = 0,
            setMinute = 0,
        ),
        val endWorkClock: Clock = Clock(
            showMinuteClock = false,
            showClock = false,
            timeHourScope = clockHour,
            timeMinuteScope = clockMinute,
            setHour = 0,
            setMinute = 0,
        ),
        val hygieneClock: Clock = Clock(
            showMinuteClock = false,
            showClock = false,
            timeHourScope = hygieneClockHour,
            timeMinuteScope = hygieneClockMinute,
            setHour = 0,
            setMinute = 0,
        ),
    )

    data class Clock(
        val showMinuteClock: Boolean,
        val showClock: Boolean,
        val timeHourScope: List<String>,
        val timeMinuteScope: List<String>,
        val setHour: Int,
        val setMinute: Int,
    ) {

        fun onHourSelected(hour: Int): Clock {
            return copy(
                showClock = false,
                setHour = hour
            )
        }

        fun onMinuteSelected(minute: Int): Clock {
            return copy(
                showMinuteClock = false,
                setMinute = minute
            )
        }

        fun showClockHour(): Clock {
            return copy(showClock = true)
        }

        fun showClockMinute(): Clock {
            return copy(showMinuteClock = true)
        }

        fun hideClock(): Clock {
            return copy(
                showClock = false,
                showMinuteClock = false
            )
        }
    }
}