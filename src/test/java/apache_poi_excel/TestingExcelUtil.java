package apache_poi_excel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ExcelUtil;

public class TestingExcelUtil {
    private static Logger logger = LogManager.getLogger(TestingExcelUtil.class);
    public static void main(String[] args) {

        logger.info("Opening the Excel file");

        ExcelUtil.openExcelFile("ReadData", "Sheet1");

        System.out.println(ExcelUtil.getValues());

//        String cellValue= ExcelUtil.getValue(1, 1);
//
//        logger.info("Cell value is '" + cellValue +"'");




    }
}
