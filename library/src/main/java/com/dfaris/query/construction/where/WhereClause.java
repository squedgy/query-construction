package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.predicate.Predicate;

public abstract class WhereClause extends Predicate {

	public WhereClause(String left, String operator, String right) {
		super(left, operator, right);
	}

	public WhereClause(Predicate p) {
		super(p);
	}

	public static <QueryType extends Query, Parent extends WhereParent<QueryType>> DefaultWhereClauseBuilder<QueryType, Parent> where(Parent parent) {
		return new DefaultWhereClauseBuilder<>(parent);
	}

	@Override
	public final String getClauseStarter() {
		return "WHERE ";
	}

}
