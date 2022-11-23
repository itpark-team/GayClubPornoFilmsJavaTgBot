package org.example.util;

public class ButtonsStorage {
    public static class Button {
        private String name;
        private String callBackData;

        public Button(String name, String callBackData) {
            this.name = name;
            this.callBackData = callBackData;
        }

        public String getName() {
            return name;
        }

        public String getCallBackData() {
            return callBackData;
        }
    }

    public final static Button AddNewFilmInMenuMain = new Button("Добавить новый фильм", "AddNewFilmInMenuMain");
    public final static Button ShowAllFilmsInMenuMain = new Button("Показать все фильмы", "ShowAllFilmsInMenuMain");
    public final static Button FindFilmInMenuMain = new Button("Найти фильм по названию", "FindFilmInMenuMain");

}