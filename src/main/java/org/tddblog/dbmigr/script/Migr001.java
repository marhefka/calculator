package org.tddblog.dbmigr.script;

import com.mongodb.client.MongoDatabase;
import org.tddblog.dbmigr.MigrationScript;

public class Migr001 implements MigrationScript {
    public void upgrade(MongoDatabase db) {
        db.createCollection("calculator");
    }
}
