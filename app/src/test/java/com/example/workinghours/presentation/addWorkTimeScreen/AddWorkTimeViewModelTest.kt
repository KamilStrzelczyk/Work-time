package com.example.workinghours.presentation.addWorkTimeScreen

import com.example.workinghours.domain.provider.DateProvider
import com.example.workinghours.domain.usecase.CalculateAmountOfHoursUseCase
import com.example.workinghours.domain.usecase.GetCurrentDateAndTimeUseCase
import com.example.workinghours.domain.usecase.SaveUserWorkDataUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
internal class AddWorkTimeViewModelTest {
    private val dateProvider: DateProvider = mockk()
    private val calculateAmountOfHours = CalculateAmountOfHoursUseCase(
        dateProvider
    )
    private val saveUserWorkData: SaveUserWorkDataUseCase = mockk()
    private val getCurrentDateAndTime: GetCurrentDateAndTimeUseCase = mockk()
    private val userId: Int = 1
    private val userName: String = "Paweł"

    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun before() {
        Dispatchers.setMain(dispatcher)
        every { dateProvider.getLocalDateNow() } returns LocalDate(1673996400000)
    }

    @Test
    fun `WHEN user clicked on save in dialog THEN data was saved and save dialog was not displayed`() {
        // GIVEN
        coEvery { getCurrentDateAndTime.invoke() } returns flow {
            emit(DateTime(1645443000000))
        }
        coEvery { saveUserWorkData.invoke(any()) } returns Unit
        val expectedState = AddWorkTimeViewModel.ViewModelState(
            showSaveDialog = false,
        )
        val viewModel = AddWorkTimeViewModel(
            calculateAmountOfHours = calculateAmountOfHours,
            saveUserWorkData = saveUserWorkData,
            getCurrentDateAndTime = getCurrentDateAndTime,
            userId = userId,
            userName = userName,
        )
        viewModel.onFirstSaveButtonClicked()

        // WHEN
        viewModel.onDialogSaveButtonClicked()

        // THEN
        assertEquals(expectedState.showSaveDialog, viewModel.state.value.showSaveDialog)
    }

    @Test
    fun `WHEN user clicked on onDismissSaveDialog THEN dialog will be not displayed`() {
        // GIVEN
        coEvery { getCurrentDateAndTime.invoke() } returns flow {
            emit(DateTime(1645443000000))
        }
        coEvery { saveUserWorkData.invoke(any()) } returns Unit
        val expectedState = AddWorkTimeViewModel.ViewModelState(
            showSaveDialog = false,
        )
        val viewModel = AddWorkTimeViewModel(
            calculateAmountOfHours = calculateAmountOfHours,
            saveUserWorkData = saveUserWorkData,
            getCurrentDateAndTime = getCurrentDateAndTime,
            userId = userId,
            userName = userName,
        )
        viewModel.onFirstSaveButtonClicked()

        // WHEN
        viewModel.onDismissSaveDialog()

        // THEN
        assertEquals(expectedState.showSaveDialog, viewModel.state.value.showSaveDialog)
    }

    @Test
    fun `WHEN user clicked on onFirstSaveButtonClicked THEN will be displayed dialog`() {
        // GIVEN
        coEvery { getCurrentDateAndTime.invoke() } returns flow {
            emit(DateTime(1645443000000))
        }
        coEvery { saveUserWorkData.invoke(any()) } returns Unit
        val expectedState = AddWorkTimeViewModel.ViewModelState(
            showSaveDialog = true,
        )
        val viewModel = AddWorkTimeViewModel(
            calculateAmountOfHours = calculateAmountOfHours,
            saveUserWorkData = saveUserWorkData,
            getCurrentDateAndTime = getCurrentDateAndTime,
            userId = userId,
            userName = userName,
        )

        // WHEN
        viewModel.onFirstSaveButtonClicked()

        // THEN
        assertEquals(expectedState.showSaveDialog, viewModel.state.value.showSaveDialog)

    }

