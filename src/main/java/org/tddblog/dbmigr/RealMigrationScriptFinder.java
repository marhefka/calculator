package org.tddblog.dbmigr;

import com.google.inject.Inject;

public class RealMigrationScriptFinder implements MigrationScriptFinder {
    private final DbMigrConfig dbMigrConfig;

    @Inject
    public RealMigrationScriptFinder(DbMigrConfig dbMigrConfig) {
        this.dbMigrConfig = dbMigrConfig;
    }

    public MigrationScript getMigrationScript(int i) {
        try {
            String className = String.format(dbMigrConfig.getMigrationScriptClassPrefix(), i);
            return (MigrationScript) Class.forName(className).newInstance();
        } catch (Exception ex) {
            return null;
        }
    }

    public int getLastScriptVersion() {
        int i = 1;

        while (true) {
            if (getMigrationScript(i) == null) {
                if (i == 1) {
                    throw new IllegalStateException("No db migration scripts found.");
                }

                return i - 1;
            }

            i++;
        }
    }
}
