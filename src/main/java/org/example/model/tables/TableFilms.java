package org.example.model.tables;

import org.example.model.entities.Film;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TableFilms {
    private final Connection connection;

    public TableFilms(Connection connection) {
        this.connection = connection;
    }

    public void addNew(Film newFilm) throws SQLException {
        Statement statement = connection.createStatement();

        String insertQuery = String.format("INSERT INTO films (chat_id, name, tags, url) VALUES (%d,'%s','%s', '%s')", newFilm.getChatId(), newFilm.getName(), newFilm.getTags(), newFilm.getUrl());

        statement.executeUpdate(insertQuery);

        statement.close();
    }

    public List<Film> getAll() throws SQLException {

        List<Film> films = new ArrayList<>();

        Statement statement = connection.createStatement();

        String selectQuery = String.format("SELECT * FROM films ORDER BY id ASC");

        ResultSet resultSet = statement.executeQuery(selectQuery);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            long chatId = resultSet.getLong("chat_id");
            String name  = resultSet.getString("name");
            String tags  = resultSet.getString("tags");
            String url  = resultSet.getString("url");

            films.add(new Film(id, chatId, name, tags, url));
        }

        resultSet.close();

        statement.close();

        return films;
    }
}
