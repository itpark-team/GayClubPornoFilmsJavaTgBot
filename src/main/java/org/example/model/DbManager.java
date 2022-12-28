package org.example.model;

import org.example.model.connection.DbConnection;
import org.example.model.tables.TableFilms;
import org.example.model.tables.TableFilmsImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class DbManager {

    private final TableFilms tableFilms;

    public DbManager(TableFilms tableFilms) {
        this.tableFilms = tableFilms;
    }

    public TableFilms getTableFilms() {
        return tableFilms;
    }
}
