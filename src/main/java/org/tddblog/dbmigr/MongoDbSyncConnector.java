package org.tddblog.dbmigr;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

import java.util.logging.Level;

@Singleton
public class MongoDbSyncConnector {
    private final String mongoDbUri;

    private MongoClient mongoClient;
    private MongoDatabase db;

    @Inject
    public MongoDbSyncConnector(String mongoDbUri) {
        this.mongoDbUri = mongoDbUri;
    }

    @Inject
    public void init() {
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);

        MongoClientOptions.Builder clientOptionsBuilder = new MongoClientOptions.Builder()
                .connectionsPerHost(1);

        MongoClientURI mongoClientURI = new MongoClientURI(mongoDbUri, clientOptionsBuilder);

        mongoClient = new MongoClient(mongoClientURI);
        db = mongoClient.getDatabase(mongoClientURI.getDatabase());
    }

    public void close() {
        mongoClient.close();
    }

    public MongoDatabase getDb() {
        return db;
    }
}
