package com.dfaris.query.construction.select.having;

import com.dfaris.query.construction.select.SelectQuery;

public class DefaultHavingBuilder
        extends HavingClauseBuilder<DefaultHavingBuilder, ParenthesizedHavingBuilder<DefaultHavingBuilder>> {

    public DefaultHavingBuilder(HavingClause a, String andOr) {
        super(a, andOr);
        this.refe = this;
    }

    public DefaultHavingBuilder() { this(null, null); }

    @Override
    public ParenthesizedHavingBuilder<DefaultHavingBuilder> startParenthesizedGroup() {
        return new ParenthesizedHavingBuilder<>(this);
    }

}
