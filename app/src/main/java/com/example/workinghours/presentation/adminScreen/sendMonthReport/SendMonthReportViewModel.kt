package com.example.workinghours.presentation.adminScreen.sendMonthReport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workinghours.domain.model.WorkData
import com.example.workinghours.domain.usecase.GenerateMonthReportUseCase
import com.example.workinghours.domain.usecase.GetDateFromOneMonthUseCase
import com.example.workinghours.presentation.model.DataToExcelFile
import com.example.workinghours.ui.MonthPickerGridOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.joda.time.DateTime
import org.joda.time.LocalDate
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SendMonthReportViewModel @Inject constructor(
    private val getDateFromOneMonth: GetDateFromOneMonthUseCase,
    private val generateMonthReport: GenerateMonthReportUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(ViewModelState())
    val state: StateFlow<ViewModelState> = _state

    fun minusYear() {
        val newYear = _state.value.yearOnScreen
        updateState(
            _state.value.copy(
                yearOnScreen = LocalDate(
                    newYear.minusYears(1)
                ),
                year = _state.value.year.minus(1)
            )
        )
    }

    fun addYear() {
        val newYear = _state.value.yearOnScreen
        updateState(
            _state.value.copy(
                yearOnScreen = LocalDate(
                    newYear.plusYears(1)
                ),
                year = _state.value.year.plus(1)
            )
        )
    }

    fun sendMontReport(onResult: (List<DataToExcelFile>) -> Unit) {
        viewModelScope.launch {
            val getDateFromOneMonth: List<WorkData> =
                getDateFromOneMonth(_state.value.year, _state.value.month)
            updateState(
                _state.value.copy(
                    oneMonthWorkDate = getDateFromOneMonth
                )
            )
            onResult(state.value.dataForExcel)
        }
    }

    fun onMonthClicked(year: Int, monthOfYear: Int) {
        updateState(
            _state.value.copy(
                year = year,
                month = monthOfYear,
                date = LocalDate(year, monthOfYear, 1)
            )
        )
    }

    fun onSendMonthReportClicked(onResult: (File?) -> Unit) {
        viewModelScope.launch {
            val fileWithReport = generateMonthReport()
            onResult(fileWithReport)
        }
    }

    private fun updateState(state: ViewModelState) {
        this._state.value = state
    }

    data class ViewModelState(
        val date: LocalDate = LocalDate(),
        val yearOnScreen: LocalDate = LocalDate(),
        val year: Int = LocalDate().year,
        val month: Int = LocalDate().monthOfYear,
        val oneMonthWorkDate: List<WorkData> = emptyList(),
    ) {
        val dataForExcel: List<DataToExcelFile> = oneMonthWorkDate.map {
            DataToExcelFile(
                workDate = it.userWorkDate.toStringOrEmptyDate(),
                userName = it.userName,
                workAmount = it.amountWorkTime.toStringOrEmpty(),
                startWorkTime = it.startWorkTime.toStringOrEmpty(),
                endWorkTime = it.endWorkTime.toStringOrEmpty(),
                hygieneTime = it.hygieneWorkTime.toStringOrEmpty(),
            )
        }

        private fun DateTime.toStringOrEmpty(): String {
            val patternForTime = "HH:mm"
            return toString(patternForTime) ?: ""
        }

        private fun LocalDate.toStringOrEmptyDate(): String {
            val patternForTime = "dd/MM"
            return toString(patternForTime) ?: ""
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