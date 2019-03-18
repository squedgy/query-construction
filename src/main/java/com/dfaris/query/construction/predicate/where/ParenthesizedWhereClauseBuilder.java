package com.dfaris.query.construction.predicate.where;

import com.dfaris.query.construction.predicate.IndividualPredicateBuilder;
import com.dfaris.query.construction.predicate.ParenedPredicate;

import static com.dfaris.query.construction.predicate.where.WhereClause.wrap;

/**
 * Deals with parenthesized groups from {@link DefaultWhereClauseBuilder}
 * @param <Parent> What created the current instance of this builder
 */
public class ParenthesizedWhereClauseBuilder<Parent extends WhereParent>
		extends WhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>,
									ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>>> {

	private Parent parent;

	ParenthesizedWhereClauseBuilder(Parent parent) {
		super(null, null);
		this.refe = this;
		this.parent = parent;
		if(parent instanceof IndividualPredicateBuilder) binding = ((IndividualPredicateBuilder) parent).isBinding();
	}

	@Override
	public ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}

	public Parent endParenthesizedGroup() {
		parent.setPredicate(wrap(new ParenedPredicate(build())));
		return parent;
	}

}
