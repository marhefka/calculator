package org.tddblog.dbmigr;

public class DbMigration {
    private int dbVersion;
    private int appVersion;
    private DbMigrationStatus status;

    public DbMigration(int dbVersion, int appVersion, DbMigrationStatus status) {
        this.dbVersion = dbVersion;
        this.appVersion = appVersion;
        this.status = status;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public DbMigrationStatus getStatus() {
        return status;
    }

    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }

    public void setStatus(DbMigrationStatus status) {
        this.status = status;
    }
}
