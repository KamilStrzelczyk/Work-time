package com.example

import android.content.Context
import com.example.workinghours.presentation.model.DataToExcelFile
import dagger.hilt.android.qualifiers.ApplicationContext
import org.apache.poi.ss.usermodel.*

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class ExcelFileFormatter @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    open fun createWorkBook2(listOfData: List<DataToExcelFile>): Workbook {
        val workbook = XSSFWorkbook()

        val sheet = workbook.createSheet("Styczeń")
        addData(sheet, listOfData)
        return workbook
    }


    open fun createExel(workbook: Workbook, fileName: String) {

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

    fun addData(sheet: Sheet, listOfData: List<DataToExcelFile>) {
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
//        listOfData.forEach {
//            val row = sheet.createRow(rowIndex++)
//            val row0 = sheet.createRow(0)
//            val cell0 = row0.createCell(1)
//            cell0.setCellValue(it.userName)
//            val cell1 = row.createCell(0)
//            cell1.setCellValue(it.workDate)
//            val cell2 = row.createCell(1)
//            cell2.setCellValue(it.startWorkTime)
//            val cell3 = row.createCell(2)
//            cell3.setCellValue(it.endWorkTime)
//            val cell4 = row.createCell(3)
//            cell4.setCellValue(it.hygieneTime)
//            val cell5 = row.createCell(4)
//            cell5.setCellValue(it.workAmount)
//        }


//        val row1 = sheet.createRow(0)
//        val row2 = sheet.createRow(1)
//        val row3 = sheet.createRow(2)
//
//        val headerCell = createCell(row1, 1, "Kasia")
//        val headerNextCell = createCell(row1, (5), "Małgosia")
//        val headerNextCell2 = createCell(row1, (9), "Dominik")
//        val headerNextCell3 = createCell(row1, (12), "Basia") // co 4 kolumna z nazwiskiem
//        val headerNextCell4 = createCell(row1, (17), "Agnieszka")
////header
//        headerCell
//        headerNextCell
//        headerNextCell2
//        headerNextCell3
//        headerNextCell4
//        createCell(row1, 0, "Data")
////        createCell(row1, 1, "Imie Nazwisko") // różne nazwiska
//        createCell(row2, 1, "Rozpoczęcie Pracy")
//        createCell(row2, 2, "Koniec Pracy")
//        createCell(row2, 3, "Higieny")
//        createCell(row2, 4, "Suma")
////
//        createCell(row3, 0, "23.01")// powinien wypisać wszystkie daty w danym miesiącu
//        createCell(row3, 1, "8.30")
//        createCell(row3, 2, "16.30")
//        createCell(row3, 3, "1:0")
    }
}