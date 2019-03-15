package com.dfaris.query.construction.predicate.where;

import com.dfaris.query.construction.Query;

public class DefaultWhereClauseBuilder<QueryType extends Query, Parent extends WhereParent<QueryType>>
		extends WhereClauseBuilder<QueryType,
									Parent,
									DefaultWhereClauseBuilder<QueryType, Parent>,
									ParenthesizedWhereClauseBuilder<QueryType, DefaultWhereClauseBuilder<QueryType, Parent>>>{


	DefaultWhereClauseBuilder(Parent parent, WhereClause a, String andOr) {
		super(parent, a, andOr);
		this.refe = this;
	}

	DefaultWhereClauseBuilder(Parent parent) { this(parent, null, null); }

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, DefaultWhereClauseBuilder<QueryType, Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}
}
