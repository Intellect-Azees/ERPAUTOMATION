package TestData;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class Test_Data
{
	String filePath ="D:\\aa\\e-PACS\\src\\test\\resources\\TestData\\TestData.xlsx";
	public String  dataUtility(String sheetname,int rownum,int cellnum) throws Exception
	{
		FileInputStream fis=new FileInputStream(filePath);
		Workbook book=WorkbookFactory.create(fis);
		Sheet exlsheet=book.getSheet(sheetname);
		DataFormatter formatter=new DataFormatter();
		String data=formatter.formatCellValue(exlsheet.getRow(rownum).getCell(cellnum));
		return data;
	}
	public void updateData(String sheetName, int rowNum, int cellNum, String newData) throws Exception {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            throw new RuntimeException("Row " + rowNum + " does not exist in the sheet.");
        }

        Cell cell = row.getCell(cellNum);
        if (cell == null) {
            throw new RuntimeException("Cell " + cellNum + " does not exist in the row.");
        }

        cell.setCellValue(newData);

        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
    }

}
