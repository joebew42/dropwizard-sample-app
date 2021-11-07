package com.evilcorp.sampleapp;

import com.evilcorp.sampleapp.infrastructure.InMemoryNotesRepository;
import com.evilcorp.sampleapp.infrastructure.JdbiNotesRepository;
import com.evilcorp.sampleapp.models.NotesRepository;
import com.evilcorp.sampleapp.resources.NotesResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi3.JdbiFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.jdbi.v3.core.Jdbi;

public class SampleAppApplication extends Application<SampleAppConfiguration> {
    public static void main(String[] args) throws Exception {
        new SampleAppApplication().run(args);
    }

    @Override
    public void run(SampleAppConfiguration configuration, Environment environment) {
        NotesRepository repository = createNotesRepositoryFrom(configuration, environment);

        final NotesResource notes = new NotesResource(repository);

        environment.jersey().register(notes);
    }

    @Override
    public void initialize(Bootstrap<SampleAppConfiguration> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<>() {
            public DataSourceFactory getDataSourceFactory(SampleAppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    private NotesRepository createNotesRepositoryFrom(SampleAppConfiguration configuration, Environment environment) {
        if (configuration.getDataSourceFactory() != null) {
            JdbiFactory jdbiFactory = new JdbiFactory();
            final Jdbi jdbi = jdbiFactory.build(environment, configuration.getDataSourceFactory(), "database");
            return new JdbiNotesRepository(jdbi);
        }

        return new InMemoryNotesRepository();
    }
}
