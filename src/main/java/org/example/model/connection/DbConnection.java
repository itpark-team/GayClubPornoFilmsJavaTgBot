package org.example.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private final Connection connection;

    private DbConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://194.67.105.79:5432/gayclubpornofilmsdb", "gayclubpornofilmsuser", "12345");
    }

    public Connection getConnection() {
        return connection;
    }

    private static DbConnection instance = null;

    public static DbConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }
}
