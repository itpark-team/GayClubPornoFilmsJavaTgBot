package org.example.service.menupoints;

import org.example.model.DbManager;
import org.example.model.entities.Film;
import org.example.model.tables.TableFilms;
import org.example.statemachine.TransmittedData;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FindFilmsServiceTest {
    @Test
    public void processInputSearchValueInFindFilms_TagНю_ReturnFilmsMessageWithTagНю() throws Exception {
        //подготовка
        String searchValue = "ню";
        TransmittedData transmittedData = new TransmittedData(0);

        List<Film> films = Arrays.asList(
                new Film(1, 0, "Крик", "ню", "scream.сom"),
                new Film(1, 0, "Звонок", "ню", "bell.сom")
        );

        TableFilms tableFilms = Mockito.mock(TableFilms.class);
        when(tableFilms.getAllByIdOrNameOrTag("ню")).thenReturn(films);

        DbManager dbManager = new DbManager(tableFilms);

        FindFilmsService findFilmsService = new FindFilmsService(dbManager);

        SendMessage testMessage = new SendMessage();
        TransmittedData testTransmittedData = new TransmittedData(0);

        //тестирование
        SendMessage message = findFilmsService.processInputSearchValueInFindFilms(searchValue, transmittedData);

        String expectedText = SharedService.showFilms(testMessage, films, testTransmittedData).getText();
        String actualText = message.getText();

        //проверка
        assertThat(actualText).isEqualTo(expectedText);
    }
}