package com.example

import android.content.Context
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.IndexedColorMap
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

open class ExcelClass @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    open fun createWorkBook(): Workbook {
        val workbook = XSSFWorkbook()

        val sheet: Sheet = workbook.createSheet("CZAS")

        val cellStyle = getHeaderStyle(workbook)

        createSheetHeader(cellStyle, sheet)

        addData(0, sheet)

        return workbook
    }

    private fun createSheetHeader(cellStyle: CellStyle, sheet: Sheet) {

        val row = sheet.createRow(0)

        val HEADER_LIST = listOf("column_1", "column_2", "column_3")

        for ((index, value) in HEADER_LIST.withIndex()) {
            val columnWidth = (15 * 500)

            sheet.setColumnWidth(index, columnWidth)

            val cell = row.createCell(index)

            cell?.setCellValue(value)

            cell.cellStyle = cellStyle
        }
    }

    private fun getHeaderStyle(workbook: Workbook): CellStyle {

        val cellStyle: CellStyle = workbook.createCellStyle()

        val colorMap: IndexedColorMap = (workbook as XSSFWorkbook).stylesSource.indexedColors
        var color = XSSFColor(IndexedColors.INDIGO, colorMap).indexed
        cellStyle.fillForegroundColor = color
        cellStyle.fillPattern = FillPatternType.SOLID_FOREGROUND

        val whiteFont = workbook.createFont()
        color = XSSFColor(IndexedColors.INDIGO, colorMap).indexed
        whiteFont.color = color
        whiteFont.bold = true
        cellStyle.setFont(whiteFont)

        return cellStyle
    }

    private fun addData(rowIndex: Int, sheet: Sheet) {
        val row = sheet.createRow(rowIndex)

        createCell(row, 0, "value 1")
        createCell(row, 1, "value 2")
        createCell(row, 2, "value 3")
    }

    private fun createCell(row: Row, columnIndex: Int, value: String?) {
        val cell = row.createCell(columnIndex)
        cell?.setCellValue(value)
    }

    open fun createExel(workbook: Workbook, fileName: String) {
        val excelFile = File(context.filesDir, "$fileName.xlsx")
        try {
            val filedOut = FileOutputStream(excelFile)
            workbook.write(filedOut)
            filedOut.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}