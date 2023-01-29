package com.example.workinghours.infrastructure.utils

import android.content.Context
import com.example.workinghours.domain.model.Day
import dagger.hilt.android.qualifiers.ApplicationContext
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.joda.time.LocalDate
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class ReportGenerator @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun generateReportForMonth(
        daysOfCurrentMonth: List<Day>,
        data: Map<String, List<ReportWorkData>>,
    ): File? =
        generateReport(
            reportFile = File(context.filesDir, "$MONTHLY_REPORT_FILE_NAME$FILE_EXTENSION"),
            days = daysOfCurrentMonth,
            data = data,
        )

    fun generateReportForDay(
        day: Day,
        data: Map<String, List<ReportWorkData>>,
    ): File? = generateReport(
        reportFile = File(context.filesDir, "$DAILY_REPORT_FILE_NAME$FILE_EXTENSION"),
        days = listOf(day),
        data = data,
    )

    private fun generateReport(
        reportFile: File,
        days: List<Day>,
        data: Map<String, List<ReportWorkData>>,
    ) = runCatching {
        val filedOut = FileOutputStream(reportFile)
        createWorkBook(days, data).write(filedOut)
        filedOut.close()
        return@runCatching reportFile
    }
        .onFailure { it.printStackTrace() }
        .getOrNull()

    private fun createWorkBook(
        days: List<Day>,
        data: Map<String, List<ReportWorkData>>,
    ): Workbook {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Styczeń")
        writeData(sheet, days, data)
        return workbook
    }

    private fun writeData(
        sheet: XSSFSheet,
        days: List<Day>,
        data: Map<String, List<ReportWorkData>>,
    ) {
        val names = data.keys.toList()
        val userDataHeaders = listOf(
            START_TIME_HEADER,
            END_TIME_HEADER,
            AMOUNT_OF_HYGIENE_HEADER,
            TOTAL_AMOUNT_HEADER
        )
        sheet.insertUserNames(
            names = names,
            userNamesRowIndex = 0,
            startColumnIndex = 1,
            amountOfCellsToMerge = userDataHeaders.size,
        )
        val headersRow = sheet.createRow(1)
        insertDateHeader(
            headersRow = headersRow,
            columnIndex = 0,
        )
        insertUserDataHeaders(
            userDataHeaders = userDataHeaders,
            headersRow = headersRow,
            startColumnIndex = 1,
            iterations = names.size,
        )
        sheet.insetWorkData(
            days = days,
            data = data,
            startRowIndex = 2,
        )
    }

    private fun Sheet.insertUserNames(
        names: List<String>,
        userNamesRowIndex: Int,
        startColumnIndex: Int,
        amountOfCellsToMerge: Int,
    ) {
        val namesRow = createRow(userNamesRowIndex)
        var firstIndex = startColumnIndex
        var lastIndex = amountOfCellsToMerge
        names.forEach { name ->
            addMergedRegion(
                CellRangeAddress(
                    userNamesRowIndex,
                    userNamesRowIndex,
                    firstIndex,
                    lastIndex
                )
            )
            createCell(namesRow, firstIndex, name)
            firstIndex = lastIndex + 1
            lastIndex = firstIndex + amountOfCellsToMerge - 1
        }
    }

    private fun insertDateHeader(headersRow: Row, columnIndex: Int) {
        createCell(headersRow, columnIndex, DATE_HEADER)
    }

    private fun insertUserDataHeaders(
        userDataHeaders: List<String>,
        headersRow: Row,
        startColumnIndex: Int,
        iterations: Int,
    ) {

        var firstIndex = startColumnIndex
        repeat(iterations) {
            userDataHeaders.forEachIndexed { headerIndex, header ->
                val columnIndex = firstIndex + headerIndex
                createCell(headersRow, columnIndex, header)
            }
            firstIndex += userDataHeaders.size
        }
    }

    private fun Sheet.insetWorkData(
        days: List<Day>,
        data: Map<String, List<ReportWorkData>>,
        startRowIndex: Int
    ) {
        days.forEachIndexed { index, day ->
            val row = createRow(startRowIndex + index)
            createCell(row, 0, day.date.toString(DATE_FORMAT))

            var firstIndex = 1
            data.values.forEach { reportWorkData ->
                reportWorkData.firstOrNull { it.data == day.date }
                    ?.run {
                        createCell(row, firstIndex, startTime)
                        createCell(row, firstIndex + 1, endTime)
                        createCell(row, firstIndex + 2, hygiene)
                        createCell(row, firstIndex + 3, totalAmount)
                    }
                firstIndex += 4
            }
        }
    }

    private fun createCell(sheetRow: Row, columnIndex: Int, cellValue: String?) {
        val ourCell = sheetRow.createCell(columnIndex)
        ourCell?.setCellValue(cellValue)
    }

    data class ReportWorkData(
        val data: LocalDate,
        val startTime: String,
        val endTime: String,
        val hygiene: String,
        val totalAmount: String,
    )

    companion object {
        private const val FILE_EXTENSION = ".xlsx"
        private const val DAILY_REPORT_FILE_NAME = "Day Report"
        private const val MONTHLY_REPORT_FILE_NAME = "Month Report"
        private const val DATE_HEADER = "Data"
        private const val START_TIME_HEADER = "Godzina rozpoczęcia"
        private const val END_TIME_HEADER = "Godzina zakończenia"
        private const val AMOUNT_OF_HYGIENE_HEADER = "Ilość higien"
        private const val TOTAL_AMOUNT_HEADER = "Suma"
        private const val DATE_FORMAT = "dd/MM"
        const val HOUR_FORMAT = "HH:mm"
    }
}