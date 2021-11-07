package com.evilcorp.sampleapp.resources;

import com.codahale.metrics.annotation.Timed;
import com.evilcorp.sampleapp.models.Note;
import com.evilcorp.sampleapp.models.NotesRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.OK;

@Path("/notes")
@Produces(APPLICATION_JSON)
public class NotesResource {
    private NotesRepository repository;

    public NotesResource(NotesRepository repository) {
        this.repository = repository;
    }

    @GET
    @Path("/{id}")
    @Timed
    public Response get(@PathParam("id") Integer id) {
        Note note = repository.findBy(id);

        return Response.status(OK).entity(note).build();
    }

    @GET
    @Path("/")
    @Timed
    public Response get() {
        List<Note> notes = repository.findAll();

        return Response.status(OK).entity(notes).build();
    }

    @POST
    @Path("/")
    @Timed
    public Response post(String message) {
        Note note = repository.create(Note.builder().withMessage(message).build());

        return Response.status(CREATED).entity(note).build();
    }
}
