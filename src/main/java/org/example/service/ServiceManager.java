package org.example.service;

import org.example.service.menupoints.AddNewFimService;
import org.example.service.menupoints.MainMenuService;

import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;

public class ServiceManager {
    private final Map<State, Service> methods;

    private final MainMenuService mainMenuService;
    private final AddNewFimService addNewFimService;

    public ServiceManager() throws Exception {
        methods = new HashMap<>();

        mainMenuService = new MainMenuService();
        addNewFimService = new AddNewFimService();

        methods.put(State.CommandStart, mainMenuService::processCommandStart);
        methods.put(State.ClickInMenuMain, mainMenuService::processClickInMenuMain);

        methods.put(State.InputFilmNameInAddFilm, addNewFimService::processInputFilmNameInAddFilm);
        methods.put(State.InputFilmUrlInAddFilm, addNewFimService::processInputFilmUrlInAddFilm);
        methods.put(State.InputFilmTagsInAddFilm, addNewFimService::processInputFilmTagsInAddFilm);
    }

    public SendMessage processUpdate(String textData, TransmittedData transmittedData) throws Exception {
        return methods.get(transmittedData.getState()).processUpdate(textData, transmittedData);
    }

}