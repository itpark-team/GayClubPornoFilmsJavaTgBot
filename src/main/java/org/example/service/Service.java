package org.example.service;

import org.example.statemachine.TransmittedData;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Service {
    SendMessage processUpdate(String textData, TransmittedData transmittedData) throws Exception;
}
