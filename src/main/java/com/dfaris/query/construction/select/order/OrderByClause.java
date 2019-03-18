package com.dfaris.query.construction.select.order;

import java.util.Arrays;
import java.util.LinkedList;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Stringable;

/**
 * An Order By clause in a SelectQuery
 */
public class OrderByClause extends Stringable implements Clause {

    private LinkedList<String> columns;

    public OrderByClause(LinkedList<String> columns) {
        this.columns = columns;
    }

    /**
     * @see com.dfaris.query.construction.Clause
     * @return the String "ORDER BY "
     */
    @Override
    public String getClauseStarter() {
        return "ORDER BY ";
    }

    /**
     * The columns this clause holds comma-seperated
     * @return a comma-seperated String of columns this clause holds
     */
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
