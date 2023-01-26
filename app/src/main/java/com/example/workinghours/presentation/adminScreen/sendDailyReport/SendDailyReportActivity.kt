package com.example.workinghours.presentation.adminScreen.sendDailyReport

import android.content.ActivityNotFoundException
import android.content.Intent
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
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class SendDailyReportActivity : ComponentActivity() {
    @Inject
    lateinit var excelFileFormatter: ExcelFileFormatter
    private val sendDailyReportViewModel: SendDailyReportViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                SendDailyReportScreen(
                    viewModel = sendDailyReportViewModel,
                    onSendClicked = {
                        excelFileFormatter.createExel(
                            workbook = excelFileFormatter.createWorkBook(it),
                            fileName = "Daily Report",
                        )
                        val file = File("/data/data/com.example.workinghours/files/excel.xlsx")
                        val file2 = FileProvider.getUriForFile(
                            this,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file
                        )

                        val i = Intent(Intent.ACTION_SEND)
                        i.type = "message/rfc822"
                        i.putExtra(Intent.EXTRA_EMAIL, arrayOf("strzelcod@gmail.com"))
                        i.putExtra(Intent.EXTRA_SUBJECT, "Working Hour File")
                        i.putExtra(Intent.EXTRA_TEXT, "This is file!")
                        i.putExtra(Intent.EXTRA_STREAM, file2)
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."))
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