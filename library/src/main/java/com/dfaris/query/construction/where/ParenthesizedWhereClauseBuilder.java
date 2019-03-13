package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.ParenedPredicate;
import com.dfaris.query.construction.structure.Predicate;

public class ParenthesizedWhereClauseBuilder<QueryType extends Query,
											Parent extends WhereParent<QueryType>>
		extends AbstractParenthesizedWhereClauseBuilder<QueryType,
														Parent,
														ParenthesizedWhereClauseBuilder<QueryType, Parent>,
														ParenthesizedWhereClauseBuilder<QueryType, Parent>,
														ParenthesizedWhereClauseBuilder<QueryType, ParenthesizedWhereClauseBuilder<QueryType, Parent>>>
		implements WhereParent<QueryType> {

	ParenthesizedWhereClauseBuilder(Parent parent) {
		super(parent, null, null);
		this.refe = this;
		if(parent instanceof AbstractWhereBuilder) binding = ((AbstractWhereBuilder) parent).binding;
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, Parent> and() {
		this.a = buildClause();
		this.andOr = "and";
		return this;
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, Parent> or() {
		this.a = buildClause();
		this.andOr = "or";
		return this;
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, ParenthesizedWhereClauseBuilder<QueryType, Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}

	@Override
	public Parent endParenthesizedGroup() {
		Predicate finalClause = buildClause();
		parent.setWhere(new ParenedPredicate(finalClause));
		return parent;
	}

	@Override
	public QueryType build() {
		parent.setWhere(a);
		return parent.build();
	}

	private void setParentWhere(String andOr) {
		ParenedPredicate clause = new ParenedPredicate(buildClause());
		parent.setWhere(new IndividualWhereClause(clause));
	}

	@Override
	public void setWhere(Predicate clause) {
		if(a == null) a = clause;
		else if (andOr != null) {
			a = new CompoundWhereClause(a, andOr, clause);
			andOr = null;
		} else throw new IllegalStateException("Builder doesn't know what ");
	}
}
