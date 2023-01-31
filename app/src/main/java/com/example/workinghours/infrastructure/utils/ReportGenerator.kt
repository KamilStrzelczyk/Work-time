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
        sheetName: String,
        daysOfCurrentMonth: List<Day>,
        data: Map<String, List<ReportWorkData>>,
    ): File? =
        generateReport(
            reportFile = File(context.filesDir, "$MONTHLY_REPORT_FILE_NAME$FILE_EXTENSION"),
            days = daysOfCurrentMonth,
            data = data,
            sheetName = sheetName,
        )

    fun generateReportForDay(
        sheetName: String,
        day: Day,
        data: Map<String, List<ReportWorkData>>,
    ): File? = generateReport(
        reportFile = File(context.filesDir, "$DAILY_REPORT_FILE_NAME$FILE_EXTENSION"),
        days = listOf(day),
        data = data,
        sheetName = sheetName,
    )

    private fun generateReport(
        sheetName: String,
        reportFile: File,
        days: List<Day>,
        data: Map<String, List<ReportWorkData>>,
    ) = runCatching {
        val filedOut = FileOutputStream(reportFile)
        createWorkBook(days = days, data = data, sheetName = sheetName).write(filedOut)
        filedOut.close()
        return@runCatching reportFile
    }
        .onFailure { it.printStackTrace() }
        .getOrNull()

    private fun createWorkBook(
        sheetName: String,
        days: List<Day>,
        data: Map<String, List<ReportWorkData>>,
    ): Workbook {
        val workbook = XSSFWorkbook()
        val cellStyleWithBorderAndColor = workbook.createCellStyle()
        val cellStyleWithBorder = workbook.createCellStyle()
        val sheet = workbook.createSheet(sheetName)
        cellStyleWithBorderAndColor.fillForegroundColor = IndexedColors.LIGHT_TURQUOISE.index
        cellStyleWithBorderAndColor.fillPattern = FillPatternType.SOLID_FOREGROUND
        cellStyleWithBorderAndColor.borderTop = BorderStyle.THIN;
        cellStyleWithBorderAndColor.borderBottom = BorderStyle.THIN;
        cellStyleWithBorderAndColor.borderLeft = BorderStyle.THIN;
        cellStyleWithBorderAndColor.borderRight = BorderStyle.THIN;
        cellStyleWithBorder.borderTop = BorderStyle.THIN;
        cellStyleWithBorder.borderBottom = BorderStyle.THIN;
        cellStyleWithBorder.borderLeft = BorderStyle.THIN;
        cellStyleWithBorder.borderRight = BorderStyle.THIN;
        writeData(
            sheet = sheet,
            days = days,
            data = data,
            cellStyleWithBorderAndColor = cellStyleWithBorderAndColor,
            cellStyleWithBorder = cellStyleWithBorder,
        )
        return workbook
    }

    private fun writeData(
        cellStyleWithBorder: CellStyle,
        cellStyleWithBorderAndColor: CellStyle,
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
            cellStyleWithBorder = cellStyleWithBorder,
        )
        val headersRow = sheet.createRow(1)
        insertDateHeader(
            headersRow = headersRow,
            columnIndex = 0,
            cellStyleWithBorderAndColor = cellStyleWithBorderAndColor,
        )
        insertUserDataHeaders(
            sheet = sheet,
            userDataHeaders = userDataHeaders,
            headersRow = headersRow,
            startColumnIndex = 1,
            iterations = names.size,
            cellStyleWithBorderAndColor = cellStyleWithBorderAndColor,
            cellStyleWithBorder = cellStyleWithBorder,
        )
        sheet.insetWorkData(
            days = days,
            data = data,
            startRowIndex = 2,
            cellStyleWithBorderAndColor = cellStyleWithBorderAndColor,
            cellStyleWithBorder = cellStyleWithBorder,
        )
    }

    private fun Sheet.insertUserNames(
        names: List<String>,
        userNamesRowIndex: Int,
        startColumnIndex: Int,
        amountOfCellsToMerge: Int,
        cellStyleWithBorder: CellStyle,
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
            createCell(namesRow, firstIndex, name, cellStyleWithBorder)
            firstIndex = lastIndex + 1
            lastIndex = firstIndex + amountOfCellsToMerge - 1
        }
    }

    private fun insertDateHeader(
        headersRow: Row,
        columnIndex: Int,
        cellStyleWithBorderAndColor: CellStyle,
    ) {
        createCell(headersRow, columnIndex, DATE_HEADER, cellStyleWithBorderAndColor)
    }

    private fun insertUserDataHeaders(
        sheet: Sheet,
        userDataHeaders: List<String>,
        headersRow: Row,
        startColumnIndex: Int,
        iterations: Int,
        cellStyleWithBorderAndColor: CellStyle,
        cellStyleWithBorder: CellStyle,
    ) {
        var firstIndex = startColumnIndex
        repeat(iterations) {
            userDataHeaders.forEachIndexed { headerIndex, header ->
                val columnIndex = firstIndex + headerIndex
                if (headerIndex == 3) {
                    createCell(headersRow, columnIndex, header, cellStyleWithBorderAndColor)
                } else
                    createCell(headersRow, columnIndex, header, cellStyleWithBorder)
            }
            firstIndex += userDataHeaders.size
        }
    }

    private fun Sheet.insetWorkData(
        days: List<Day>,
        data: Map<String, List<ReportWorkData>>,
        startRowIndex: Int,
        cellStyleWithBorderAndColor: CellStyle,
        cellStyleWithBorder: CellStyle,
    ) {
        days.forEachIndexed { index, day ->
            val row = createRow(startRowIndex + index)
            createCell(row, 0, day.date.toString(DATE_FORMAT), cellStyleWithBorderAndColor)
            var firstIndex = 1
            data.values.forEach { reportWorkData ->
                val totalAmountCell = row.createCell(firstIndex + 3)
                totalAmountCell.cellStyle = cellStyleWithBorderAndColor
                reportWorkData.firstOrNull { it.data == day.date }
                    ?.run {
                        createCell(row, firstIndex, startTime, cellStyleWithBorder)
                        createCell(row, firstIndex + 1, endTime, cellStyleWithBorder)
                        createCell(row, firstIndex + 2, hygiene, cellStyleWithBorder)
                        createCell(row, firstIndex + 3, totalAmount, cellStyleWithBorderAndColor)
                        totalAmountCell.setCellValue(totalAmount)
                    } ?: run {
                    val cell0 = row.createCell(firstIndex)
                    cell0.cellStyle = cellStyleWithBorder
                    val cell1 = row.createCell(firstIndex + 1)
                    cell1.cellStyle = cellStyleWithBorder
                    val cell2 = row.createCell(firstIndex + 2)
                    cell2.cellStyle = cellStyleWithBorder
                    val cell3 = row.createCell(firstIndex + 3)
                    cell3.cellStyle = cellStyleWithBorderAndColor
                }
                firstIndex += 4
            }
        }
    }

    private fun createCell(
        sheetRow: Row,
        columnIndex: Int,
        cellValue: String?,
        cellStyle: CellStyle? = null,
    ) {
        val ourCell = sheetRow.createCell(columnIndex)
        ourCell?.setCellValue(cellValue)
        cellStyle?.run {
            ourCell?.cellStyle = cellStyle
        }
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
        private const val START_TIME_HEADER = "Rozpoczęcie"
        private const val END_TIME_HEADER = "Zakończenie"
        private const val AMOUNT_OF_HYGIENE_HEADER = "Higieny"
        private const val TOTAL_AMOUNT_HEADER = "Suma"
        private const val DATE_FORMAT = "dd/MM"
        const val HOUR_FORMAT = "HH:mm"
    }
}