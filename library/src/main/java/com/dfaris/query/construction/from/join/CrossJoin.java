package com.dfaris.query.construction.from.join;

public class CrossJoin extends JoinClause {

    private final String table;

    CrossJoin(String table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return String.format("CROSS JOIN %s)", table);
    }
}
