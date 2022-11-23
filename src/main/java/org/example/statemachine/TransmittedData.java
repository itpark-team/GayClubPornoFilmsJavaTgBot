package org.example.statemachine;

public class TransmittedData {

    private State state;
    private final DataStorage dataStorage;
    private final long chatId;

    public TransmittedData(long chatId) {
        this.chatId = chatId;
        state = State.CommandStart;
        dataStorage = new DataStorage();
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public DataStorage getDataStorage() {
        return dataStorage;
    }

    public long getChatId() {
        return chatId;
    }
}
