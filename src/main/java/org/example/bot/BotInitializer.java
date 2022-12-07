package org.example.bot;

import org.example.statemachine.ChatRouter;
import org.example.util.SystemStringsStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class BotInitializer extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(BotInitializer.class);

    private final ChatRouter chatRouter;

    public BotInitializer() throws Exception {
        chatRouter = new ChatRouter();
    }

    @Override
    public String getBotUsername() {
        return "GayClubBlueAndroid2020";
    }

    @Override
    public String getBotToken() {
        return "5384747123:AAHYso67z4GII5nTy0lnv2o8mth73OS2Kxw";
    }

    @Override
    public void onUpdateReceived(Update update) {
        UpdateHandlerMultiThread updateHandlerMultiThread = new UpdateHandlerMultiThread(chatRouter, update, this);
        updateHandlerMultiThread.start();
    }
}
