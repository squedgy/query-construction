package com.dfaris.query.construction.having;

import com.dfaris.query.construction.structure.predicate.IndividualPredicateBuilder;
import com.dfaris.query.construction.structure.predicate.ParenedPredicate;

import static com.dfaris.query.construction.having.HavingClause.wrap;

public class ParenthesizedHavingBuilder<Parent extends HavingParent>
		extends HavingClauseBuilder<Parent, ParenthesizedHavingBuilder<Parent>, ParenthesizedHavingBuilder<ParenthesizedHavingBuilder<Parent>>> {

	public ParenthesizedHavingBuilder(Parent parent) {
		super(parent, null, null);
		this.refe = this;
		if(parent instanceof IndividualPredicateBuilder) binding = ((IndividualPredicateBuilder)parent).isBinding();
	}

	@Override
	public ParenthesizedHavingBuilder<ParenthesizedHavingBuilder<Parent>> startParenthesizedGroup() {
		return new ParenthesizedHavingBuilder<>(this);
	}

	public Parent endParenthesizedGroup() {
		parent.setPredicate(wrap(new ParenedPredicate(buildCompoundClause())));
		return parent;
	}

}
