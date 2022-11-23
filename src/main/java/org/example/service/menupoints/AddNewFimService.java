package org.example.service.menupoints;

import org.example.model.DbManager;
import org.example.model.entities.Film;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;

import org.example.util.Constants;
import org.example.util.DialogStringsStorage;
import org.example.util.SystemStringsStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AddNewFimService {
    public SendMessage processInputFilmNameInAddFilm(String filmName, TransmittedData transmittedData) throws Exception {
        SendMessage message = new SendMessage();
        message.setChatId(transmittedData.getChatId());

        if (filmName.length() < Constants.FilmNameMinLength || filmName.length() > Constants.FilmNameMaxLength) {

            message.setText(DialogStringsStorage.InputFilmNameInAddFilmError);
            return message;
        }

        message.setText(DialogStringsStorage.InputFilmNameInAddFilmOk);

        transmittedData.getDataStorage().add(SystemStringsStorage.DataStorageAddNewFilmName, filmName);
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

        transmittedData.getDataStorage().add(SystemStringsStorage.DataStorageAddNewFilmUrl, filmUrl);
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

        Film film = new Film(transmittedData.getChatId(), filmName, filmTags, filmUrl);
        DbManager.getInstance().getTableFilms().addNew(film);

        message.setText(DialogStringsStorage.createInputFilmTagsInAddFilmOk(filmName,filmUrl,filmTags));

        transmittedData.setState(State.CommandStart);
        return message;
    }
}
