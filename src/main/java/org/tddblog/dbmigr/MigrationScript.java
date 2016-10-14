package org.tddblog.dbmigr;

import com.mongodb.client.MongoDatabase;

public interface MigrationScript {
    void upgrade(MongoDatabase db);
}
