package org.example.service.menupoints;


import org.example.model.DbManager;
import org.example.model.entities.Film;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class MainMenuService {
    private DbManager dbManager;

    public MainMenuService() throws Exception {
        dbManager = DbManager.getInstance();
    }

    public SendMessage processCommandStart(String command, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (!command.equals(SystemStringsStorage.CommandStart)) {
            message.setText(DialogStringsStorage.CommandStartError);
            return message;
        }

        return SharedService.goToProcessClickInMenuMain(transmittedData);
    }

    public SendMessage processClickInMenuMain(String callBackData, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (callBackData.equals(ButtonsStorage.AddNewFilmInMenuMain.getCallBackData())) {
            message.setText(DialogStringsStorage.InputFilmNameInAddFilmHello);
            transmittedData.setState(State.InputFilmNameInAddFilm);
            return message;
        } else if (callBackData.equals(ButtonsStorage.ShowAllFilmsInMenuMain.getCallBackData())) {

            List<Film> films = dbManager.getTableFilms().getAll();
            boolean hasFit = true;

            StringBuilder stringBuilder = new StringBuilder();

            for (Film film : films) {
                int totalLength = stringBuilder.length();
                int newFilmLength = DialogStringsStorage.createShowFilmInMenuMain(film).length();

                if (totalLength + newFilmLength > Constants.MessageMaxLength) {
                    hasFit = false;
                    transmittedData.getDataStorage().addOrUpdate(SystemStringsStorage.DataStorageShowFilmLastId, film.getId());
                    break;
                }

                stringBuilder.append(DialogStringsStorage.createShowFilmInMenuMain(film));
            }

            message.setText(stringBuilder.toString());

            if (hasFit) {
                message.setReplyMarkup(InlineKeyboardsMarkupStorage.getShowAllFilms());
            } else {
                message.setReplyMarkup(InlineKeyboardsMarkupStorage.getShowMoreFilms());
            }

            transmittedData.setState(State.ClickInShowFilms);
            return message;
        } else if (callBackData.equals(ButtonsStorage.FindFilmInMenuMain.getCallBackData())) {
            message.setText("нажата кнопка: " + ButtonsStorage.FindFilmInMenuMain.getName());
            return message;
        }

        throw new Exception("Ошибка распознавания callBackData");
    }
}
