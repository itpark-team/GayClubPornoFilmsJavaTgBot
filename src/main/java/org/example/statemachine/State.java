package org.example.statemachine;

public enum State {
    CommandStart,

    ClickInMenuMain,

    InputFilmNameInAddFilm,
    InputFilmUrlInAddFilm,
    InputFilmTagsInAddFilm,
    ClickInAddFilm,

    ClickInShowFilms,

    InputSearchValueInFindFilms,
    ClickInFindFilm,
}
