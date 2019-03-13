package com.dfaris.query.construction.group;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.dfaris.query.construction.select.SelectQuery;
import com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder;

public class GroupByBuilder extends SelectQueryBuilder {

    private final List<String> columns = new LinkedList<>();

    public GroupByBuilder(SelectQueryBuilder parent, String[] columns){
        super(parent);
        this.columns.addAll(Arrays.asList(columns));
    }

    public GroupByBuilder addGroupingColumn(String column) {
        this.columns.add(column);
        return this;
    }

    public GroupByBuilder setGroupingColumns(String... columns) {
        this.columns.clear();
        this.columns.addAll(Arrays.asList(columns));
        return this;
    }

    public SelectQuery build() {
        setGroupBy(columns);
        return super.build();
    }

}
