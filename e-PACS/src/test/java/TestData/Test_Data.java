package TestData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Test_Data {

    String projectPath = System.getProperty("user.dir");

    String excelFilePath = Paths.get(projectPath, "src", "test", "resources", "TestData", "TestData.xlsx").toString();
    String propertiesFilePath = Paths.get(projectPath, "src", "test", "java", "TestData", "LoginTestData.properties").toString();

    public String getLoginCredantials(String key) throws Exception {
        FileInputStream fis = new FileInputStream(propertiesFilePath);
        Properties prop = new Properties();
        prop.load(fis);
        return prop.getProperty(key);
    }

    public String dataUtility(String sheetName, int rowNum, int cellNum) throws Exception {
        FileInputStream fis = new FileInputStream(excelFilePath);
        Workbook book = WorkbookFactory.create(fis);
        Sheet sheet = book.getSheet(sheetName);
        DataFormatter formatter = new DataFormatter();
        String data = formatter.formatCellValue(sheet.getRow(rowNum).getCell(cellNum));
        book.close();
        return data;
    }

    public void updateData(String sheetName, int rowNum, int cellNum, String newData) throws Exception {
        FileInputStream fis = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        Cell cell = row.getCell(cellNum);
        if (cell == null) {
            cell = row.createCell(cellNum);
        }
        cell.setCellValue(newData);

        FileOutputStream fos = new FileOutputStream(excelFilePath);
        workbook.write(fos);
        fos.close();
        workbook.close();
    }
}
