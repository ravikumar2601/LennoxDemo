package utilsBrowser.Excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class Excel {

    public String readExcel(String sheet, int row, int col) throws IOException {
        File f = new File("src/test/resources/testData/TestDataLen.xlsx");
        FileInputStream stream = new FileInputStream(f);
        Workbook w = new XSSFWorkbook(stream);
        Sheet s = w.getSheet(sheet);
        String name = null;
        Row r = s.getRow(row);
        Cell c = r.getCell(col);
        CellType type = c.getCellType();
        if (type == CellType.STRING) {
            name = c.getStringCellValue();
        } else if (type == CellType.NUMERIC) {
            double d = c.getNumericCellValue();
            long l = (long) d;
            name = String.valueOf(l);
        }
        return name;
    }

    public void writeExcel(String sheetName, String value, int col) throws IOException {
        File file = new File("src/test/resources/testData/TestDataLen.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = wb.getSheet(sheetName);
        Cell cell = null;

//        int rowNum = sheet.getLastRowNum() + 1;
//
//        Row row = sheet.createRow(rowNum);
//        int rowNum = sheet.getLastRowNum() + 1;

        Row row = sheet.createRow(1);

//        for (int j = 0; j < value.size(); j++) {
        cell = row.createCell(col);
        cell.setCellValue(value);
//        }
        FileOutputStream fileOut = new FileOutputStream("src/test/resources/testData/TestDataLen.xlsx");
        wb.write(fileOut);
    }

    public void writeMultipleValuesInExcel(String sheetName, List<String> value) throws IOException {
        File file = new File("src/test/resources/testData/TestDataLen.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
        XSSFSheet sheet = wb.getSheet(sheetName);
        Cell cell = null;

//        int rowNum = sheet.getLastRowNum() + 1;
//
//        Row row = sheet.createRow(rowNum);
//        int rowNum = sheet.getLastRowNum() + 1;

        Row row = sheet.createRow(1);

        for (int j = 0; j < value.size(); j++) {
        cell = row.createCell(j);
        cell.setCellValue(value.get(j));
        }
        FileOutputStream fileOut = new FileOutputStream("src/test/resources/testData/TestDataLen.xlsx");
        wb.write(fileOut);
    }
}
