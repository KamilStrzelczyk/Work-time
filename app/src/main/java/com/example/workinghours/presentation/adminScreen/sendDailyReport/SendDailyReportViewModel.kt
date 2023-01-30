package com.example.workinghours.presentation.adminScreen.sendDailyReport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.GenerateDailyReportUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.LocalDate
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SendDailyReportViewModel @Inject constructor(
    private val generateDailyReport: GenerateDailyReportUseCase,
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

    fun onSendReportClicked(onResult: (File?) -> Unit) {
        viewModelScope.launch {
            val fileWithReport = generateDailyReport(
                date = _state.value.dateFromCalendar
            )
            onResult(fileWithReport)
        }
    }

    private fun updateState(state: ViewModelState) {
        this._state.value = state
    }

    data class ViewModelState(
        val dateFromCalendar: LocalDate = LocalDate(),
        val oneDayWorkDate: List<WorkData> = emptyList(),
    )
}