package customer_service_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/CustomerServiceManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "123456anuj@";

    public static Connection getConnection() throws SQLException {
        // Optional: Load the MySQL JDBC driver explicitly (not always necessary with recent JDBC versions)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found.", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
