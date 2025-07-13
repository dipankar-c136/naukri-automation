/*
package com.naukri.utils;

import org.apache.poi.ss.usermodel.Cell;
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

    */
/*public String[][] readExcelData(String sheetName) throws Throwable {
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
    }*//*

    public String[][] readExcelData(String sheetName) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found");
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                throw new IllegalStateException("Excel file must contain at least header row and one data row");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Header row is missing");
            }

            int columnCount = headerRow.getPhysicalNumberOfCells();
            String[][] data = new String[rowCount - 1][columnCount];

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    for (int j = 0; j < columnCount; j++) {
                        Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        data[i - 1][j] = cell != null ? cell.toString() : "";
                    }
                } else {
                    for (int j = 0; j < columnCount; j++) {
                        data[i - 1][j] = "";
                    }
                }
            }
            return data;
        }
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
}*/
//=======================******************************************************************=======================
package com.naukri.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelUtils {

    public String excelFilePath;

    public ExcelUtils(String excelFilePath) {
        this.excelFilePath = excelFilePath;
    }

    private InputStream getExcelInputStream() throws IOException {
        // Try to load from classpath
        InputStream is = getClass().getClassLoader().getResourceAsStream(excelFilePath);
        if (is != null) {
            return is;
        }
        // Fallback to file system
        File file = new File(excelFilePath);
        if (file.exists()) {
            return new FileInputStream(file);
        }
        throw new FileNotFoundException("Excel file not found in classpath or file system: " + excelFilePath);
    }

    public String[][] readExcelData(String sheetName) throws IOException {
        try (InputStream is = getExcelInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet '" + sheetName + "' not found");
            }

            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                throw new IllegalStateException("Excel file must contain at least header row and one data row");
            }

            Row headerRow = sheet.getRow(0);
            if (headerRow == null) {
                throw new IllegalStateException("Header row is missing");
            }

            int columnCount = headerRow.getPhysicalNumberOfCells();
            String[][] data = new String[rowCount - 1][columnCount];

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < columnCount; j++) {
                    Cell cell = row != null ? row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL) : null;
                    data[i - 1][j] = cell != null ? cell.toString() : "";
                }
            }
            return data;
        }
    }

    public void writeExcelData(String sheetName, String[] data) throws IOException {
        // Only supports writing to file system, not classpath resources
        try (FileInputStream fileInputStream = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getPhysicalNumberOfRows();
            Row row = sheet.createRow(rowCount);

            for (int i = 0; i < data.length; i++) {
                row.createCell(i).setCellValue(data[i]);
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOutputStream);
            }
        }
    }
}