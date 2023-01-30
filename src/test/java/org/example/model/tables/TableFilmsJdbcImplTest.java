package org.example.model.tables;

import org.example.model.connection.DbConnection;
import org.example.model.entities.Film;
import org.example.util.SystemStringsStorage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TableFilmsJdbcImplTest {
    @Test
    public void getAll_Return3Films() throws Exception {
        //подготовка
        DbConnection dbConnection = new DbConnection(
                SystemStringsStorage.TestDbUrl,
                SystemStringsStorage.TestDbUser,
                SystemStringsStorage.TestDbPassword
        );

        TableFilms tableFilms = new TableFilmsJdbcImpl(dbConnection.getConnection());

        //тестирование
        List<Film> films = tableFilms.getAll();

        int expectedSize = 4;
        int actualSize = films.size();

        //проверка
        assertThat(actualSize).isEqualTo(expectedSize);
    }
}