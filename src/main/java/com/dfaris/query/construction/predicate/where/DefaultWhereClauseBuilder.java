package com.dfaris.query.construction.predicate.where;

public class DefaultWhereClauseBuilder
		extends WhereClauseBuilder<DefaultWhereClauseBuilder,
									ParenthesizedWhereClauseBuilder<DefaultWhereClauseBuilder>>{

	DefaultWhereClauseBuilder(WhereClause a, String andOr) {
		super(a, andOr);
		this.refe = this;
	}

	DefaultWhereClauseBuilder() { this(null, null); }

	@Override
	public ParenthesizedWhereClauseBuilder<DefaultWhereClauseBuilder> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}

}
