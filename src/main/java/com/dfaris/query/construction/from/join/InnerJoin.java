package com.dfaris.query.construction.from.join;

public class InnerJoin extends JoinClause {

    protected final String joinTable,
                        joinTableAlias,
                        onAlias,
                        joinTableColumn,
                        onAliasColumn;

    InnerJoin(String joinTable, String joinTableAlias, String joinTableColumn, String onAlias, String onAliasColumn) {
        this.joinTable = joinTable;
        this.joinTableAlias = joinTableAlias;
        this.joinTableColumn = joinTableColumn;
        this.onAlias = onAlias;
        this.onAliasColumn = onAliasColumn;
    }

    @Override
    public String toString() {
        return String.format("JOIN %s %s ON %s.%s = %s.%s ",
                             joinTable,
                             joinTableAlias,
                             joinTableAlias,
                             joinTableColumn,
                             onAlias,
                             onAliasColumn);
    }

}
