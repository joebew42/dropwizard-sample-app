package com.evilcorp.sampleapp.models;

import java.util.List;

public interface NotesRepository {
    Note findBy(Integer id);

    List<Note> findAll();

    Note create(Note note);
}
