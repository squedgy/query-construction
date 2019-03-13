package com.dfaris.query.construction.having;

import com.dfaris.query.construction.Clause;

public abstract class HavingClause implements Clause {

    @Override
    public final String getClauseStarter() {
        return "HAVING ";
    }

}
