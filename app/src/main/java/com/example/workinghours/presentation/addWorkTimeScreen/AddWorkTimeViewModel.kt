package com.example.workinghours.presentation.addWorkTimeScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.workinghours.domain.usecase.AddWorkTimeScreenUseCase.CalculateAmountOfHours
import dagger.hilt.android.lifecycle.HiltViewModel
import org.joda.time.DateTime
import javax.inject.Inject

@HiltViewModel
class AddWorkTimeViewModel @Inject constructor(
    private val calculateAmountOfHours: CalculateAmountOfHours,
) : ViewModel() {
    val state = mutableStateOf(ViewModelState())

    fun onSecondHourClicked(secondHour: String) {
        updateState(state.value.copy(
            secondHour = secondHour.toInt(),
            showSecondClockHour = false,
        ))
    }

    fun onSecondMinuteClicked(secondMinute: String) {
        updateState(state.value.copy(
            secondMinute = secondMinute.toInt(),
            showSecondClockMinute = false,
        ))
    }

    fun onStartHourClicked(startHour: String) {
        updateState(state.value.copy(
            startHour = startHour.toInt(),
            showStartClockHour = false,
        ))
    }

    fun onStartMinuteClicked(startMinute: String) {
        updateState(state.value.copy(
            startMinute = startMinute.toInt(),
            showStartClockMinute = false,
        ))
    }

    fun onEndHourClicked(endHour: String) {
        updateState(state.value.copy(
            endHour = endHour.toInt(),
            showEndClockHour = false,
        ))
    }

    fun onEndMinuteClicked(endMinute: String) {
        updateState(state.value.copy(
            endMinute = endMinute.toInt(),
            showEndClockMinute = false,
        ))

    }

    fun onDropArrowStartHourClicked() {
        updateState(state.value.copy(
            showStartClockHour = true,
        ))
    }

    fun onDropArrowSecondHourClicked() {
        updateState(state.value.copy(
            showSecondClockHour = true,
        ))

    }

    fun onDropArrowSecondMinuteClicked() {
        updateState(state.value.copy(
            showSecondClockMinute = true,
        ))
    }

    fun onDropArrowStartMinuteClicked() {
        updateState(state.value.copy(
            showStartClockMinute = true,
        ))
    }

    fun onDropArrowEndHourClicked() {
        updateState(state.value.copy(
            showEndClockHour = true,
        ))
    }

    fun onDropArrowEndMinuteClicked() {
        updateState(state.value.copy(
            showEndClockMinute = true,
        ))
    }

    fun onDismissSaveDialog (){
        updateState(state.value.copy(
            showSaveDialog = false,
        ))
    }

    fun onButtonClicked() {
        val workTime = calculateAmountOfHours(
            state.value.startHour,
            state.value.startMinute,
            state.value.endHour,
            state.value.endMinute,
            state.value.secondHour,
            state.value.secondMinute,
        )
        updateState(state.value.copy(
            workTime = workTime,
            showSaveDialog = true,
        ))
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(
        val showStartClockHour: Boolean = false,
        val showStartClockMinute: Boolean = false,
        val showEndClockHour: Boolean = false,
        val showEndClockMinute: Boolean = false,
        val showSaveDialog: Boolean = false,
        val showSecondClockHour: Boolean = false,
        val showSecondClockMinute: Boolean = false,
        val secondHour: Int = 0,
        val secondMinute: Int = 0,
        val startHour: Int = 0,
        val startMinute: Int = 0,
        val endHour: Int = 0,
        val endMinute: Int = 0,
        val extraTime: Int = 0,
        val workTime: DateTime? = null,
        val clockHour: List<String> = listOf<String>("00",
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
        val secondClockHour: List<String> = listOf<String>(
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
        val secondClockMinute: List<String> = listOf<String>(
            "00",
            "10",
            "20",
            "30",
            "40",
            "50",
            "60"),
    )
}