package org.tddblog.dbmigr;

import com.google.inject.Inject;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class MongoDbSchemeMigration {
    private static final Logger LOG = LoggerFactory.getLogger(MongoDbSchemeMigration.class);

    private final DbMigrConfig dbMigrConfig;
    private final MigrationScriptFinder migrationScriptFinder;
    private final MongoDatabase db;
    private final DbMigrationDAO dbMigrationDAO;

    private DbMigration dbDocument;

    @Inject
    public MongoDbSchemeMigration(DbMigrConfig dbMigrConfig,
                                  MigrationScriptFinder migrationScriptFinder,
                                  MongoDatabase db,
                                  DbMigrationDAO dbMigrationDAO) {

        this.dbMigrConfig = dbMigrConfig;
        this.migrationScriptFinder = migrationScriptFinder;
        this.db = db;
        this.dbMigrationDAO = dbMigrationDAO;
    }

    public void migrate() {
        LOG.info("Application version number (Jenkins build number in commit stage): #" + dbMigrConfig.getAppVersion());

        init();
        try {
            doMigration();

            dbDocument.setStatus(DbMigrationStatus.DONE);
            dbMigrationDAO.storeDbDocument(dbDocument);
            LOG.info("Migration finished.");
        } catch (Exception ex) {
            dbDocument.setStatus(DbMigrationStatus.ERROR);
            dbMigrationDAO.storeDbDocument(dbDocument);

            throw ex;
        }
    }

    private void init() {
        dbDocument = dbMigrationDAO.getDocument();
        if (dbDocument != null) {
            dbDocument.setStatus(DbMigrationStatus.IN_PROGRESS);
            dbDocument.setAppVersion(dbMigrConfig.getAppVersion());
            dbMigrationDAO.storeDbDocument(dbDocument);
            return;
        }

        dbDocument = new DbMigration(0, dbMigrConfig.getAppVersion(), DbMigrationStatus.IN_PROGRESS);
        dbMigrationDAO.storeDbDocument(dbDocument);
    }

    private void doMigration() {
        int currDbVersion = dbDocument.getDbVersion();
        int lastScriptVersion = migrationScriptFinder.getLastScriptVersion();

        String msg = MessageFormat.format("Current version: {0}, lastScriptVersion: {1}", currDbVersion, lastScriptVersion);
        LOG.info(msg);

        for (int version = currDbVersion + 1; version <= lastScriptVersion; version++) {
            upgradeTo(version);
        }
    }

    private void upgradeTo(int version) {
        LOG.info("Running migration script #" + version);
        MigrationScript migrationScript = migrationScriptFinder.getMigrationScript(version);
        migrationScript.upgrade(db);

        dbDocument.setDbVersion(version);
        dbMigrationDAO.storeDbDocument(dbDocument);
    }
}
