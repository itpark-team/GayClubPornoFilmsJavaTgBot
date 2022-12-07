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

        if (callBackData.equals(ButtonsStorage.BackToMenuMain.getCallBackData())) {
            return SharedService.goToProcessClickInMenuMain(transmittedData);
        } else if (callBackData.equals(ButtonsStorage.ShowMore.getCallBackData())) {

            long startId = (long) transmittedData.getDataStorage().get(SystemStringsStorage.DataStorageFilmLastId);
            List<Film> films = dbManager.getTableFilms().getAllFromId(startId);

            return SharedService.showFilms(message, films, transmittedData);
        }

        throw new Exception("Ошибка распознавания callBackData");
    }
}
