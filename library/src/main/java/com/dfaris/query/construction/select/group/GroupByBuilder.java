package com.dfaris.query.construction.select.group;

import java.util.Arrays;
import java.util.LinkedList;

import com.dfaris.query.construction.select.SelectQuery;
import com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder;

public class GroupByBuilder extends SelectQueryBuilder {

    private GroupByClause clause = new GroupByClause(new LinkedList<>());

    public GroupByBuilder(SelectQueryBuilder parent, String[] columns){
        super(parent);
        if(columns != null) clause.addAll(Arrays.asList(columns));
        setGroupBy(clause);
    }

    public GroupByBuilder addGroupingColumn(String column) {
        clause.addColumn(column);
        return this;
    }

    public GroupByBuilder setGroupingColumns(String... columns) {
        clause = new GroupByClause(Arrays.asList(columns));
        setGroupBy(clause);
        return this;
    }

    public SelectQuery build() {
        return super.build();
    }

}
