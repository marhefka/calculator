package org.tddblog.dbmigr;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.client.MongoDatabase;

public class MongoDatabaseSyncProvider implements Provider<MongoDatabase> {
    private final MongoDbSyncConnector mongoDbSyncConnector;

    @Inject
    public MongoDatabaseSyncProvider(MongoDbSyncConnector mongoDbSyncConnector) {
        this.mongoDbSyncConnector = mongoDbSyncConnector;
    }

    @Override
    public MongoDatabase get() {
        return mongoDbSyncConnector.getDb();
    }
}
