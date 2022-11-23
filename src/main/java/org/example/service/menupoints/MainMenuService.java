package org.example.service.menupoints;




import org.example.model.DbManager;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.ButtonsStorage;
import org.example.util.DialogStringsStorage;
import org.example.util.SystemStringsStorage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
        }
        else if (callBackData.equals(ButtonsStorage.ShowAllFilmsInMenuMain.getCallBackData())) {
            message.setText("нажата кнопка: "+ButtonsStorage.ShowAllFilmsInMenuMain.getName());
            return message;
        }
        else if (callBackData.equals(ButtonsStorage.FindFilmInMenuMain.getCallBackData())) {
            message.setText("нажата кнопка: "+ButtonsStorage.FindFilmInMenuMain.getName());
            return message;
        }

        throw new Exception("Ошибка распознавания callBackData");
    }
}
