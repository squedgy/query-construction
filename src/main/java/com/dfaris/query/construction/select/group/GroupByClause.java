package com.dfaris.query.construction.select.group;

import java.util.Collection;
import java.util.List;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Stringable;

public class GroupByClause extends Stringable implements Clause {

    private final List<String> columns;

    public GroupByClause(List<String> columns) {
        this.columns = columns;
    }

    void addColumn(String column) {
        this.columns.add(column);
    }

    void addAll(Collection<String> columns) {
        this.columns.addAll(columns);
    }

    @Override
    public String getClauseStarter() {
        return "GROUP BY ";
    }

    @Override
    public String toString() {
        return String.join(",", columns);
    }
}
