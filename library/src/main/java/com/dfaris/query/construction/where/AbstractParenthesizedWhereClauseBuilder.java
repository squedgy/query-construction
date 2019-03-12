package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;

public abstract class AbstractParenthesizedWhereClauseBuilder<Parent extends WhereParent, This, AndOrReturn, StartParenReturn, EndParenReturn>
		extends AbstractMultiWhereBuilder<Parent, This, AndOrReturn, StartParenReturn> {


	AbstractParenthesizedWhereClauseBuilder(Parent parent, WhereClause a, String andOr) { super(parent, a, andOr); }

	AbstractParenthesizedWhereClauseBuilder(Parent parent) { this(parent, null, null); }

	public abstract EndParenReturn endParenthesizedGroupAnd();

	public abstract EndParenReturn endParenthesizedGroupOr();

	public abstract Query endParenthesizedGroup();

}
