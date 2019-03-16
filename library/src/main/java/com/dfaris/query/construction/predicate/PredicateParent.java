package com.dfaris.query.construction.predicate;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.QueryBuilder;

public interface PredicateParent<PredicateType extends Predicate> {

	void setPredicate(PredicateType predicate);

}
