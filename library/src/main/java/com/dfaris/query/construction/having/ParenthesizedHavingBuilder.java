package com.dfaris.query.construction.having;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.select.SelectQuery;
import com.dfaris.query.construction.structure.predicate.Predicate;
import com.dfaris.query.construction.structure.predicate.PredicateParent;

public class ParenthesizedHavingBuilder extends HavingBuilder {

	public ParenthesizedHavingBuilder(SelectQuery.SelectQueryBuilder parent, Predicate a, String andOr) {
		super(a, andOr);
	}

}
