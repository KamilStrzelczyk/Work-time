package com.example

import android.content.Context
import com.example.workinghours.presentation.model.DataToExcelFile
import dagger.hilt.android.qualifiers.ApplicationContext
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ExcelFileFormatter @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    fun createWorkBook(listOfData: List<DataToExcelFile>): Workbook {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Styczeń")
        addData(sheet, listOfData)
        return workbook
    }

    fun createExel(workbook: Workbook, fileName: String) {
        val addData = File(context.filesDir, "$fileName.xlsx")
        try {
            val filedOut = FileOutputStream(addData)
            workbook.write(filedOut)
            filedOut.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createCell(sheetRow: Row, columnIndex: Int, cellValue: String?) {
        //create a cell at a passed in index
        val ourCell = sheetRow.createCell(columnIndex)
        //add the value to it
        //a cell can be empty. That's why its nullable
        ourCell?.setCellValue(cellValue)
    }

    private fun addData(sheet: Sheet, listOfData: List<DataToExcelFile>) {
        var rowIndex = 2
        var cellIndex = 1
        var cellForAmount = 4
        val row1 = sheet.createRow(0)
        val row2 = sheet.createRow(1)

        createCell(row1, 0, "Data")
        createCell(row2, 1, "Rozpoczęcie Pracy")
        createCell(row2, 2, "Koniec Pracy")
        createCell(row2, 3, "Higieny")
        createCell(row2, 4, "Suma")

        listOfData.forEach {
            val row = sheet.createRow(rowIndex++)
            val row0 = sheet.createRow(0)
            val row3 = sheet.createRow(2)
            val cell0 = row0.createCell(1)
            cell0.setCellValue(it.userName)
            val cell1 = row.createCell(0)
            cell1.setCellValue(it.workDate)
            val cell2 = row.createCell(1)
            cell2.setCellValue(it.startWorkTime)
            val cell3 = row.createCell(2)
            cell3.setCellValue(it.endWorkTime)
            val cell4 = row.createCell(3)
            cell4.setCellValue(it.hygieneTime)
            val cell5 = row.createCell(cellForAmount)
            cell5.setCellValue(it.workAmount)
            cellForAmount += 4
        }
    }
}