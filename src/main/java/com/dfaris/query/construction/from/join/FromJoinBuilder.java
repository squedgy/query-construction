package com.dfaris.query.construction.from.join;

import com.dfaris.query.construction.from.FromBuilder;
import com.dfaris.query.construction.from.FromParent;

public class FromJoinBuilder<QueryBuilderType extends FromParent> extends JoinBuilder<FromJoinBuilder<QueryBuilderType>, FromBuilder<QueryBuilderType>> {

    protected String table,
            alias,
            tableColumn,
            otherTableAlias,
            onColumn;

    protected final FromBuilder<QueryBuilderType> parent;

    public FromJoinBuilder(FromBuilder<QueryBuilderType> builder, JoinClause.Type type, String table) {
        super(type);
        this.table(table);
        this.parent = builder;
    }

    public FromJoinBuilder(FromBuilder<QueryBuilderType> builder, JoinClause.Type type) {
        super(type);
        this.parent = builder;
    }

    public FromJoinBuilder<QueryBuilderType> table(String name) {
        this.table = name;
        return this;
    }

    public FromJoinBuilder<QueryBuilderType> table(String name, String alias) {
        this.table = name;
        this.alias = alias;
        return this;
    }

    public FromJoinBuilder<QueryBuilderType> alias(String alias) {
        this.alias = alias;
        return this;
    }

    public FromBuilder<QueryBuilderType> on(String column, String otherTableAlias, String onColumn) {
        this.tableColumn = column;
        this.otherTableAlias = otherTableAlias;
        this.onColumn = onColumn;
        parent.addJoin(build());
        return parent;
    }

    public FromJoinBuilder<QueryBuilderType> reset(JoinClause.Type type) {
        this.alias = null;
        this.table = null;
        this.onColumn = null;
        this.tableColumn = null;
        this.otherTableAlias = null;
        this.type = type;
        return this;
    }

    protected final JoinClause build() {
        if(type == JoinClause.Type.CROSS) {
            return new CrossJoin(table);
        } else if (type == JoinClause.Type.INNER) {
            return new InnerJoin(table, alias, tableColumn, otherTableAlias, onColumn);
        } else if (type == JoinClause.Type.LEFT) {
            return new LeftJoin(table, alias, tableColumn, otherTableAlias, onColumn);
        } else if (type == JoinClause.Type.RIGHT) {
            return new RightJoin(table, alias, tableColumn, otherTableAlias, onColumn);
        } else if (type == JoinClause.Type.FULL) {
            return new FullJoin(table, alias, tableColumn, otherTableAlias, onColumn);
        }

        return null;
    }
}
