package org.example.service;

import org.example.model.DbManager;
import org.example.model.connection.DbConnection;
import org.example.model.connection.HibernateSession;
import org.example.model.tables.TableFilms;
import org.example.model.tables.TableFilmsHiberImpl;
import org.example.model.tables.TableFilmsJdbcImpl;
import org.example.service.menupoints.AddNewFimService;
import org.example.service.menupoints.FindFilmsService;
import org.example.service.menupoints.MainMenuService;

import org.example.service.menupoints.ShowFilmsService;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.SystemStringsStorage;
import org.hibernate.SessionFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private final Map<State, Service> methods;

    private final MainMenuService mainMenuService;
    private final AddNewFimService addNewFimService;
    private final ShowFilmsService showFilmsService;
    private final FindFilmsService findFilmsService;

    public ServiceManager() throws Exception {
        methods = new HashMap<>();

//        DbConnection dbConnection = new DbConnection(
//                SystemStringsStorage.DbUrl,
//                SystemStringsStorage.DbUser,
//                SystemStringsStorage.DbPassword
//        );
//
//        TableFilms tableFilms = new TableFilmsJdbcImpl(dbConnection.getConnection());

        SessionFactory sessionFactory = new HibernateSession().getSessionFactory();

        TableFilms tableFilms = new TableFilmsHiberImpl(sessionFactory);

        DbManager dbManager = new DbManager(tableFilms);

        mainMenuService = new MainMenuService(dbManager);
        addNewFimService = new AddNewFimService(dbManager);
        showFilmsService = new ShowFilmsService(dbManager);
        findFilmsService = new FindFilmsService(dbManager);

        methods.put(State.CommandStart, mainMenuService::processCommandStart);
        methods.put(State.ClickInMenuMain, mainMenuService::processClickInMenuMain);

        methods.put(State.InputFilmNameInAddFilm, addNewFimService::processInputFilmNameInAddFilm);
        methods.put(State.InputFilmUrlInAddFilm, addNewFimService::processInputFilmUrlInAddFilm);
        methods.put(State.InputFilmTagsInAddFilm, addNewFimService::processInputFilmTagsInAddFilm);
        methods.put(State.ClickInAddFilm, addNewFimService::processClickBackToMenuMainInAddFilm);

        methods.put(State.ClickInShowFilms, showFilmsService::processClickInShowFilms);

        methods.put(State.InputSearchValueInFindFilms, findFilmsService::processInputSearchValueInFindFilms);
        methods.put(State.ClickInFindFilm, findFilmsService::processClickInFindFilm);

    }

    public SendMessage processUpdate(String textData, TransmittedData transmittedData) throws Exception {
        return methods.get(transmittedData.getState()).processUpdate(textData, transmittedData);
    }

}
