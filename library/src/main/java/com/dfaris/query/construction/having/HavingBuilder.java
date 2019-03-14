package com.dfaris.query.construction.having;

import com.dfaris.query.construction.select.SelectQuery;
import com.dfaris.query.construction.structure.predicate.MultiPredicateBuilder;
import com.dfaris.query.construction.structure.predicate.Predicate;

public class HavingBuilder extends MultiPredicateBuilder<HavingBuilder, ParenthesizedHavingBuilder> {

    protected final SelectQuery.SelectQueryBuilder parent;

    public HavingBuilder(SelectQuery.SelectQueryBuilder parent, Predicate a, String andOr) {
        super(a, andOr);
        this.parent = parent;
        this.refe = this;
    }

    @Override
    protected Predicate buildCompoundClause() {
        return null;
    }

    @Override
    public ParenthesizedHavingBuilder startParenthesizedGroup() {
        return null;
    }

    @Override
    protected Predicate buildIndividualClause() {
        return null;
    }

}
