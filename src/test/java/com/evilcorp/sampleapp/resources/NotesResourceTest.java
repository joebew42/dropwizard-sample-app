package com.evilcorp.sampleapp.resources;

import com.evilcorp.sampleapp.models.Note;
import com.evilcorp.sampleapp.models.NotesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.ws.rs.core.Response;
import java.util.List;

import static java.util.Arrays.asList;
import static javax.ws.rs.core.Response.Status.CREATED;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotesResourceTest {
    public static final Integer AN_ID = 1;
    public static final Integer ANOTHER_ID = 2;
    public static final String A_MESSAGE = "This is a new note";

    @Mock
    private NotesRepository repository;

    private NotesResource resource;

    @Before
    public void setup() {
        resource = new NotesResource(repository);
    }

    @Test
    public void
    returns_an_existing_note() {
        when(repository.findBy(AN_ID)).thenReturn(aNoteWith(AN_ID));

        Response response = resource.get(AN_ID);

        assertEquals(response.getEntity(), aNoteWith(AN_ID));
    }

    @Test
    public void
    returns_the_list_of_all_notes() {
        List<Note> expectedNotes = asList(aNoteWith(AN_ID), aNoteWith(ANOTHER_ID));
        when(repository.findAll()).thenReturn(expectedNotes);

        Response response = resource.get();

        assertEquals(expectedNotes, response.getEntity());
    }

    @Test
    public void
    when_create_a_new_note_it_returns_the_newly_created_one() {
        Note noteToBeCreated = aNoteWith(A_MESSAGE);
        Note createdNote = aNoteWith(AN_ID, noteToBeCreated.getMessage());
        when(repository.create(noteToBeCreated)).thenReturn(createdNote);

        Response response = resource.post(A_MESSAGE);

        assertEquals(response.getStatusInfo(), CREATED);
        assertEquals(response.getEntity(), createdNote);
    }

    private Note aNoteWith(String message) {
        return Note.builder().withMessage(message).build();
    }

    private Note aNoteWith(Integer id, String message) {
        return aNote(id).withMessage(message).build();
    }

    private Note aNoteWith(Integer id) {
        return aNote(id).build();
    }

    private Note.Builder aNote(Integer id) {
        return Note.builder().withId(id);
    }
}
