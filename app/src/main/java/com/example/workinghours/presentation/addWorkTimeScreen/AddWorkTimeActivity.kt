package com.example.workinghours.presentation.addWorkTimeScreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddWorkTimeActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: AddWorkTimeViewModel.Factory

    private val addWorkTimeViewModel: AddWorkTimeViewModel by viewModels {
        provideAddWorkTimeViewModelFactory(
            factory = viewModelFactory,
            userId = intent.getIntExtra(USER_ID, 0),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colors.background) {
                WorkingHoursTheme {
                    AddWorkTimeScreen(viewModel = addWorkTimeViewModel)
                }
            }
        }
    }

    private fun provideAddWorkTimeViewModelFactory(
        factory: AddWorkTimeViewModel.Factory,
        userId: Int,
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(userId) as T
            }
        }
    }

    companion object {

        private val USER_ID = "user_id"
        fun createStartIntent(context: Context, userId: Int): Intent {
            return Intent(context, AddWorkTimeActivity::class.java).apply {
                putExtra(USER_ID, userId)
            }
        }
    }
}

