package com.evilcorp.sampleapp.infrastructure;

import com.evilcorp.sampleapp.models.Note;
import com.evilcorp.sampleapp.models.NotesRepository;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class InMemoryNotesRepositoryIT {
    @Test public void
    it_creates_find_and_find_all_notes() {
        NotesRepository repository = new InMemoryNotesRepository();

        Note firstNote = repository.create(aNoteWith("First note"));
        Note lastNote = repository.create(aNoteWith("Another note"));

        assertEquals(firstNote, repository.findBy(firstNote.getId()));
        assertEquals(lastNote, repository.findBy(lastNote.getId()));

        assertEquals(asList(lastNote, firstNote), repository.findAll());
    }

    private Note aNoteWith(String message) {
        return Note.builder().withMessage(message).build();
    }
}
