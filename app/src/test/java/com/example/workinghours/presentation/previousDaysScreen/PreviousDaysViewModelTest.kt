package com.example.workinghours.presentation.previousDaysScreen

import com.example.workinghours.domain.model.DaysOfMonth
import com.example.workinghours.domain.provider.DateProvider
import com.example.workinghours.domain.usecase.GetDayOfMonthUseCase
import com.example.workinghours.domain.usecase.GetUserDateUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.joda.time.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach


@OptIn(ExperimentalCoroutinesApi::class)
internal class PreviousDaysViewModelTest {

    private val getUserDate: GetUserDateUseCase = mockk()
    private val getDayOfMonth: GetDayOfMonthUseCase = mockk()
    private val dateProvider: DateProvider = mockk()
    private val userId: Int = 1

    private val dispatcher = UnconfinedTestDispatcher()

    private val date = LocalDate()


    @BeforeEach
    private fun before() {
        Dispatchers.setMain(dispatcher)
        coEvery { getDayOfMonth(any()) } returns emptyList()
        coEvery { getUserDate(userId) } returns emptyList()
        every { dateProvider.getLocalDateNow() } returns date
    }

    @AfterEach
    private fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN user clicked in top app bar filter on all days THEN will be displayed all days in month`() {
        // GIVEN
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        listOfUsersViewModel.onTopAppBarFilterWorkDaysClicked()
        val expectedState = listOfUsersViewModel.state.value.copy(
            showOnlyWorkDays = false
        )

        // WHEN
        listOfUsersViewModel.onTopAppBarFilterAllDaysClicked()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onTopAppBarFilterWorkdaysClicked THEN will be displayed only work days`() {
        // GIVEN
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        val expectedState = listOfUsersViewModel.state.value.copy(
            showOnlyWorkDays = true
        )

        // WHEN
        listOfUsersViewModel.onTopAppBarFilterWorkDaysClicked()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onTopAppBarFilterClicked THEN will be displayed filter top app bar`() {
        // GIVEN
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        val expectedState = listOfUsersViewModel.state.value.copy(
            showFilterTopAppBar = true
        )

        // WHEN
        listOfUsersViewModel.onTopAppBarFilterClicked()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onDismissFilterTopAppBar THEN filter top app bar will by not displayed`() {
        // GIVEN
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        val expectedState = listOfUsersViewModel.state.value.copy(
            showFilterTopAppBar = false,
        )

        // WHEN
        listOfUsersViewModel.onDismissFilterTopAppBar()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on previous month THEN calendar will be displayed `() {
        // GIVEN
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        val expectedState = listOfUsersViewModel.state.value.copy(
            showCalendar = true,
        )

        // WHEN
        listOfUsersViewModel.showCalendar()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked onDismissCalendar THEN calnedar will be not displayed`() {
        // GIVEN
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        listOfUsersViewModel.showCalendar()
        val expectedState = listOfUsersViewModel.state.value.copy(
            showCalendar = false,
        )

        // WHEN
        listOfUsersViewModel.onDismissCalendar()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on minusYear THEN year will be subtracted and updated`() {
        // GIVEN
        val year = LocalDate(1640991600000)
        val subtractedYear = LocalDate(1609455600000)
        every { dateProvider.getLocalDateNow() } returns year
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        val expectedState = listOfUsersViewModel.state.value.copy(
            year = subtractedYear
        )

        // WHEN
        listOfUsersViewModel.minusYear()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on plusYear THEN year will be added and updated`() {
        // GIVEN
        val year = LocalDate(1640991600000)
        val addedYear = LocalDate(1672527600000)
        every { dateProvider.getLocalDateNow() } returns year
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        val expectedState = listOfUsersViewModel.state.value.copy(
            year = addedYear
        )

        // WHEN
        listOfUsersViewModel.addYear()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user selected month on calendar THEN month to show will be updated`() {
        // GIVEN
        val year = 2025
        val month = 6
        val calendarDate = LocalDate(1748728800000)
        val date = LocalDate(1685570400000)
        every { dateProvider.getLocalDateNow() } returns date
        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )
        val expectedState = listOfUsersViewModel.state.value.copy(
            calendarDate = calendarDate
        )

        // WHEN
        listOfUsersViewModel.onOtherMonthClicked(
            year = year,
            month = month,
        )

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onShowDateClicked THEN data from selected month will be displayed`() {
        // GIVEN
        val calendarDate = LocalDate(1748728800000)
        val daysOfMonth = listOf(DaysOfMonth(1, 21, 2022, LocalDate(1685570400000)))
        coEvery { getDayOfMonth(calendarDate) } returns daysOfMonth

        val date = LocalDate(1685570400000)
        every { dateProvider.getLocalDateNow() } returns date

        val listOfUsersViewModel = PreviousDaysViewModel(
            getUserDate = getUserDate,
            getDayOfMonth = getDayOfMonth,
            userId = userId,
            dateProvider = dateProvider,
        )

        val expectedState = listOfUsersViewModel.state.value.copy(
            daysOfMonth = daysOfMonth,
            showCalendar = false,
        )

        // WHEN
        listOfUsersViewModel.onShowDateClicked()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }
}