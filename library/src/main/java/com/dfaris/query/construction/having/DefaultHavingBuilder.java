package com.dfaris.query.construction.having;

import com.dfaris.query.construction.select.SelectQuery;

public class DefaultHavingBuilder
        extends HavingClauseBuilder<SelectQuery.SelectQueryBuilder, DefaultHavingBuilder, ParenthesizedHavingBuilder<DefaultHavingBuilder>> {

    public DefaultHavingBuilder(SelectQuery.SelectQueryBuilder parent, HavingClause a, String andOr) {
        super(parent, a, andOr);
        this.refe = this;
    }

    public DefaultHavingBuilder(SelectQuery.SelectQueryBuilder parent) { this(parent, null, null); }

    @Override
    public ParenthesizedHavingBuilder<DefaultHavingBuilder> startParenthesizedGroup() {
        return new ParenthesizedHavingBuilder<>(this);
    }

}
