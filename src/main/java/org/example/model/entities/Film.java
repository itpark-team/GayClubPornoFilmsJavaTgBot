package org.example.model.entities;

public class Film {
    private final long id;
    private final long chatId;
    private final String name;
    private final String tags;
    private final String url;

    public Film(long id, long chatId, String name, String tags, String url) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.tags = tags;
        this.url = url;
    }

    public Film(long chatId, String name, String tags, String url) {
        this.id = 0;
        this.chatId = chatId;
        this.name = name;
        this.tags = tags;
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public long getChatId() {
        return chatId;
    }

    public String getName() {
        return name;
    }

    public String getTags() {
        return tags;
    }

    public String getUrl() {
        return url;
    }
}
