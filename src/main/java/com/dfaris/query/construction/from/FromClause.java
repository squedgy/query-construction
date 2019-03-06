package com.dfaris.query.construction.from;

import java.util.List;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.from.join.JoinClause;

public class FromClause extends Clause {

    private final String table;
    private final String alias;
    private final List<JoinClause> joins;

    FromClause(String tableName, String alias, List<JoinClause> joins) {
        this.table = tableName;
        this.alias = alias;
        this.joins = joins;
    }

    public String getTable() {
        return table;
    }

    public List<JoinClause> getJoins() {
        return joins;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        StringBuilder joinBuilder = new StringBuilder();
        for(JoinClause join: joins) {
            joinBuilder.append(join.toString());
        }
        return String.format("%s %s %s",
                             table,
                             alias,
                             joinBuilder);
    }

    @Override
    public String getClauseStarter() {
        return "FROM ";
    }

}
