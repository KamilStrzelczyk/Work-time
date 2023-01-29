package com.example.workinghours.presentation.adminScreen.sendMonthReport

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import com.example.ExcelFileFormatter
import com.example.workinghours.BuildConfig
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SendMonthReportActivity : ComponentActivity() {
    @Inject
    lateinit var excelFormatter: ExcelFileFormatter
    private val sendMonthReportViewModel: SendMonthReportViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                SendMonthReportScreen(
                    viewModel = sendMonthReportViewModel,
                    onMonthReportGenerated = { fileWithReport ->
                        if (fileWithReport == null) return@SendMonthReportScreen

                        val reportUri: Uri = FileProvider.getUriForFile(
                            this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            fileWithReport,
                        )

                        val shareFileIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "message/rfc822"
                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                            putExtra(
                                Intent.EXTRA_EMAIL,
                                arrayOf("strzelcod@gmail.com", "mzglinicki.96@gmail.com"),
                            )
                            putExtra(Intent.EXTRA_SUBJECT, "Working Hour File")
                            putExtra(Intent.EXTRA_TEXT, "This is file!")
                            putExtra(Intent.EXTRA_STREAM, reportUri)
                        }
                        try {
                            startActivity(Intent.createChooser(shareFileIntent, "Send mail..."))
                        } catch (ex: ActivityNotFoundException) {
                            Toast.makeText(
                                this,
                                "There are no email clients installed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }
        }
    }
}
