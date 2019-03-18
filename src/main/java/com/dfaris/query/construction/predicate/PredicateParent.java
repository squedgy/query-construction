package com.dfaris.query.construction.predicate;

public interface PredicateParent<PredicateType extends Predicate> {

	void setPredicate(PredicateType predicate);

}
