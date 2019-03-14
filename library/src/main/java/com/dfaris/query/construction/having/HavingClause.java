package com.dfaris.query.construction.having;

import com.dfaris.query.construction.structure.predicate.Predicate;

public abstract class HavingClause extends Predicate {

    public HavingClause(String left, String operator, String right) {
        super(left, operator, right);
    }

    public HavingClause(Predicate p) {
        super(p);
    }

    @Override
    public final String getClauseStarter() {
        return "HAVING ";
    }

}
