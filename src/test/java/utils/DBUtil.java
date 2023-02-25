package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    private static String url = ConfigReader.getProperty("DBUrl");
    private static String username = ConfigReader.getProperty("DBUsername");
    private static String password = ConfigReader.getProperty("DBPassword");

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static Connection createDBConnection(){
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection is successful");
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
        return connection;
    }

    public static void executeQuery(String query){
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<List<Object>> getQueryResultList(String query){
//        List<String> column = new ArrayList<>();
//        try {
//            connection = DriverManager.getConnection(url, username, password);
//            statement = connection.createStatement();
//            resultSet = statement.executeQuery(query);
//        }catch(SQLException e){
//            throw new RuntimeException(e);
//        }
    }

}
