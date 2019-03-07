package com.dfaris.query.construction.from;

import java.util.LinkedList;
import java.util.List;

import com.dfaris.query.construction.from.join.FromJoinBuilder;
import com.dfaris.query.construction.from.join.JoinClause;

import static com.dfaris.query.construction.from.join.JoinClause.Type.CROSS;
import static com.dfaris.query.construction.from.join.JoinClause.Type.FULL;
import static com.dfaris.query.construction.from.join.JoinClause.Type.INNER;
import static com.dfaris.query.construction.from.join.JoinClause.Type.LEFT;
import static com.dfaris.query.construction.from.join.JoinClause.Type.RIGHT;

public class FromBuilder<QueryBuilderType extends FromParent> {

    private String table, alias;
    private final List<JoinClause> joins;
    private final QueryBuilderType parent;

    public FromBuilder(QueryBuilderType parent, String table, String alias) {
        this.joins = new LinkedList<>();
        this.table = table;
        this.parent = parent;
        this.alias = alias;
    }

    public FromBuilder(QueryBuilderType parent, String table) {
        this(parent, table, table);
    }

    public FromBuilder(QueryBuilderType parent) {
        this(parent, null);
    }

    public FromBuilder<QueryBuilderType> table(String table) {
        this.table = table;
        if(this.alias == null) {
            this.alias = table;
        }
        return this;
    }

    public FromBuilder<QueryBuilderType> table(String table, String alias) {
        this.table = table;
        this.alias = alias;
        return this;
    }

    public FromBuilder<QueryBuilderType> alias(String alias) {
        this.alias = alias;
        return this;
    }

    public FromJoinBuilder<QueryBuilderType> join() {
        return innerJoin();
    }

    public FromJoinBuilder<QueryBuilderType> join(String table) {
        return innerJoin(table);
    }

    public FromJoinBuilder<QueryBuilderType> innerJoin() {
        return new FromJoinBuilder<>(this, INNER);
    }

    public FromJoinBuilder<QueryBuilderType> innerJoin(String table) {
        return new FromJoinBuilder<>(this, INNER, table);
    }

    public FromJoinBuilder<QueryBuilderType> leftJoin() {
        return new FromJoinBuilder<>(this, LEFT);
    }

    public FromJoinBuilder<QueryBuilderType> leftJoin(String table) {
        return new FromJoinBuilder<>(this, LEFT, table);
    }

    public FromJoinBuilder<QueryBuilderType> rightJoin() {
        return new FromJoinBuilder<>(this, RIGHT);
    }

    public FromJoinBuilder<QueryBuilderType> rightJoin(String table) {
        return new FromJoinBuilder<>(this, RIGHT, table);
    }

    public FromJoinBuilder<QueryBuilderType> fullJoin() {
        return new FromJoinBuilder<>(this, FULL);
    }

    public FromJoinBuilder<QueryBuilderType> fullJoin(String table) {
        return new FromJoinBuilder<>(this, FULL, table);
    }

    public FromBuilder<QueryBuilderType> crossJoin(String table){
        return new FromJoinBuilder<>(this, CROSS, table).on(null, null, null);
    }
    
    public FromBuilder<QueryBuilderType> crossJoin(String table, String alias) {
        return new FromJoinBuilder<>(this, CROSS, table).alias(alias).on(null, null, null);
    }

    public void addJoin(JoinClause join) {
        this.joins.add(join);
    }

    public QueryBuilderType setFrom() {
        parent.setFrom(new FromClause(table, (alias == null ? table : alias), joins));
        return parent;
    }

    public QueryBuilderType cancelFrom() {
        return parent;
    }

}
