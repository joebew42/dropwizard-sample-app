package com.evilcorp.sampleapp.infrastructure;

import com.evilcorp.sampleapp.models.Note;
import com.evilcorp.sampleapp.models.NotesRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryNotesRepository implements NotesRepository {

    private static final Map<Integer, Note> notes = new HashMap<Integer, Note>();

    public Note findBy(Integer id) {
        return notes.get(id);
    }

    public List<Note> findAll() {
        return new ArrayList<>(notes.values())
                .stream()
                .sorted((note, otherNote) -> note.getId() > otherNote.getId() ? -1 : 1)
                .collect(Collectors.toList());
    }

    public Note create(Note note) {
        synchronized (notes) {
            note.setId(notes.size() + 1);
            notes.put(note.getId(), note);
        }

        return note;
    }
}
