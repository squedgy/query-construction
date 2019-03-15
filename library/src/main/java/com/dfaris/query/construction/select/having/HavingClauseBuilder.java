package com.dfaris.query.construction.select.having;

import com.dfaris.query.construction.select.SelectQuery;
import com.dfaris.query.construction.predicate.MultiPredicateBuilder;
import com.dfaris.query.construction.predicate.Predicate;

public abstract class HavingClauseBuilder<Parent extends HavingParent,
                                    This extends HavingClauseBuilder,
                                    Paren extends HavingClauseBuilder>
        extends MultiPredicateBuilder<This, HavingClause, Paren> implements HavingParent{

    protected final Parent parent;

    public HavingClauseBuilder(Parent parent, HavingClause a, String andOr) {
        super(a, andOr);
        this.parent = parent;
    }

    @Override
    protected HavingClause buildCompoundClause() throws IllegalStateException{
        HavingClause clause;
        if(!canBuildCompoundClause()) {
            try {
                clause = buildIndividualClause();
            } catch (IllegalStateException e) {
                if(a != null) clause = a;
                else throw e;
            }
        } else clause = new HavingClause(a.toString(), andOr, buildIndividualClause().toString());
        clear();
        return clause;
    }

    @Override
    protected HavingClause buildIndividualClause() throws IllegalStateException{
        if(canBuildIndividualClause()) return new HavingClause(column, operator, String.join(",", constants));
        throw new IllegalStateException("Couldn't build an individual clause. Missing either column, operator, or your constant(s)!");
    }

    @Override
    public void setPredicate(Predicate predicate) throws IllegalStateException{
        if(a == null) a = (HavingClause) predicate;
        else if(andOr != null){
            HavingClause tmp = new HavingClause(a.toString(), andOr, predicate.toString());
            clear();
            a = tmp;
        }
        else throw new IllegalStateException("Attempted to set predicate on a builder that contains a predicate, but wasn't expecting another one!");
    }

    @Override
    public SelectQuery build() throws IllegalStateException{
        parent.setPredicate(buildCompoundClause());
        return parent.build();
    }

    public Parent continueBuilding() {
        parent.setPredicate(buildCompoundClause());
        return parent;
    }

}
