package com.dfaris.query.construction.from.join;

public class FullJoin extends InnerJoin {

    FullJoin(String joinTable, String joinTableAlias, String joinTableColumn, String onAlias, String onAliasColumn) {
        super(joinTable, joinTableAlias, joinTableColumn, onAlias, onAliasColumn);
    }

    @Override
    public String toString() {
        return String.format("FULL JOIN %s %s ON %s.%s = %s.%s)",
                             joinTable,
                             joinTableAlias,
                             joinTableAlias,
                             joinTableColumn,
                             onAlias,
                             onAliasColumn);
    }

}
