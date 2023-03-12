package apache_poi_excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadingFromExcelFile {

    static Logger logger = LogManager.getLogger(ReadingFromExcelFile.class);

    public static void main(String[] args) throws IOException {

        // assign the file path
        String excelFilePath = "test_data/ReadData.xlsx";

        // reaching out the file
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);

        // opening the file where we specify the path
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

        // going into the specific sheet in the workbook
        XSSFSheet sheet = workbook.getSheet("Sheet1");

        // getting the first name from the file
        String firstName = sheet.getRow(1).getCell(0).getStringCellValue();
        logger.info("First name from the first cell is " + firstName);

        // getting second id from the file
        int second_id = (int)sheet.getRow(2).getCell(1).getNumericCellValue();
        logger.info("Second id value is " + second_id);

        //getting the last row number from the file
        int lastRaw = sheet.getLastRowNum();
        logger.info("The last row number is " + lastRaw);

        //getting the last cell number from the file
        int lastCell = sheet.getRow(0).getLastCellNum();
        logger.info("The last cell number is " + lastCell);

        // Looping each data
        for(int r = 0; r <= lastRaw; r++){

            XSSFRow row = sheet.getRow(r);
            for (int c = 0; c < lastCell; c++) {

                XSSFCell cell = row.getCell(c);
                System.out.print(cell + " | ");
            }
            System.out.println();
        }
    }
}
