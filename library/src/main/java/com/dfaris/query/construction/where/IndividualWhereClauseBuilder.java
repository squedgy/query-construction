package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.ParenedPredicate;

public class IndividualWhereClauseBuilder<QueryType extends Query,
										Parent extends WhereParent<QueryType>>
		extends AbstractWhereBuilder<QueryType,
									Parent,
									IndividualWhereClauseBuilder<QueryType, Parent>,
									MultiWhereClauseBuilder<QueryType, Parent>,
									ParenthesizedWhereClauseBuilder<QueryType, MultiWhereClauseBuilder<QueryType, Parent>>> {

	private WhereClause clause;

	public IndividualWhereClauseBuilder(Parent parent) {
		super(parent);
		this.refe = this;
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, MultiWhereClauseBuilder<QueryType, Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(new MultiWhereClauseBuilder<>(this, null, null));
	}

	@Override
	public MultiWhereClauseBuilder<QueryType, Parent> and() {
		return new MultiWhereClauseBuilder<>(this, "and");
	}

	@Override
	public MultiWhereClauseBuilder<QueryType, Parent> or() {
		return new MultiWhereClauseBuilder<>(this, "or");
	}

	public boolean canBuild() {
		return column != null &&
				operator != null &&
				constants != null &&
				constants.size() > 0;
	}

}
