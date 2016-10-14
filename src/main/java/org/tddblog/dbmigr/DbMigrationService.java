package org.tddblog.dbmigr;

import com.google.inject.Inject;

public class DbMigrationService {
    private final DbMigrConfig dbMigrConfig;
    private final MongoDbSchemeMigration mongoDbSchemeMigration;
    private final DbMigrationSync dbMigrationSync;

    @Inject
    public DbMigrationService(DbMigrConfig dbMigrConfig,
                              MongoDbSchemeMigration mongoDbSchemeMigration,
                              DbMigrationSync dbMigrationSync) {

        this.dbMigrConfig = dbMigrConfig;
        this.mongoDbSchemeMigration = mongoDbSchemeMigration;
        this.dbMigrationSync = dbMigrationSync;
    }

    public void doMigration() {
        if (dbMigrConfig.isPrimary()) {
            mongoDbSchemeMigration.migrate();
        } else {
            dbMigrationSync.waitUntilDbMigrationIsFinished();
        }
    }
}
