package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.Predicate;

public class MultiWhereClauseBuilder<QueryType extends Query,
									Parent extends WhereParent<QueryType>>
		extends AbstractMultiWhereBuilder<QueryType,
										Parent,
										MultiWhereClauseBuilder<QueryType, Parent>,
										MultiWhereClauseBuilder<QueryType, Parent>,
										ParenthesizedWhereClauseBuilder<QueryType, MultiWhereClauseBuilder<QueryType, Parent>>>
		implements WhereParent<QueryType> {

	MultiWhereClauseBuilder(IndividualWhereClauseBuilder<QueryType, Parent> builder, String andOr) {
		this(builder, builder.buildClause(), andOr);
	}

	MultiWhereClauseBuilder(AbstractWhereBuilder<QueryType, Parent, ?, ?, ?> builder, Predicate a, String andOr) {
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
		parent.setWhere(buildClause());
		return parent.build();
	}

	@Override
	public void setWhere(Predicate clause) {
		if(a == null || andOr == null) a = clause;
		else a = new CompoundWhereClause(a, andOr, clause);
	}
}
