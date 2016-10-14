package org.tddblog.dbmigr;

public interface DbMigrationDAO {
    DbMigration getDocument();

    void storeDbDocument(DbMigration dbMigration);
}
