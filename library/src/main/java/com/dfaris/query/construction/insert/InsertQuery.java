package com.dfaris.query.construction.insert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.insert.values.ValuesBuilder;
import com.dfaris.query.construction.predicate.Predicate;
import com.dfaris.query.construction.predicate.where.WhereParent;

public class InsertQuery extends Query {

    private String table;
    private String[] columns;
    private List<Optional<Clause>> clauses;

    private InsertQuery(String table, String[] columns, Optional<Clause> where) {
        this.table = table;
        this.columns = columns;
        this.clauses = Arrays.asList(
                where
        );
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder("INSERT INTO ");

        query.append(table).append('(').append(String.join(",", columns)).append(") ");

        clauses.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(clause -> {
                    query.append(clause.getClauseStarter())
                            .append(clause.toString())
                            .append(' ');
                });

        return query.toString();
    }

    public static class InsertQueryBuilder implements WhereParent<InsertQuery> {

        private final String table;
        protected final String[] columns;
        private Predicate where;

        public static InsertQueryBuilder insertInto(String table, String... columns) {
            return new InsertQueryBuilder(table, columns);
        }

        private InsertQueryBuilder(String table, String[] columns){
            this.table = table;
            this.columns = columns;
        }

        protected InsertQueryBuilder(InsertQueryBuilder builder) {
            this.columns = builder.columns;
            this.table = builder.table;
            this.where = builder.where;
        }

        public ValuesBuilder values(Object... values) {
            if(values.length % columns.length != 0) throw new IllegalArgumentException(
                    "The amount of values must be evenly divisible by the number of columns. " +
                            "Received: " + values.length + ", with " + columns.length + "columns!"
            );
            return new ValuesBuilder(this, values);
        }

        public ValuesBuilder values(int values) {
            if(values % columns.length != 0) throw new IllegalArgumentException(
                    "values must be evenly divisible by the number of columns! " +
                            "Requested: " + values + " question marks with " + columns.length + "columns"
            );
            return new ValuesBuilder(this, values);
        }

        @Override
        public void setPredicate(Predicate predicate) {
            this.where = predicate;
        }

        @Override
        public InsertQuery build() {
            return new InsertQuery(table, columns, Optional.ofNullable(where));
        }

    }


}
