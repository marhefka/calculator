package org.tddblog.dbmigr;

public class DbMigrConfig {
    private String migrationScriptClassPrefix;
    private boolean primary;
    private String mongoDbUri;
    private int appVersion;

    public DbMigrConfig() {
    }

    public String getMigrationScriptClassPrefix() {
        return migrationScriptClassPrefix;
    }

    public DbMigrConfig setMigrationScriptClassPrefix(String migrationScriptClassPrefix) {
        this.migrationScriptClassPrefix = migrationScriptClassPrefix;
        return this;
    }

    public boolean isPrimary() {
        return primary;
    }

    public DbMigrConfig setPrimary(boolean primary) {
        this.primary = primary;
        return this;
    }

    public String getMongoDbUri() {
        return mongoDbUri;
    }

    public DbMigrConfig setMongoDbUri(String mongoDbUri) {
        this.mongoDbUri = mongoDbUri;
        return this;
    }

    public int getAppVersion() {
        return appVersion;
    }

    public DbMigrConfig setAppVersion(int appVersion) {
        this.appVersion = appVersion;
        return this;
    }
}
