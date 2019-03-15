package com.dfaris.query.construction.predicate.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.predicate.IndividualPredicateBuilder;
import com.dfaris.query.construction.predicate.ParenedPredicate;

import static com.dfaris.query.construction.predicate.where.WhereClause.wrap;

public class ParenthesizedWhereClauseBuilder<QueryType extends Query,
											Parent extends WhereParent<QueryType>>
		extends WhereClauseBuilder<QueryType,
														Parent,
														ParenthesizedWhereClauseBuilder<QueryType, Parent>,
														ParenthesizedWhereClauseBuilder<QueryType, ParenthesizedWhereClauseBuilder<QueryType, Parent>>> {

	ParenthesizedWhereClauseBuilder(Parent parent) {
		super(parent, null, null);
		this.refe = this;
		if(parent instanceof IndividualPredicateBuilder) binding = ((IndividualPredicateBuilder) parent).isBinding();
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, ParenthesizedWhereClauseBuilder<QueryType, Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}

	public Parent endParenthesizedGroup() {
		parent.setPredicate(wrap(new ParenedPredicate(buildCompoundClause())));
		return parent;
	}

}
