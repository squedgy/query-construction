package com.dfaris.query.construction.select;

import java.util.Arrays;
import java.util.Optional;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.QueryBuilder;
import com.dfaris.query.construction.from.FromBuilder;
import com.dfaris.query.construction.from.FromClause;
import com.dfaris.query.construction.from.FromParent;
import com.dfaris.query.construction.where.IndividualWhereClause;
import com.dfaris.query.construction.where.IndividualWhereClauseBuilder;
import com.dfaris.query.construction.where.WhereClause;
import com.dfaris.query.construction.where.WhereParent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectQuery extends Query {

    private final String[] columns;
    private final FromClause from;
    private final Optional<WhereClause> where;
    private static final Logger log = LoggerFactory.getLogger(SelectQuery.class);

    SelectQuery(String[] columns, FromClause from, Optional<WhereClause> where) {
        this.columns = columns;
        this.where = where;
        this.from = from;
    }

    @Override
    public String toString() {
        StringBuilder query = new StringBuilder("SELECT ");
        for(int i = 0; i < columns.length-1; i++){
            query.append(columns[i]).append(", ");
        }
        query.append(columns[columns.length-1])
                .append(' ')
                .append(from.getClauseStarter())
                .append(from.toString());

        where.ifPresent(w -> {
            query.append(w.getClauseStarter())
                    .append(w.toString())
                    .append(' ');
        });

        return query.toString();
    }

    public static SelectQueryBuilder select(String... columns){
        return new SelectQueryBuilder(columns);
    }

    public static class SelectQueryBuilder extends QueryBuilder<SelectQuery> implements FromParent, WhereParent {

        private final String[] columns;
        private FromClause from;
        private WhereClause where;

        private SelectQueryBuilder(String[] columns){
            this.columns = columns;
        }

        public FromBuilder<SelectQueryBuilder> from() {
            return new FromBuilder<>(this);
        }

        public FromBuilder<SelectQueryBuilder> from(String table) {
            return new FromBuilder<>(this, table);
        }

        public FromBuilder<SelectQueryBuilder> from(String table, String alias) {
            return new FromBuilder<>(this, table, alias);
        }

        public IndividualWhereClauseBuilder<SelectQueryBuilder> where() {
            return IndividualWhereClause.where(this);
        }

        @Override
        public SelectQuery build() {
            log.debug("columns: " + Arrays.toString(columns));
            log.debug("from: " + from);
            log.debug("where: " + where);
            return new SelectQuery(columns, from, Optional.ofNullable(where));
        }

        @Override
        public void setFrom(FromClause from) {
            this.from = from;
        }

        @Override
        public void setWhere(WhereClause clause) {
            this.where = clause;
        }
    }

}
