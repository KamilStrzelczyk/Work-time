package com.example.workinghours.presentation.adminScreen.sendDailyReport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.GetDateFromOneDayUseCase
import com.example.workinghours.presentation.model.DataToExcelFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SendDailyReportViewModel @Inject constructor(
    private val getDateFromOneDay: GetDateFromOneDayUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewModelState())
    val state: StateFlow<ViewModelState> = _state

    fun onDateClicked(date: LocalDate) {
        updateState(
            _state.value.copy(
                dateFromCalendar = date
            )
        )
    }

    fun onSendReportClicked(onResult: (List<DataToExcelFile>) -> Unit) {
        viewModelScope.launch {
            val getDateFromOneDay: List<WorkData> =
                getDateFromOneDay(_state.value.dateFromCalendar)
            updateState(
                _state.value.copy(
                    oneDayWorkDate = getDateFromOneDay
                )
            )
            onResult(state.value.dataForExcel)
        }
    }

    private fun updateState(state: ViewModelState) {
        this._state.value = state
    }

    data class ViewModelState(
        val dateFromCalendar: LocalDate = LocalDate(),
        val oneDayWorkDate: List<WorkData> = emptyList(),
    ) {
        val dataForExcel: List<DataToExcelFile> = oneDayWorkDate.map {
            DataToExcelFile(
                workDate = it.userWorkDate.toStringOrEmptyDate(),
                userName = it.userName,
                workAmount = it.amountWorkTime.toStringOrEmpty(),
                startWorkTime = it.startWorkTime.toStringOrEmpty(),
                endWorkTime = it.endWorkTime.toStringOrEmpty(),
                hygieneTime = it.hygieneWorkTime.toStringOrEmpty(),
            )
        }

        private fun DateTime?.toStringOrEmpty(): String {
            val patternForTime = "HH:mm"
            return this?.toString(patternForTime) ?: ""
        }

        private fun LocalDate.toStringOrEmptyDate(): String {
            val patternForTime = "dd/MM"
            return this?.toString(patternForTime) ?: ""
        }

    }
}