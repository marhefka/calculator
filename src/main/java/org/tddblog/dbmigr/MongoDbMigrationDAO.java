package org.tddblog.dbmigr;

import com.google.inject.Inject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonString;
import org.bson.Document;

public class MongoDbMigrationDAO implements DbMigrationDAO {
    public final static String COLLECTION_NAME = "databases";
    private final static String DB_VERSION_FIELD_NAME = "db_version";
    private final static String APP_VERSION_FIELD_NAME = "app_version";
    private final static String STATUS_FIELD_NAME = "status";

    private final MongoDatabase db;

    @Inject
    public MongoDbMigrationDAO(MongoDatabase db) {
        this.db = db;
    }

    @Override
    public DbMigration getDocument() {
        MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);
        Document document = collection.find().iterator().tryNext();
        if (document == null) {
            return null;
        }

        int dbVersion = readIntOrDoubleAsInt(document, DB_VERSION_FIELD_NAME);
        int appVersion = readIntOrDoubleAsInt(document, APP_VERSION_FIELD_NAME);

        String sStatus = (String) document.get(STATUS_FIELD_NAME);
        DbMigrationStatus status = DbMigrationStatus.valueOf(sStatus);

        return new DbMigration(dbVersion, appVersion, status);
    }

    private int readIntOrDoubleAsInt(Document document, String field) {
        if (document.get(field) instanceof Integer) {
            return (int) document.get(field);
        }

        if (document.get(field) instanceof Double) {
            return ((Double) document.get(field)).intValue();
        }

        throw new IllegalStateException("Unknown field type: " + document.get(field).getClass().getSimpleName());
    }

    @Override
    public void storeDbDocument(DbMigration dbMigration) {
        MongoCollection<Document> collection = db.getCollection(COLLECTION_NAME);

        Document document = new Document();
        document.put(DB_VERSION_FIELD_NAME, new BsonInt32(dbMigration.getDbVersion()));
        document.put(APP_VERSION_FIELD_NAME, new BsonInt32(dbMigration.getAppVersion()));
        document.put(STATUS_FIELD_NAME, new BsonString(dbMigration.getStatus().name()));

        collection.replaceOne(new BsonDocument(), document, new UpdateOptions().upsert(true));
    }
}
