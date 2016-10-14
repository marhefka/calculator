package org.tddblog.dbmigr;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

public class DbMigrationSync {
    private static final Logger LOG = LoggerFactory.getLogger(DbMigrationSync.class);

    private final DbMigrConfig dbMigrConfig;
    private final DbMigrationDAO dbMigrationDAO;

    @Inject
    public DbMigrationSync(DbMigrConfig dbMigrConfig,
                           DbMigrationDAO dbMigrationDAO) {

        this.dbMigrConfig = dbMigrConfig;
        this.dbMigrationDAO = dbMigrationDAO;
    }

    public void waitUntilDbMigrationIsFinished() {
        LOG.info("Not primary. Waiting for db migration to complete...");

        int currentAppVersion = dbMigrConfig.getAppVersion();

        while (true) {
            DbMigration dbMigration = dbMigrationDAO.getDocument();

            if (dbMigration == null || dbMigration.getAppVersion() < currentAppVersion) {
                waitABit();
                continue;
            }

            if (dbMigration.getAppVersion() > currentAppVersion) {
                String msg = MessageFormat.format("Application version number ({0}) must be equal or greater than db version number ({1}). ", currentAppVersion, dbMigration.getAppVersion());
                throw new RuntimeException(msg);
            }

            switch (dbMigration.getStatus()) {
                case IN_PROGRESS:
                    waitABit();
                    continue;
                case DONE:
                    return;
                case ERROR:
                    throw new RuntimeException("Migration finished with error, exiting...");
                default:
                    throw new IllegalStateException("Illegal phase " + dbMigration.getStatus().toString());
            }
        }
    }

    private void waitABit() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}
