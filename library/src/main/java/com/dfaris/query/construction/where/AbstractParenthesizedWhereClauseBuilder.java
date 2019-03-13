package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.ParenedPredicate;
import com.dfaris.query.construction.structure.Predicate;

public abstract class AbstractParenthesizedWhereClauseBuilder<QueryType extends Query,
															Parent extends WhereParent<QueryType>,
															This,
															AndOrReturn,
															StartParenReturn>
		extends AbstractMultiWhereBuilder<QueryType,
										Parent,
										This,
										AndOrReturn,
										StartParenReturn> {


	AbstractParenthesizedWhereClauseBuilder(Parent parent, Predicate a, String andOr) { super(parent, a, andOr); }

	AbstractParenthesizedWhereClauseBuilder(Parent parent) { this(parent, null, null); }

	public abstract Parent endParenthesizedGroup();

}
