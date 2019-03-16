package com.dfaris.query.construction.insert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.insert.values.ValuesBuilder;
import com.dfaris.query.construction.predicate.Predicate;
import com.dfaris.query.construction.predicate.where.DefaultWhereClauseBuilder;
import com.dfaris.query.construction.predicate.where.WhereClause;
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

    public static class InsertQueryBuilder {

        private final String table;
        protected final String[] columns;
        protected List<Object> values;
        private Predicate where;

        public static InsertQueryBuilder insertInto(String table, String... columns) {
            return new InsertQueryBuilder(table, columns);
        }

        private InsertQueryBuilder(String table, String[] columns){
            this.table = table;
            this.columns = columns;
        }

        public InsertQueryBuilder values(Function<ValuesBuilder, List<Object>> callback) {
            values = callback.apply(new ValuesBuilder(table, columns));
            return this;
        }

        public InsertQueryBuilder where(Function<DefaultWhereClauseBuilder, WhereClause> callback) {
            where = callback.apply(WhereClause.where());
            return this;
        }

        public InsertQuery build() {
            return new InsertQuery(table, columns, Optional.ofNullable(where));
        }

    }


}
