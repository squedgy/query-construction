package com.dfaris.query.construction.where;

import com.dfaris.query.construction.structure.predicate.Predicate;

public class CompoundWhereClause extends WhereClause {

	CompoundWhereClause(Predicate a, String andOr, Predicate b) {
		super(a.toString(), andOr.toUpperCase(), b.toString());
		if (!andOr.matches("and|or")) {
			throw new IllegalArgumentException("andOr must be either \"and\" OR \"or\" exactly. found: \"" + andOr + "\"");
		}
	}

}
