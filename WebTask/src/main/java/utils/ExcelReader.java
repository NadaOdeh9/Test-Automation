package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	
private Workbook workbook;
    
    public ExcelReader(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            workbook = new XSSFWorkbook(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String[]> readSheetData(String sheetName) {
        List<String[]> data = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        
        if (sheet != null) {
            for (Row row : sheet) {
                String[] rowData = new String[row.getLastCellNum()];
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    rowData[i] = cell.getStringCellValue();
                }
                data.add(rowData);
            }
        }
        return data;
    }
}
