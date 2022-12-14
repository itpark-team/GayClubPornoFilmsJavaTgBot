package org.example.util;

import org.example.model.entities.Film;


public class DialogStringsStorage {

    public final static String CommandStartHello = "Выберите действие";
    public final static String CommandStartError = "Команда не распознана. Для начала работы с ботом введите /start";

    public final static String InputFilmNameInAddFilm = "Введите название фильма(от 3 до 128 символов)";
    public final static String InputFilmNameInAddFilmError = "Ошибка! Неверное название фильма. Введите название фильма(от 3 до 128 символов)";
    public final static String InputFilmNameInAddFilmOk = "Название фильма сохранено. Пожалуйста введите ссылку на фильм";

    public final static String InputFilmUrlInAddFilmError = "Ошибка! Невеврное ссылка на фильм. Ссылка не должна быть длиннее 2048 символов и начинаться с http:// или c https://";

    public final static String InputFilmUrlInAddFilmOk = "Ссылка сохранена. Введите теги фильма через точку с запятой(;) от 3 до 512 символов";

    public final static String InputFilmTagsInAddFilmError = "Ошибка! Неверный ввод тегов. Длина строки тегов от 3 до 512 символов)";

    public static String createInputFilmTagsInAddFilmOk(Film film) {
        return String.format("Фильм: %s\nСсылка: %s\nТеги: %s\nуспешно сохранён", film.getName(), film.getUrl(), film.getTags());
    }

    public static String createShowFilm(Film film) {
        return String.format("ИД: %d\nФильм: %s\nСсылка: %s\nТеги: %s\n", film.getId(), film.getName(), film.getUrl(), film.getTags());
    }

    public final static String InputSearchValueInFindFilms = "Введите ИД или название или тег фильма для поиска";

    public final static String InputSearchValueInFindFilmsError = "Ошибка! Нарушен лимит символов поискового запроса. Введите значение от 1 до 128 символов";

    public final static String NoFilms = "Фильмы не обнаружены";
}

