package org.example.service.menupoints;

import org.example.model.DbManager;
import org.example.model.entities.Film;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.ButtonsStorage;
import org.example.util.Constants;
import org.example.util.DialogStringsStorage;
import org.example.util.SystemStringsStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public class FindFilmsService {
    private DbManager dbManager;

    public FindFilmsService(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public SendMessage processInputSearchValueInFindFilms(String searchValue, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (searchValue.length() < Constants.FilmSearchValueMinLength || searchValue.length() > Constants.FilmSearchValueMaxLength) {

            message.setText(DialogStringsStorage.InputSearchValueInFindFilmsError);
            return message;
        }

        List<Film> films = dbManager.getTableFilms().getAllByIdOrNameOrTag(searchValue);

        transmittedData.getDataStorage().addOrUpdate(SystemStringsStorage.DataStorageSearchValue, searchValue);
        transmittedData.setState(State.ClickInFindFilm);
        return SharedService.showFilms(message, films, transmittedData);
    }

    public SendMessage processClickInFindFilm(String callBackData, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (callBackData.equals(ButtonsStorage.BackToMenuMain.getCallBackData())) {
            return SharedService.goToProcessClickInMenuMain(transmittedData);
        } else if (callBackData.equals(ButtonsStorage.ShowMore.getCallBackData())) {

            long startId = (long) transmittedData.getDataStorage().get(SystemStringsStorage.DataStorageFilmLastId);

            String searchValue = (String) transmittedData.getDataStorage().get(SystemStringsStorage.DataStorageSearchValue);

            List<Film> films = dbManager.getTableFilms().getAllByIdOrNameOrTagFromId(searchValue, startId);

            return SharedService.showFilms(message, films, transmittedData);
        }

        throw new Exception("Ошибка распознавания callBackData");
    }
}
