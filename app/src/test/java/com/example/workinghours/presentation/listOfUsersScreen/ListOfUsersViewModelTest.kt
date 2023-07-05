@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.workinghours.presentation.listOfUsersScreen


import com.example.Utils
import com.example.workinghours.domain.model.User
import com.example.workinghours.domain.usecase.GetAdminPasswordUseCase
import com.example.workinghours.domain.usecase.GetAllUsersUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class ListOfUsersViewModelTest {

    private val getAllUsers: GetAllUsersUseCase = mockk()
    private val getAdminPassword: GetAdminPasswordUseCase = mockk()
    private val usersList = listOf(User(1, "ABC", "1234"))
    private val password = "1234"

    private val dispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    private fun before() {
        Dispatchers.setMain(dispatcher)
        coEvery { getAllUsers.invoke() } returns usersList
        coEvery { getAdminPassword.invoke() } returns password
    }

    @AfterEach
    private fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN user click on user item THEN userId and userName will be saved `() {
        // GIVEN
        val expectedState = ListOfUsersViewModel.ViewModelState(
            userId = 1,
            userName = "ABC",
            userList = usersList,
            adminPassword = password,
        )
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )

        // WHEN
        listOfUsersViewModel.onUserNameBoxClicked(userId = 1, userName = "ABC")

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onTopAppBarMoreActionClicked THEN UserActionsDialog will be displayed`() {
        // GIVEN
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        val expectedState = listOfUsersViewModel.state.value.copy(
            showTopAppBarMoreAction = true,
            userList = usersList,
            adminPassword = password,
        )

        // WHEN
        listOfUsersViewModel.onTopAppBarMoreActionClicked()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked onDismissTopAppBarMoreAction THEN UserActionsDialog will be not displayed`() {
        // GIVEN
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        listOfUsersViewModel.onTopAppBarMoreActionClicked()
        val expectedState = listOfUsersViewModel.state.value.copy(
            showTopAppBarMoreAction = false,
            userList = usersList,
            adminPassword = password,
        )

        // WHEN
        listOfUsersViewModel.onDismissTopAppBarMoreAction()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onDismissUserActionsDialog THEN UserActionDialog will be not displayed`() {
        // GIVEN
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        listOfUsersViewModel.onUsersClicked()
        val expectedState = ListOfUsersViewModel.ViewModelState(
            showUserActionsDialog = false,
            userList = usersList,
            adminPassword = password,
        )

        // WHEN
        listOfUsersViewModel.onDismissUserActionsDialog()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onUsersClicked THEN UserActionDialog will be displayed`() {
        // GIVEN
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        val expectedState = ListOfUsersViewModel.ViewModelState(
            showUserActionsDialog = true,
            userList = usersList,
            adminPassword = password,
        )

        // WHEN
        listOfUsersViewModel.onUsersClicked()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onAdminClicked THEN AdminPasswordDialog will be displayed`() {
        // GIVEN
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        val expectedState = ListOfUsersViewModel.ViewModelState(
            showAdminPasswordDialog = true,
            userList = usersList,
            adminPassword = password,
        )

        // WHEN
        listOfUsersViewModel.onAdminClicked()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user clicked on onDismissAdminPasswordDialog THEN AdminPasswordDialog will be not displayed `() {
        // GIVEN
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        listOfUsersViewModel.onAdminClicked()
        val expectedState = ListOfUsersViewModel.ViewModelState(
            showAdminPasswordDialog = false,
            userList = usersList,
            adminPassword = password,
        )

        // WHEN
        listOfUsersViewModel.onDismissAdminPasswordDialog()

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user introduces password THEN passwordChange will be changed`() {
        // GIVEN
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        val expectedState = ListOfUsersViewModel.ViewModelState(
            password = password,
            userList = usersList,
            adminPassword = password,
        )

        // WHEN
        listOfUsersViewModel.onPasswordChange(password)

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user introduces correct password THEN dialog will be not displayed and showed admin screen`() {
        // GIVEN
        val expectedState = ListOfUsersViewModel.ViewModelState(
            showAdminPasswordDialog = false,
            correctPassword = true,
            password = Utils.EMPTY_STRING,
            isError = false,
            adminPassword = password,
            userList = usersList,
        )
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        listOfUsersViewModel.onPasswordChange("1234")
        // WHEN
        listOfUsersViewModel.onOkClicked { mockk() }

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }

    @Test
    fun `WHEN user introduces wrong password THEN error dialog will be displayed`() {
        // GIVEN
        val wrongPassword = "5423"
        val expectedState = ListOfUsersViewModel.ViewModelState(
            isError = true,
            password = wrongPassword,
            userList = usersList,
            adminPassword = password,
        )
        val listOfUsersViewModel = ListOfUsersViewModel(
            getAllUsers = getAllUsers,
            getAdminPassword = getAdminPassword,
        )
        listOfUsersViewModel.onPasswordChange(wrongPassword)

        // WHEN
        listOfUsersViewModel.onOkClicked { mockk() }

        // THEN
        assertEquals(expectedState, listOfUsersViewModel.state.value)
    }
}