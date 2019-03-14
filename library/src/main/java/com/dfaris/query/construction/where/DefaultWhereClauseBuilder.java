package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.predicate.Predicate;

public class DefaultWhereClauseBuilder<QueryType extends Query, Parent extends WhereParent<QueryType>>
		extends WhereClauseBuilder<QueryType,
									Parent,
									DefaultWhereClauseBuilder<QueryType, Parent>,
									ParenthesizedWhereClauseBuilder<QueryType, DefaultWhereClauseBuilder<QueryType, Parent>>>{


	DefaultWhereClauseBuilder(Parent parent, Predicate a, String andOr) {
		super(parent, a, andOr);
		this.refe = this;
	}

	DefaultWhereClauseBuilder(Parent parent) { this(parent, null, null); }

	@Override
	public DefaultWhereClauseBuilder<QueryType, Parent> and() {
		a = buildCompoundClause();
		return super.and();
	}

	@Override
	public DefaultWhereClauseBuilder<QueryType, Parent> or() {
		a = buildCompoundClause();
		return super.or();
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, DefaultWhereClauseBuilder<QueryType, Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}
}
