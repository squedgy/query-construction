package com.dfaris.query.construction.predicate.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.predicate.MultiPredicateBuilder;
import com.dfaris.query.construction.predicate.Predicate;

public abstract class WhereClauseBuilder<This extends WhereClauseBuilder,
											StartParenReturn>
		extends MultiPredicateBuilder<This, WhereClause, StartParenReturn>
		implements WhereParent {

	WhereClauseBuilder(WhereClause a, String andOr) {
		super(a, andOr);
	}

	WhereClauseBuilder() {
		this(null, null);
	}

	protected WhereClause buildIndividualClause() throws IllegalStateException{
		if(canBuild()) return new WhereClause(column, operator, String.join(",", constants));
		throw new IllegalStateException(String.format("Column, Operator, Or constants where not correctly setup for a clause.\n\tcolumn=\"%s\"\n\toperator=\"%s\"\n\tconstants=%s",
				column,
				operator,
				constants));
	}


	@Override
	public WhereClause build() throws IllegalStateException{
		WhereClause ret;
		if (!canBuildCompound()) {
			try {
				ret = buildIndividualClause();
			} catch(IllegalStateException e) {
				if( a != null) ret = a;
				else throw e;
			}
		} else {
			ret = new WhereClause(a.toString(), andOr, buildIndividualClause().toString());
		}
		clear();
		return ret;
	}

	@Override
	public void setPredicate(WhereClause predicate) throws IllegalStateException {
		if(a == null ) a = predicate;
		else if (andOr != null){
			WhereClause temp = new WhereClause(a.toString(), andOr, predicate.toString());
			clear();
			a = temp;
		} else {
			throw new IllegalStateException("Attempted to set predicate on an existing builder without an and/or set.");
		}
	}

}
