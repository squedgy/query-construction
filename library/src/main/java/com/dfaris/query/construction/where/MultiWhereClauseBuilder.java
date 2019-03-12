package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;

public class MultiWhereClauseBuilder<QueryType extends Query,
		Parent extends WhereParent<QueryType>>
		extends AbstractMultiWhereBuilder<Parent,
		MultiWhereClauseBuilder<QueryType, Parent>,
		MultiWhereClauseBuilder<QueryType, Parent>,
		ParenthesizedWhereClauseBuilder<QueryType, MultiWhereClauseBuilder<QueryType, Parent>>>
		implements WhereParent<QueryType> {
	private String andOr;
	private WhereClause a;
	private ParenGroup parenGroup;

	MultiWhereClauseBuilder(IndividualWhereClauseBuilder<QueryType, Parent> builder, String andOr) {
		this(builder, builder.buildClause(), andOr);
	}

	MultiWhereClauseBuilder(AbstractWhereBuilder<Parent, ?, ?, ?> builder, WhereClause a, String andOr) {
		super(builder.parent, a, andOr);
		this.binding = builder.binding;
		this.a = a;
		this.andOr = andOr;
		this.refe = this;
	}

	public MultiWhereClauseBuilder<QueryType, Parent> and() {
		this.a = buildClause();
		this.andOr = "and";
		return this.refe;
	}

	public MultiWhereClauseBuilder<QueryType, Parent> or() {
		this.a = buildClause();
		this.andOr = "or";
		return this.refe;
	}

	public ParenthesizedWhereClauseBuilder<QueryType, MultiWhereClauseBuilder<QueryType, Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}

	public QueryType build() {
		WhereClause finalWhere;
		if (this.andOr != null) {
			finalWhere = buildClause();
		} else {
			if (a == null) {
				if (parenGroup != null) {
					finalWhere = parenGroup;
				} else {
					finalWhere = buildClause();
				}
			} else {
				finalWhere = a;
			}
		}
		if (this.parenGroup != null) {
			if (finalWhere != null)
				finalWhere = new CompoundWhereClause(parenGroup.getClause(), parenGroup.getFollowedBy(), finalWhere);
			else finalWhere = parenGroup;
		}
		parent.setWhere(finalWhere);
		return parent.build();
	}

}
