package org.example.model.tables;

import org.example.model.entities.Film;

import java.util.List;

public interface TableFilms {
    void addNew(Film newFilm) throws Exception;
    List<Film> getAll() throws Exception;
    List<Film> getAllFromStartId(long startId) throws Exception;
    List<Film> getAllByIdOrNameOrTag(String searchValue) throws Exception;
    List<Film> getAllByIdOrNameOrTagFromId(String searchValue, long startId) throws Exception;
}
