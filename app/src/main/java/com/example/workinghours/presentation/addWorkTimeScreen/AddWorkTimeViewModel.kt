package com.example.workinghours.presentation.addWorkTimeScreen


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Utils
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.CalculateAmountOfHoursUseCase
import com.example.workinghours.domain.usecase.SaveUserWorkDataUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import org.joda.time.DateTime

class AddWorkTimeViewModel @AssistedInject constructor(
    private val calculateAmountOfHours: CalculateAmountOfHoursUseCase,
    private val saveUserWorkData: SaveUserWorkDataUseCase,
    @Assisted
    private val userId: Int,
) : ViewModel() {

    val state = mutableStateOf(ViewModelState())

    @AssistedFactory
    interface Factory {
        fun create(userId: Int): AddWorkTimeViewModel
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            state.value.workTime?.let {
                WorkData(
                    userId = userId,
                    userWorkData = state.value.userWorkData,
                    userWorkAmount = it,
                )
            }?.let { saveUserWorkData(it) }
        }
        onDismissSaveDialog()
    }

    fun onDismissSaveDialog() {
        updateState(state.value.copy(
            showSaveDialog = false,
        ))
    }

    fun onButtonClicked() {
        val workTime = calculateAmountOfHours(
            startHour = state.value.startWorkClock.setHour,
            startMinute = state.value.startWorkClock.setMinute,
            endHour = state.value.endWorkClock.setHour,
            endMinute = state.value.endWorkClock.setMinute,
            hygieneHour = state.value.hygieneClock.setHour,
            hygieneMinute = state.value.hygieneClock.setMinute,
        )
        updateState(state.value.copy(
            workTime = workTime,
            showSaveDialog = true,
        ))
    }

    fun updateStartWorkClock(clock: Clock) {
        updateState(state.value.copy(
            startWorkClock = clock
        ))
    }

    fun updateEndWorkClock(clock: Clock) {
        updateState(state.value.copy(
            endWorkClock = clock
        ))
    }

    fun updateHygieneWorkClock(clock: Clock) {
        updateState(state.value.copy(
            hygieneClock = clock
        ))
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(
        val userWorkData: Int = 2,
        val showSaveDialog: Boolean = false,
        val extraTime: Int = Utils.EMPTY_INT,
        val workTime: DateTime? = null,
        val clockHour: List<String> = listOf<String>(
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
            "23"),
        val clockMinute: List<String> = listOf<String>(
            "00",
            "15",
            "30",
            "45"),
        val hygieneClockHour: List<String> = listOf<String>(
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
            "10"),
        val hygieneClockMinute: List<String> = listOf<String>(
            "00",
            "10",
            "20",
            "30",
            "40",
            "50",
            "60"),
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
            return copy(showClock = false, setHour = hour)
        }

        fun onMinuteSelected(minute: Int): Clock {
            return copy(showMinuteClock = false, setMinute = minute)
        }

        fun showClockHour(): Clock {
            return copy(showClock = true)
        }

        fun showClockMinute(): Clock {
            return copy(showMinuteClock = true)
        }

        fun hideClock(): Clock {
            return copy(showClock = false, showMinuteClock = false)
        }
    }
}