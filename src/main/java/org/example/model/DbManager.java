package org.example.model;

import org.example.model.connection.DbConnection;
import org.example.model.tables.TableFilms;

import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {

    private final TableFilms tableFilms;

    private DbManager() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        tableFilms = new TableFilms(connection);
    }

    public TableFilms getTableFilms() {
        return tableFilms;
    }

    private static DbManager instance;

    public static DbManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }
}
