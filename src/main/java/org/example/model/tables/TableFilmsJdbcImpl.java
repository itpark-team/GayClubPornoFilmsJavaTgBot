package org.example.model.tables;

import org.apache.commons.lang3.StringUtils;
import org.example.model.entities.Film;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TableFilmsJdbcImpl implements TableFilms {
    private final Connection connection;

    public TableFilmsJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    public void addNew(Film newFilm) throws Exception {

        Statement statement = connection.createStatement();

        String insertQuery = String.format("INSERT INTO films (chat_id, name, tags, url) VALUES (%d,'%s','%s', '%s')", newFilm.getChatId(), newFilm.getName(), newFilm.getTags(), newFilm.getUrl());

        statement.executeUpdate(insertQuery);

        statement.close();
    }

    public List<Film> getAll() throws Exception {

        List<Film> films = new ArrayList<>();

        Statement statement = connection.createStatement();

        String selectQuery = String.format("SELECT * FROM films ORDER BY id ASC");

        ResultSet resultSet = statement.executeQuery(selectQuery);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            long chatId = resultSet.getLong("chat_id");
            String name = resultSet.getString("name");
            String tags = resultSet.getString("tags");
            String url = resultSet.getString("url");

            films.add(new Film(id, chatId, name, tags, url));
        }

        resultSet.close();

        statement.close();

        return films;
    }

    public List<Film> getAllFromStartId(long startId) throws Exception {

        List<Film> films = new ArrayList<>();

        Statement statement = connection.createStatement();

        String selectQuery = String.format("SELECT * FROM films WHERE id>=%d ORDER BY id ASC", startId);

        ResultSet resultSet = statement.executeQuery(selectQuery);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            long chatId = resultSet.getLong("chat_id");
            String name = resultSet.getString("name");
            String tags = resultSet.getString("tags");
            String url = resultSet.getString("url");

            films.add(new Film(id, chatId, name, tags, url));
        }

        resultSet.close();

        statement.close();

        return films;
    }


    public List<Film> getAllByIdOrNameOrTag(String searchValue) throws Exception {

        List<Film> films = new ArrayList<>();

        Statement statement = connection.createStatement();

        String selectQuery = "";

        if (StringUtils.isNumeric(searchValue)) {
            selectQuery = String.format("SELECT * FROM films WHERE id=%s OR name LIKE '%%%s%%' OR tags LIKE '%%%s%%' ORDER BY id ASC", searchValue, searchValue, searchValue);
        } else {
            selectQuery = String.format("SELECT * FROM films WHERE name LIKE '%%%s%%' OR tags LIKE '%%%s%%' ORDER BY id ASC", searchValue, searchValue);
        }

        ResultSet resultSet = statement.executeQuery(selectQuery);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            long chatId = resultSet.getLong("chat_id");
            String name = resultSet.getString("name");
            String tags = resultSet.getString("tags");
            String url = resultSet.getString("url");

            films.add(new Film(id, chatId, name, tags, url));
        }

        resultSet.close();

        statement.close();

        return films;
    }

    public List<Film> getAllByIdOrNameOrTagFromId(String searchValue, long startId) throws Exception {

        List<Film> films = new ArrayList<>();

        Statement statement = connection.createStatement();

        String selectQuery = String.format("SELECT * FROM films WHERE (name LIKE '%%%s%%' OR tags LIKE '%%%s%%') AND id>=%d ORDER BY id ASC", searchValue, searchValue, startId);

        ResultSet resultSet = statement.executeQuery(selectQuery);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            long chatId = resultSet.getLong("chat_id");
            String name = resultSet.getString("name");
            String tags = resultSet.getString("tags");
            String url = resultSet.getString("url");

            films.add(new Film(id, chatId, name, tags, url));
        }

        resultSet.close();

        statement.close();

        return films;
    }

}
