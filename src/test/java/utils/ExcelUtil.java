package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    private static Logger logger = LogManager.getLogger(ExcelUtil.class);
    private static XSSFWorkbook workbook;
    private static XSSFSheet sheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static String filepath;

    public static void openExcelFile (String fileName, String sheetName){
        filepath = "test_data/" + fileName + ".xlsx";

        try{
            FileInputStream fileInputStream = new FileInputStream(filepath);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);
            logger.info("Sheet '" + sheetName + "' was created");
        }catch(Exception e){
            logger.debug("'" + fileName + "' and '" + sheetName + "' cannot be found");
        }

    }

    public static String getValue(int rowNumber, int cellNumber){
        row = sheet.getRow(rowNumber);
        cell = row.getCell(cellNumber);
        return cell.toString();
    }

    public static List<List<String>> getValues(){

        //creating List of List to store all the values from the Excel file
        List<List<String>> allValues = new ArrayList<>();

        //creating loops for getting the rows
        for (int r = sheet.getFirstRowNum() + 1; r <= sheet.getLastRowNum(); r++) {

            //creating a row in the sheet
            row = sheet.getRow(r);
            List<String> rowValue = new ArrayList<>();

            for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
                rowValue.add(getValue(r, c));
            }
            allValues.add(rowValue);
        }
        return allValues;
    }


}
