package com.example.gerenciador_tarefas.config;

import org.hibernate.dialect.DatabaseVersion;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

public class SQLiteDialect extends Dialect {

    public SQLiteDialect() {
        super(DatabaseVersion.make(3));
        // registerColumnType removido no Hibernate 6+
    }

    @Override
    public int getDefaultStatementBatchSize() {
        return 1;
    }


    public boolean supportsIdentityColumns() {
        return true;
    }

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new SQLiteIdentityColumnSupport();
    }

    @Override
    public boolean supportsCascadeDelete() {
        return false;
    }

    @Override
    public String getAddColumnString() {
        return "add column";
    }

    // Desabilita suporte a ALTER TABLE (evita comandos incompatíveis)
    @Override
    public boolean hasAlterTable() {
        return false;
    }

    // Evita tentativa de dropar constraints (SQLite não suporta)
    @Override
    public boolean dropConstraints() {
        return false;
    }

    // Evita geração de foreign keys via ALTER TABLE

    public boolean supportsForeignKeyConstraints() {
        return false;
    }

    public static class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl {

        @Override
        public boolean supportsIdentityColumns() {
            return true;
        }

        @Override
        public String getIdentitySelectString(String table, String column, int type) {
            return "select last_insert_rowid()";
        }

        @Override
        public String getIdentityColumnString(int type) {
            return "integer primary key autoincrement";
        }
    }
}
