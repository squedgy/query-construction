package com.dfaris.query.construction.structure.predicate;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.QueryBuilder;

public interface PredicateParent<QueryType extends Query, PredicateType extends Predicate> extends QueryBuilder<QueryType> {

	void setPredicate(PredicateType predicate);

}
