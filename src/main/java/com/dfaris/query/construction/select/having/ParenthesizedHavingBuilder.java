package com.dfaris.query.construction.select.having;

import com.dfaris.query.construction.predicate.IndividualPredicateBuilder;
import com.dfaris.query.construction.predicate.ParenedPredicate;

import static com.dfaris.query.construction.select.having.HavingClause.wrap;

public class ParenthesizedHavingBuilder<Parent extends HavingParent>
		extends HavingClauseBuilder<ParenthesizedHavingBuilder<Parent>, ParenthesizedHavingBuilder<ParenthesizedHavingBuilder<Parent>>> {

	private Parent parent;

	public ParenthesizedHavingBuilder(Parent parent) {
		super(null, null);
		this.refe = this;
		this.parent = parent;
		if(parent instanceof IndividualPredicateBuilder) binding = ((IndividualPredicateBuilder)parent).isBinding();
	}

	@Override
	public ParenthesizedHavingBuilder<ParenthesizedHavingBuilder<Parent>> startParenthesizedGroup() {
		return new ParenthesizedHavingBuilder<>(this);
	}

	public Parent endParenthesizedGroup() {
		parent.setPredicate(wrap(new ParenedPredicate(build())));
		return parent;
	}

}
