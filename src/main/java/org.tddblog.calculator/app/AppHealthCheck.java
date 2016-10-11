package org.tddblog.calculator.app;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.mongodb.morphia.Datastore;

@Singleton
public class AppHealthCheck extends HealthCheck {
    private final Datastore datastore;

    @Inject
    public AppHealthCheck(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    protected Result check() throws Exception {
        datastore.getDB().getCollectionNames(); // checking if we can still access db
        return Result.healthy();
    }
}
