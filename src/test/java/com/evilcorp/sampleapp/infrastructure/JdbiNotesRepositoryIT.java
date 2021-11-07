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
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import static io.dropwizard.testing.ResourceHelpers.resourceFilePath;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class JdbiNotesRepositoryIT {
    private static Jdbi jdbi;

    @ClassRule
    public static final DropwizardAppRule<SampleAppConfiguration> RULE =
            new DropwizardAppRule<>(SampleAppApplication.class, resourceFilePath("configuration-integration-test.yml"));

    @BeforeClass
    public static void before() {
        jdbi = buildJdbi();
        cleanDatabase(jdbi);
    }

    @Test public void
    it_creates_find_and_find_all_notes() {
        NotesRepository repository = new JdbiNotesRepository(jdbi);

        Note firstNote = repository.create(aNoteWith("First message"));
        Note lastNote = repository.create(aNoteWith("Second message"));

        assertEquals(firstNote, repository.findBy(firstNote.getId()));
        assertEquals(lastNote, repository.findBy(lastNote.getId()));

        assertEquals(asList(lastNote, firstNote), repository.findAll());
    }

    private Note aNoteWith(String aMessage) {
        return Note.builder().withMessage(aMessage).build();
    }

    private static Jdbi buildJdbi() {
        Environment environment = RULE.getEnvironment();
        PooledDataSourceFactory dataSourceFactory = RULE.getConfiguration().getDataSourceFactory();

        return new JdbiFactory().build(environment, dataSourceFactory, "mysql");
    }

    private static void cleanDatabase(Jdbi jdbi) {
        jdbi.useHandle(handle -> handle.execute("TRUNCATE TABLE notes"));
    }
}
