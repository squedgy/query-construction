package com.dfaris.query.construction.order;

import java.util.Arrays;
import java.util.LinkedList;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Stringable;

public class OrderByClause extends Stringable implements Clause {

    private LinkedList<String> columns;

    public OrderByClause(LinkedList<String> columns) {
        this.columns = columns;
    }

    @Override
    public String getClauseStarter() {
        return "ORDER BY ";
    }

    @Override
    public String toString() {
        return String.join(",", columns);
    }

    void addClause(String column) {
        columns.add(column);
    }

    void setClauses(String[] strings) {
        columns.clear();
        addAllClauses(strings);
    }

    void addAllClauses(String[] strings) {
        columns.addAll(Arrays.asList(strings));
    }
}
