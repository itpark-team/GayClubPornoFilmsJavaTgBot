package org.example.service.menupoints;

import org.example.model.DbManager;
import org.example.model.entities.Film;
import org.example.statemachine.TransmittedData;
import org.example.util.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class ShowFilmsService {
    private DbManager dbManager;

    public ShowFilmsService() throws Exception {
        dbManager = DbManager.getInstance();
    }

    public SendMessage processClickInShowFilms(String callBackData, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (callBackData.equals(ButtonsStorage.BackToMenuMainInShowFilms.getCallBackData())) {
            return SharedService.goToProcessClickInMenuMain(transmittedData);
        } else if (callBackData.equals(ButtonsStorage.ShowMoreInShowFilms.getCallBackData())) {

            long startId = (long) transmittedData.getDataStorage().get(SystemStringsStorage.DataStorageShowFilmLastId);
            List<Film> films = dbManager.getTableFilms().getAllFromId(startId);
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

            return message;
        }

        throw new Exception("Ошибка распознавания callBackData");
    }
}
