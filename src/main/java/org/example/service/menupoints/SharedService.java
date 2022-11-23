package org.example.service.menupoints;

import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.DialogStringsStorage;
import org.example.util.InlineKeyboardsMarkupStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
}
