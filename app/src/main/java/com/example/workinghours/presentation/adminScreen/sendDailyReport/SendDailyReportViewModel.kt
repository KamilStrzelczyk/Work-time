package com.example.workinghours.presentation.adminScreen.sendDailyReport

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.CreateDailyReportUseCase
import com.example.workinghours.domain.usecase.GetDateFromOneDayUseCase
import com.example.workinghours.presentation.model.DataToExcelFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SendDailyReportViewModel @Inject constructor(
    private val getDateFromOneDay: GetDateFromOneDayUseCase,
    private val createDailyReport: CreateDailyReportUseCase,
) : ViewModel() {

    val state = MutableStateFlow(ViewModelState())

    init {
        viewModelScope.launch {
            val getDateFromOneDay = getDateFromOneDay(currentDate = state.value.currentDate)
            updateState(
                state.value.copy(
                    currentWorkDate = getDateFromOneDay
                )
            )
        }
    }

    fun onDateClicked(date: LocalDate) {
        updateState(
            state.value.copy(
                dateFromCalendar = date
            )
        )
    }

    fun onSendReportClicked() {
        createDailyReport
    }

    private fun updateState(state: ViewModelState) {
        this.state.value = state
    }

    data class ViewModelState(
        val dateFromCalendar: LocalDate = LocalDate(),
        val currentDate: LocalDate = LocalDate(1674514800000),
        val currentWorkDate: List<WorkData> = emptyList(),

        ) {
        val dataForExcel: List<DataToExcelFile> = currentWorkDate.map {
            DataToExcelFile(
                workDate = it.userWorkDate.toStringOrEmptyDate(),
                userName = it.id.toString(),
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