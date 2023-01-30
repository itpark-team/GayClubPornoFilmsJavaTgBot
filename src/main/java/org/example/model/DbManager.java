package org.example.model;

import org.example.model.tables.TableFilms;

public class DbManager {

    private final TableFilms tableFilms;

    public DbManager(TableFilms tableFilms) {
        this.tableFilms = tableFilms;
    }

    public TableFilms getTableFilms() {
        return tableFilms;
    }
}
