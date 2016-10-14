package org.tddblog.dbmigr;

import com.google.inject.AbstractModule;
import com.mongodb.client.MongoDatabase;

public class DbMigrationModule extends AbstractModule {
    private final DbMigrConfig dbMigrConfig;

    public DbMigrationModule(DbMigrConfig dbMigrConfig) {
        this.dbMigrConfig = dbMigrConfig;
    }

    @Override
    protected void configure() {
        bind(DbMigrConfig.class).toInstance(dbMigrConfig);
        bind(MongoDatabase.class).toProvider(MongoDatabaseSyncProvider.class);
        bind(DbMigrationDAO.class).to(MongoDbMigrationDAO.class);
        bind(MigrationScriptFinder.class).to(RealMigrationScriptFinder.class);
    }
}
