package org.tddblog.calculator.app;

import io.dropwizard.Configuration;
import org.tddblog.calculator.mongo.MongoConfig;

public class AppConfig extends Configuration {
    private MongoConfig mongo;

    public MongoConfig getMongo() {
        return mongo;
    }

    public void setMongo(MongoConfig mongo) {
        this.mongo = mongo;
    }
}