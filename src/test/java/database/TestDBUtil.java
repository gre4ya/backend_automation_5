package database;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.DBUtil;

import java.util.List;

public class TestDBUtil {

    @BeforeMethod
    public void setDB(){
        DBUtil.createDBConnection();
    }
    @Test
    public void executeDatabase(){
        List<List<Object>> result = DBUtil.getQueryResultList("select first_name, last_name from employees");
        System.out.println(result);
    }
}
