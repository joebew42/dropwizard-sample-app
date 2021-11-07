package com.evilcorp.sampleapp.infrastructure;

import com.evilcorp.sampleapp.models.Note;
import com.evilcorp.sampleapp.models.NotesRepository;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class InMemoryNotesRepositoryIT {
    @Test public void
    it_creates_find_and_find_all_notes() throws Exception {
        NotesRepository repository = new InMemoryNotesRepository();

        Note note = repository.create(Note.builder().withMessage("First note").build());

        assertEquals(new Integer(1), note.getId());
        assertEquals("First note", note.getMessage());

        assertEquals(note, repository.findBy(1));

        Note secondNote = repository.create(Note.builder().withMessage("Another note").build());

        assertEquals(asList(note, secondNote), repository.findAll());
    }
}
