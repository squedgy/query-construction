package com.dfaris.query.construction.select.having;

import com.dfaris.query.construction.predicate.MultiPredicateBuilder;

public abstract class HavingClauseBuilder<This extends HavingClauseBuilder,
                                    Paren extends HavingClauseBuilder>
        extends MultiPredicateBuilder<This, HavingClause, Paren> implements HavingParent{

    public HavingClauseBuilder(HavingClause a, String andOr) {
        super(a, andOr);
    }

    protected HavingClause buildSimpleClause() {
        if(canBuild()) return new HavingClause(column, operator, String.join(",", constants));
        throw new IllegalStateException("Couldn't build an individual clause. Missing either column, operator, or your constant(s)!");
    }

    @Override
    public HavingClause build() {
        HavingClause clause;
        if(!canBuildCompound()) {
            try {
                clause = buildSimpleClause();
            } catch (IllegalStateException e) {
                if(a != null) clause = a;
                else throw e;
            }
        } else clause = new HavingClause(a.toString(), andOr, buildSimpleClause().toString());
        clear();
        return clause;
    }

    @Override
    public void setPredicate(HavingClause predicate) throws IllegalStateException{
        if(a == null) a = predicate;
        else if(andOr != null){
            HavingClause tmp = new HavingClause(a.toString(), andOr, predicate.toString());
            clear();
            a = tmp;
        }
        else throw new IllegalStateException("Attempted to set predicate on a builder that contains a predicate, but wasn't expecting another one!");
    }

}
