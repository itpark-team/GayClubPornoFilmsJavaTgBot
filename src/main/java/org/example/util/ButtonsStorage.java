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
    public final static Button FindFilmsInMenuMain = new Button("Найти фильм", "FindFilmInMenuMain");
    public final static Button BackToMenuMainInAddFilm = new Button("В главное меню", "BackToMenuMainInAddFilm");


    public final static Button ShowMoreInShowFilms = new Button("Показать ещё", "ShowMoreInShowFilms");

    public final static Button BackToMenuMainInShowFilms = new Button("В главное меню", "BackToMenuMainInShowFilms");

}
