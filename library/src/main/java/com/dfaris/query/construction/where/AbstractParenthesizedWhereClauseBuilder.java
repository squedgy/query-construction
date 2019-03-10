package com.dfaris.query.construction.where;

public abstract class AbstractParenthesizedWhereClauseBuilder<Parent, This, AndOrReturn, StartParenReturn, EndParenReturn>
		extends AbstractMultiWhereBuilder<Parent, This, AndOrReturn, StartParenReturn> {


	AbstractParenthesizedWhereClauseBuilder(Parent parent, WhereClause a, String andOr) { super(parent, a, andOr); }

	AbstractParenthesizedWhereClauseBuilder(Parent parent) { this(parent, null, null); }

	public abstract EndParenReturn endParenthesizedGroupAnd();

	public abstract EndParenReturn endParenthesizedGroupOr();

	public abstract Parent endParenthesizedGroup();

}
