package org.example.service.menupoints;

import org.example.model.DbManager;
import org.example.model.entities.Film;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;

import org.example.util.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AddNewFimService {

    private DbManager dbManager;

    public AddNewFimService(DbManager dbManager) {
        this.dbManager = dbManager;
    }

    public SendMessage processInputFilmNameInAddFilm(String filmName, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (filmName.length() < Constants.FilmNameMinLength || filmName.length() > Constants.FilmNameMaxLength) {

            message.setText(DialogStringsStorage.InputFilmNameInAddFilmError);
            return message;
        }

        message.setText(DialogStringsStorage.InputFilmNameInAddFilmOk);

        transmittedData.getDataStorage().addOrUpdate(SystemStringsStorage.DataStorageAddNewFilmName, filmName);
        transmittedData.setState(State.InputFilmUrlInAddFilm);
        return message;
    }

    public SendMessage processInputFilmUrlInAddFilm(String filmUrl, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (filmUrl.length() > Constants.FilmUrlMaxLength ||
                !(filmUrl.startsWith(SystemStringsStorage.Http) ||
                        filmUrl.startsWith(SystemStringsStorage.Https))
        ) {
            message.setText(DialogStringsStorage.InputFilmUrlInAddFilmError);
            return message;
        }

        message.setText(DialogStringsStorage.InputFilmUrlInAddFilmOk);

        transmittedData.getDataStorage().addOrUpdate(SystemStringsStorage.DataStorageAddNewFilmUrl, filmUrl);
        transmittedData.setState(State.InputFilmTagsInAddFilm);
        return message;
    }

    public SendMessage processInputFilmTagsInAddFilm(String filmTags, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (filmTags.length() < Constants.FilmTagsMinLength || filmTags.length() > Constants.FilmTagsMaxLength) {
            message.setText(DialogStringsStorage.InputFilmTagsInAddFilmError);
            return message;
        }

        String filmName = (String) transmittedData.getDataStorage().get(SystemStringsStorage.DataStorageAddNewFilmName);
        String filmUrl = (String) transmittedData.getDataStorage().get(SystemStringsStorage.DataStorageAddNewFilmUrl);

        Film film = Film.builder()
                .chatId(transmittedData.getChatId())
                .name(filmName)
                .url(filmUrl)
                .tags(filmTags)
                .build();


        dbManager.getTableFilms().addNew(film);

        message.setText(DialogStringsStorage.createInputFilmTagsInAddFilmOk(film));
        message.setReplyMarkup(InlineKeyboardsMarkupStorage.getBackToMenuMainInAddFilm());

        transmittedData.setState(State.ClickInAddFilm);
        return message;
    }

    public SendMessage processClickBackToMenuMainInAddFilm(String callBackData, TransmittedData transmittedData) throws Exception {

        if (callBackData.equals(ButtonsStorage.BackToMenuMain.getCallBackData())) {
            return SharedService.goToProcessClickInMenuMain(transmittedData);
        }
        throw new Exception("Ошибка распознавания callBackData");
    }
}
