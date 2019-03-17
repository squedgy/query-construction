package com.dfaris.query.construction.insert;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.insert.values.ValuesBuilder;
import com.dfaris.query.construction.insert.values.ValuesClause;

import java.util.List;
import java.util.function.Function;

public class InsertQuery extends Query {

    private String table;
    private String[] columns;
    private ValuesClause values;

    private InsertQuery(String table, String[] columns, ValuesClause values) {
        this.table = table;
        this.columns = columns;
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(table).append('(').append(String.join(",", columns)).append(") ");
        query.append(values.getClauseStarter()).append(values.toString());

        return query.toString();
    }

    public static class InsertQueryBuilder {

        private final String table;
        protected final String[] columns;
        protected ValuesClause values;

        public static InsertQueryBuilder insertInto(String table, String... columns) {
            return new InsertQueryBuilder(table, columns);
        }

        private InsertQueryBuilder(String table, String[] columns){
            this.table = table;
            this.columns = columns;
        }

        public InsertQueryBuilder values(Function<ValuesBuilder, ValuesClause> callback) {
            values = callback.apply(new ValuesBuilder(table, columns));
            return this;
        }

        public InsertQuery build() {
            return new InsertQuery(table, columns, values);
        }

    }


}
