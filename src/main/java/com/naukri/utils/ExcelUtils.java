package com.naukri.utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelUtils {

    public String excelFilePath;

    public ExcelUtils(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    public String[][] readExcelData(String sheetName) throws Throwable {
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();
        int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
        String[][] data = new String[rowCount - 1][columnCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < columnCount; j++) {
                data[i - 1][j] = row.getCell(j).getStringCellValue();
            }
        }

        workbook.close();
        fileInputStream.close();
        return data;
    }

    public void writeExcelData(String sheetName, String[] data) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet(sheetName);
        int rowCount = sheet.getPhysicalNumberOfRows();
        Row row = sheet.createRow(rowCount);
        
        for (int i = 0; i < data.length; i++) {
            row.createCell(i).setCellValue(data[i]);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        workbook.close();
        fileInputStream.close();
    }
}