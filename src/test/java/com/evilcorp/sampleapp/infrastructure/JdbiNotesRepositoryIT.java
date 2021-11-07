package com.evilcorp.sampleapp.infrastructure;

import com.evilcorp.sampleapp.SampleAppApplication;
import com.evilcorp.sampleapp.SampleAppConfiguration;
import com.evilcorp.sampleapp.models.Note;
import com.evilcorp.sampleapp.models.NotesRepository;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.setup.Environment;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.jdbi.v3.core.Jdbi;
import org.junit.ClassRule;
import org.junit.Test;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static org.junit.Assert.assertEquals;

public class JdbiNotesRepositoryIT {

    @ClassRule
    public static final DropwizardAppRule<SampleAppConfiguration> RULE =
            new DropwizardAppRule<>(SampleAppApplication.class, resourceFilePath("configuration-integration-test.yml"));

    @Test public void
    it_creates_find_and_find_all_notes() {
        NotesRepository repository = new JdbiNotesRepository(buildJdbi());

        Note note = repository.create(aNoteWith("First note"));

        assertEquals(note, repository.findBy(note.getId()));
    }

    private Jdbi buildJdbi() {
        Environment environment = RULE.getEnvironment();
        PooledDataSourceFactory dataSourceFactory = RULE.getConfiguration().getDataSourceFactory();

        return new JdbiFactory().build(environment, dataSourceFactory, "mysql");
    }

    private Note aNoteWith(String aMessage) {
        return Note.builder().withMessage(aMessage).build();
    }
}
