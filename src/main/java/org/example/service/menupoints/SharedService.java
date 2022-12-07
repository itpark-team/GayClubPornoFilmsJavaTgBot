package org.example.service.menupoints;

import org.example.model.entities.Film;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.Constants;
import org.example.util.DialogStringsStorage;
import org.example.util.InlineKeyboardsMarkupStorage;
import org.example.util.SystemStringsStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class SharedService {
    public static SendMessage goToProcessClickInMenuMain(TransmittedData transmittedData) {

        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());
        message.setText(DialogStringsStorage.CommandStartHello);
        message.setReplyMarkup(InlineKeyboardsMarkupStorage.getMenuMain());

        transmittedData.getDataStorage().clear();
        transmittedData.setState(State.ClickInMenuMain);
        return message;
    }

    public static SendMessage showFilms(SendMessage message, List<Film> films, TransmittedData transmittedData) {
        boolean hasFit = true;
        StringBuilder stringBuilder = new StringBuilder();

        for (Film film : films) {
            int totalLength = stringBuilder.length();
            int newFilmLength = DialogStringsStorage.createShowFilm(film).length();

            if (totalLength + newFilmLength > Constants.MessageMaxLength) {
                hasFit = false;
                transmittedData.getDataStorage().addOrUpdate(SystemStringsStorage.DataStorageFilmLastId, film.getId());
                break;
            }

            stringBuilder.append(DialogStringsStorage.createShowFilm(film));
        }

        if (films.size() == 0) {
            message.setText(DialogStringsStorage.NoFilms);
        } else {
            message.setText(stringBuilder.toString());
        }

        if (hasFit) {
            message.setReplyMarkup(InlineKeyboardsMarkupStorage.getShowAllFilms());
        } else {
            message.setReplyMarkup(InlineKeyboardsMarkupStorage.getShowMoreFilms());
        }

        return message;
    }
}
