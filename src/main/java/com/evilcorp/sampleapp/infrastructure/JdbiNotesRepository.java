package com.evilcorp.sampleapp.infrastructure;

import com.evilcorp.sampleapp.models.Note;
import com.evilcorp.sampleapp.models.NotesRepository;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbiNotesRepository implements NotesRepository {

    private final NoteDAO notes;

    public JdbiNotesRepository(Jdbi jdbi) {
        jdbi.registerRowMapper(new NoteMapper());
        notes = jdbi.onDemand(NoteDAO.class);
    }

    public Note findBy(Integer id) {
        return notes.findById(id);
    }

    public List<Note> findAll() {
        return notes.findAll();
    }

    public Note create(Note note) {
        synchronized (notes) {
            notes.create(note.getMessage());
            note.setId(notes.lastId());
        }

        return note;
    }

    private interface NoteDAO {
        @SqlUpdate("INSERT INTO notes(message) VALUES(:message)")
        void create(@Bind("message") String message);

        @SqlQuery("SELECT id, message FROM notes WHERE id = :id")
        Note findById(@Bind("id") int id);

        @SqlQuery("SELECT id, message FROM notes ORDER BY id DESC")
        List<Note> findAll();

        @SqlQuery("SELECT MAX(id) FROM notes")
        Integer lastId();
    }

    static class NoteMapper implements RowMapper<Note> {
        @Override
        public Note map(ResultSet rs, StatementContext ctx) throws SQLException {
            return Note.builder()
                    .withId(rs.getInt("id"))
                    .withMessage(rs.getString("message"))
                    .build();
        }
    }
}
