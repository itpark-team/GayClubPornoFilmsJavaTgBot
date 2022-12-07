package org.example.statemachine;

public enum State {
    CommandStart,

    ClickInMenuMain,

    InputFilmNameInAddFilm,
    InputFilmUrlInAddFilm,
    InputFilmTagsInAddFilm,
    ClickInAddFilm,

    ClickMoreOrBackInShowFilms,

    InputSearchValueInFindFilm,
    ClickMoreOrBackToMenuMainInFindFilm,
}
