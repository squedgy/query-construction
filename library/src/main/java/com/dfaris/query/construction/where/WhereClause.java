package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Clause;

public abstract class WhereClause extends Clause {

	public static <Parent extends WhereParent> IndividualWhereClauseBuilder<Parent> where(Parent parent) {
		return new IndividualWhereClauseBuilder<>(parent);
	}

	public static <Parent extends WhereParent> IndividualWhereClauseBuilder<Parent> bindableWhere(Parent parent) {
		return new BindableIndividualWhereClauseBuilder<>(parent);
	}

	@Override
	public final String getClauseStarter() {
		return "WHERE ";
	}
}
