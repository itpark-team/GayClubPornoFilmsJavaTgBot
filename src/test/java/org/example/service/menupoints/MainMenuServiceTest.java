package org.example.service.menupoints;

import org.assertj.core.api.Assert;
import org.example.statemachine.State;
import org.example.statemachine.TransmittedData;
import org.example.util.DialogStringsStorage;
import org.example.util.InlineKeyboardsMarkupStorage;
import org.example.util.SystemStringsStorage;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static org.assertj.core.api.Assertions.assertThat;

class MainMenuServiceTest {
    @Test
    public void processCommandStart_WrongCommandStart_ReturnCommandStartError() throws Exception {
        //подготовка
        MainMenuService mainMenuService = new MainMenuService(null);
        String command = "WrongCommand";
        TransmittedData transmittedData = new TransmittedData(0);

        //тестирование
        SendMessage message = mainMenuService.processCommandStart(command, transmittedData);

        String expectedText = DialogStringsStorage.CommandStartError;
        String actualText = message.getText();

        //проверка
        assertThat(actualText).isEqualTo(expectedText);
    }

    @Test
    public void processCommandStart_CommandStart_PrepareAndReturnMainMenu() throws Exception {
        //подготовка
        MainMenuService mainMenuService = new MainMenuService(null);
        String command = SystemStringsStorage.CommandStart;
        TransmittedData transmittedData = new TransmittedData(0);

        //тестирование
        SendMessage message = mainMenuService.processCommandStart(command, transmittedData);

        String expectedText = DialogStringsStorage.CommandStartHello;
        String actualText = message.getText();

        InlineKeyboardMarkup expectedKeyboard = InlineKeyboardsMarkupStorage.getMenuMain();
        InlineKeyboardMarkup actualKeyboard = (InlineKeyboardMarkup) message.getReplyMarkup();

        int expectedDataStorageSize = 0;
        int actualDataStorageSize = transmittedData.getDataStorage().size();

        State expectedState = State.ClickInMenuMain;
        State actualState = transmittedData.getState();

        //проверка
        assertThat(actualText).isEqualTo(expectedText);
        assertThat(actualKeyboard).isEqualTo(expectedKeyboard);
        assertThat(actualDataStorageSize).isEqualTo(expectedDataStorageSize);
        assertThat(actualState).isEqualTo(expectedState);
    }
}