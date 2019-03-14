package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.predicate.IndividualPredicateBuilder;
import com.dfaris.query.construction.structure.predicate.ParenedPredicate;
import com.dfaris.query.construction.structure.predicate.Predicate;

public class ParenthesizedWhereClauseBuilder<QueryType extends Query,
											Parent extends WhereParent<QueryType>>
		extends WhereClauseBuilder<QueryType,
														Parent,
														ParenthesizedWhereClauseBuilder<QueryType, Parent>,
														ParenthesizedWhereClauseBuilder<QueryType, ParenthesizedWhereClauseBuilder<QueryType, Parent>>>
		implements WhereParent<QueryType> {

	ParenthesizedWhereClauseBuilder(Parent parent) {
		super(parent, null, null);
		this.refe = this;
		if(parent instanceof IndividualPredicateBuilder) binding = ((IndividualPredicateBuilder) parent).isBinding();
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, Parent> and() {
		this.a = buildCompoundClause();
		this.andOr = "and";
		return this;
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, Parent> or() {
		this.a = buildCompoundClause();
		this.andOr = "or";
		return this;
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, ParenthesizedWhereClauseBuilder<QueryType, Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}

	public Parent endParenthesizedGroup() {
		Predicate finalClause = buildCompoundClause();
		parent.setPredicate(new ParenedPredicate(finalClause));
		return parent;
	}

	@Override
	public QueryType build() {
		parent.setPredicate(a);
		return parent.build();
	}

	private void setParentWhere(String andOr) {
		ParenedPredicate clause = new ParenedPredicate(buildCompoundClause());
		parent.setPredicate(new IndividualWhereClause(clause));
	}

	@Override
	public void setPredicate(Predicate clause) {
		if(a == null) a = clause;
		else if (andOr != null) {
			a = new CompoundWhereClause(a, andOr, clause);
			andOr = null;
		} else throw new IllegalStateException("Builder doesn't know what ");
	}

	@Override
	protected Predicate buildCompoundClause() {
		Predicate ret;
		try{
			ret = super.buildCompoundClause();
		} catch (IllegalStateException e ) {
			if(a != null) ret = a;
			else throw e;
		}
		clear();
		return ret;
	}
}
