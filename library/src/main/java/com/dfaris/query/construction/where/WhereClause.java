package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Query;

public abstract class WhereClause extends Clause {

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
