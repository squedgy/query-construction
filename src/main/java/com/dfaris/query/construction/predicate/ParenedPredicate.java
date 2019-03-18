package com.dfaris.query.construction.predicate;

/**
 * A predicate wrapped in parenthesis formatted similarily to "(${predicate.toString()})".
 */
public class ParenedPredicate extends Predicate{

    private final String starter;

    public ParenedPredicate(Predicate p) {
        super(p);
        starter = p.getClauseStarter();
    }

    @Override
    public String toString() {
        return '(' + super.toString() + ')';
    }

    @Override
    public String getClauseStarter() {
        return starter;
    }
}
