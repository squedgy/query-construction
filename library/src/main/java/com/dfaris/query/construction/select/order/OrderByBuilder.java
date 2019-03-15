package com.dfaris.query.construction.select.order;

import java.util.LinkedList;

import com.dfaris.query.construction.select.SelectQuery;

public class OrderByBuilder extends SelectQuery.SelectQueryBuilder {

    private OrderByClause clause = new OrderByClause(new LinkedList<>());

    public OrderByBuilder(SelectQuery.SelectQueryBuilder builder, String[] columns) {
        super(builder);
        clause.addAllClauses(columns);
        setOrderBy(clause);
    }

    public OrderByBuilder addOrderByClauses(String... clauses) {
        clause.addAllClauses(clauses);
        return this;
    }

    public OrderByBuilder setOrderByClauses(String... clauses) {
        clause.setClauses(clauses);
        return this;
    }

    public OrderByBuilder addOrderByClause(String clause) {
        this.clause.addClause(clause);
        return this;
    }

}
