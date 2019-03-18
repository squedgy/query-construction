package com.dfaris.query.construction.select.having;

import com.dfaris.query.construction.predicate.Predicate;

/**
 * The Having clause in a SelectQuery
 *
 * if you wish to build see {@link HavingClause#having()}
 */
public class HavingClause extends Predicate {

    private Predicate p;

    HavingClause(String left, String operator, String right) {
        super(left, operator, right);
    }

    private HavingClause(Predicate p) {
        super(p);
        this.p = p;
    }

    static HavingClause wrap(Predicate p) { return new HavingClause(p); }

    /**
     * Returns a new {@link DefaultHavingBuilder}
     * @return {@link DefaultHavingBuilder}
     */
    public static DefaultHavingBuilder having() { return new DefaultHavingBuilder(); }

    @Override
    public final String getClauseStarter() {
        return "HAVING ";
    }

    @Override
    public String toString() {
        if(p != null) return p.toString();
        return super.toString();
    }
}
