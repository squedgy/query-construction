package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;

public class IndividualWhereClauseBuilder<QueryType extends Query,
										Parent extends WhereParent<QueryType>>
		extends AbstractWhereBuilder<QueryType,
									Parent,
									IndividualWhereClauseBuilder<QueryType, Parent>,
									MultiWhereClauseBuilder<QueryType, Parent>,
									ParenthesizedWhereClauseBuilder<QueryType, MultiWhereClauseBuilder<QueryType, Parent>>>
		implements ParenAppender {

	private WhereClause clause;
	private ParenGroup parenGroup;

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

	@Override
	public QueryType build() {
		WhereClause finalWhere = buildClause();
		if (parenGroup != null) finalWhere = new CompoundWhereClause(parenGroup, finalWhere);
		parent.setWhere(finalWhere);
		return parent.build();
	}

	@Override
	public void addWhere(ParenGroup clause) {
		if (this.parenGroup == null) parenGroup = clause;
		else this.parenGroup = new ParenGroup(parenGroup, clause);
	}

}
