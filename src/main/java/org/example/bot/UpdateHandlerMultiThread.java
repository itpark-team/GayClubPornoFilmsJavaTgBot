package org.example.bot;

import org.example.statemachine.ChatRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class UpdateHandlerMultiThread extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(UpdateHandlerMultiThread.class);

    private ChatRouter chatRouter;
    private Update update;
    private TelegramLongPollingBot bot;

    public UpdateHandlerMultiThread(ChatRouter chatRouter, Update update, TelegramLongPollingBot bot) {
        this.chatRouter = chatRouter;
        this.update = update;
        this.bot = bot;
    }

    @Override
    public void run() {
        long chatId = 0;
        int messageId = 0;
        String textData = "";
        String updateType = "";

        try {
            if (update.hasMessage()) {
                chatId = update.getMessage().getChatId();
                messageId = update.getMessage().getMessageId();
                textData = update.getMessage().getText();
                updateType = "Message";
            } else if (update.hasCallbackQuery()) {
                chatId = update.getCallbackQuery().getMessage().getChatId();
                messageId = update.getCallbackQuery().getMessage().getMessageId();
                textData = update.getCallbackQuery().getData();
                updateType = "Callback";
            }
            logger.info(String.format("INPUT: %s %d:%d:%s", updateType, chatId, messageId, textData));
            SendMessage message = chatRouter.route(chatId, textData);

            InlineKeyboardMarkup inlineKeyboardMarkup = (InlineKeyboardMarkup) message.getReplyMarkup();

            String keyboardAsString = "Клавиатуры в данном сообщении нет";
            if (inlineKeyboardMarkup != null) {
                StringBuilder stringBuilder = new StringBuilder();

                for (var keyboard : inlineKeyboardMarkup.getKeyboard()) {
                    stringBuilder.append(keyboard.get(0).getText() + ";");
                }

                keyboardAsString = stringBuilder.toString();
            }
            logger.info(String.format("OUTPUT: %d:%d\ntext=%s\nkeyboard=%s", chatId, messageId, message.getText(), keyboardAsString));
            bot.execute(message);
        } catch (Exception e) {

            //e.printStackTrace();

            logger.info(String.format("ERROR: %s %d:%d:%s error=%s", updateType, chatId, messageId, textData, e.getMessage()));

            DeleteMessage message = new DeleteMessage();
            message.setChatId(chatId);
            message.setMessageId(messageId);

            try {
                bot.execute(message);
            } catch (TelegramApiException telegramApiException) {
                telegramApiException.printStackTrace();
            }
        }
    }
}
