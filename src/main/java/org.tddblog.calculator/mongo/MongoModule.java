package org.tddblog.calculator.mongo;

import com.google.inject.AbstractModule;
import org.mongodb.morphia.Datastore;

public class MongoModule extends AbstractModule {
    private final MongoConfig mongoConfig;

    public MongoModule(MongoConfig mongoConfig) {
        this.mongoConfig = mongoConfig;
    }

    @Override
    protected void configure() {
        bind(MongoConfig.class).toInstance(mongoConfig);
        bind(Datastore.class).toProvider(MongoConnector.class);
    }
}