    @Test
    fun `WHEN user selected started hour THEN startWorkClock was update`() {
        // GIVEN
        coEvery { getCurrentDateAndTime.invoke() } returns flow {
            emit(DateTime(1645443000000))
        }
        coEvery { saveUserWorkData.invoke(any()) } returns Unit

        val viewModel = AddWorkTimeViewModel(
            calculateAmountOfHours = calculateAmountOfHours,
            saveUserWorkData = saveUserWorkData,
            getCurrentDateAndTime = getCurrentDateAndTime,
            userId = userId,
            userName = userName,
        )
        val expectedState = AddWorkTimeViewModel.ViewModelState(
            startWorkClock = AddWorkTimeViewModel.Clock(
                showMinuteClock = false,
                showClock = false,
                timeMinuteScope = viewModel.state.value.clockMinute,
                timeHourScope = viewModel.state.value.clockMinute,
                setHour = 12,
                setMinute = 35,
            )
        )
        val clock = AddWorkTimeViewModel.Clock(
            showMinuteClock = false,
            showClock = false,
            timeHourScope = viewModel.state.value.clockHour,
            timeMinuteScope = viewModel.state.value.clockMinute,
            setHour = 12,
            setMinute = 35,
        )
        // WHEN
        viewModel.updateStartWorkClock(clock)

        // THEN
        assertEquals(expectedState.startWorkClock, viewModel.state.value.showSaveDialog)
    }

    @Test
    fun updateEndWorkClock() {
        // GIVEN
        coEvery { getCurrentDateAndTime.invoke() } returns flow {
            emit(DateTime(1645443000000))
        }
        coEvery { saveUserWorkData.invoke(any()) } returns Unit

        val viewModel = AddWorkTimeViewModel(
            calculateAmountOfHours = calculateAmountOfHours,
            saveUserWorkData = saveUserWorkData,
            getCurrentDateAndTime = getCurrentDateAndTime,
            userId = userId,
            userName = userName,
        )
        val expectedState = AddWorkTimeViewModel.ViewModelState(
            startWorkClock = AddWorkTimeViewModel.Clock(
                showMinuteClock = false,
                showClock = false,
                timeMinuteScope = viewModel.state.value.clockMinute,
                timeHourScope = viewModel.state.value.clockMinute,
                setHour = 12,
                setMinute = 35,
            )
        )
        val clock = AddWorkTimeViewModel.Clock(
            showMinuteClock = false,
            showClock = false,
            timeHourScope = viewModel.state.value.clockHour,
            timeMinuteScope = viewModel.state.value.clockMinute,
            setHour = 12,
            setMinute = 35,
        )
        // WHEN
        viewModel.updateEndWorkClock(clock)

        // THEN
        assertEquals(expectedState.startWorkClock, viewModel.state.value.showSaveDialog)

    }

    @Test
    fun updateHygieneWorkClock() {
        // GIVEN
        coEvery { getCurrentDateAndTime.invoke() } returns flow {
            emit(DateTime(1645443000000))
        }
        coEvery { saveUserWorkData.invoke(any()) } returns Unit

        val viewModel = AddWorkTimeViewModel(
            calculateAmountOfHours = calculateAmountOfHours,
            saveUserWorkData = saveUserWorkData,
            getCurrentDateAndTime = getCurrentDateAndTime,
            userId = userId,
            userName = userName,
        )
        val expectedState = AddWorkTimeViewModel.ViewModelState(
            startWorkClock = AddWorkTimeViewModel.Clock(
                showMinuteClock = false,
                showClock = false,
                timeMinuteScope = viewModel.state.value.clockMinute,
                timeHourScope =viewModel.state.value.clockMinute,
                setHour = 12,
                setMinute = 35,
            )
        )
        val clock = AddWorkTimeViewModel.Clock(
            showMinuteClock = false,
            showClock = false,
            timeHourScope = viewModel.state.value.clockHour,
            timeMinuteScope = viewModel.state.value.clockMinute,
            setHour = 12,
            setMinute = 35,
        )
        // WHEN
        viewModel.updateHygieneWorkClock(clock)

        // THEN
        assertEquals(expectedState.startWorkClock, viewModel.state.value.showSaveDialog)
    }
    }

    @Test
    fun showCalendar() {
    }

    @Test
    fun onConfirmRangeOfDateClicked() {
    }

    @Test
    fun onTopAppBarMoreActionClicked() {
    }
}