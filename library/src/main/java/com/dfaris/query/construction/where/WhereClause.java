package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.Predicate;

public abstract class WhereClause extends Predicate implements Clause {

	public WhereClause(String left, String operator, String right) {
		super(left, operator, right);
	}

	public WhereClause(Predicate p) {
		super(p);
	}

	public static <QueryType extends com.dfaris.query.construction.Query, Parent extends WhereParent<QueryType>> IndividualWhereClauseBuilder<QueryType, Parent> where(Parent parent) {
		return new IndividualWhereClauseBuilder<>(parent);
	}

	public static <QueryType extends Query, Parent extends WhereParent<QueryType>> IndividualWhereClauseBuilder<QueryType, Parent> bindableWhere(Parent parent) {
		return where(parent).binding();

	}

	@Override
	public final String getClauseStarter() {
		return "WHERE ";
	}

}
