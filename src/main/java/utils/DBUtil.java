package utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {

    private static Connection connection;

    public static void establishConnection(String url, String username, String password) {
        try {
            LoggerUtil.info("Connecting to Database: " + url);
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LoggerUtil.error("Failed to connect to database: " + e.getMessage());
        }
    }

    public static List<Map<String, Object>> executeQuery(String query) {
        List<Map<String, Object>> results = new ArrayList<>();
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(metaData.getColumnName(i), resultSet.getObject(i));
                }
                results.add(row);
            }
        } catch (SQLException e) {
            LoggerUtil.error("Error executing query: " + e.getMessage());
        }
        return results;
    }

    public static int executeUpdate(String query) {
        try (Statement statement = connection.createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            LoggerUtil.error("Error executing update: " + e.getMessage());
            return -1;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                LoggerUtil.info("Database connection closed.");
            }
        } catch (SQLException e) {
            LoggerUtil.error("Error closing database connection: " + e.getMessage());
        }
    }
}
