package org.tddblog.dbmigr;

public interface MigrationScriptFinder {
    MigrationScript getMigrationScript(int i);

    int getLastScriptVersion();
}
