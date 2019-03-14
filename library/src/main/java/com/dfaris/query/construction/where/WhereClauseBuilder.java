package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.predicate.MultiPredicateBuilder;
import com.dfaris.query.construction.structure.predicate.Predicate;

public abstract class WhereClauseBuilder<QueryType extends Query,
											Parent extends WhereParent<QueryType>,
											This extends WhereClauseBuilder,
											StartParenReturn>
		extends MultiPredicateBuilder<This, Predicate,StartParenReturn>
		implements WhereParent<QueryType> {

	protected final Parent parent;

	WhereClauseBuilder(Parent parent, WhereClause a, String andOr) {
		super(a, andOr);
		this.parent = parent;
	}

	WhereClauseBuilder(Parent parent) {
		this(parent, null, null);
	}

	@Override
	protected Predicate buildIndividualClause() throws IllegalStateException{
		if(canBuildIndividualClause()) return new WhereClause(column, operator, String.join(",", constants));
		throw new IllegalStateException(String.format("Column, Operator, Or constants where not correctly setup for a clause.\n\tcolumn=\"%s\"\n\toperator=\"%s\"\n\tconstants=%s",
				column,
				operator,
				constants));
	}


	@Override
	protected Predicate buildCompoundClause() throws IllegalStateException{
		Predicate ret;
		if (!canBuildCompoundClause()) {
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

	public Parent getParent() {
		return parent;
	}

	public Parent continueBuilding() throws IllegalStateException {
		if(a != null) parent.setPredicate(a);
		return parent;
	}

	@Override
	public QueryType build() {
		parent.setPredicate(buildCompoundClause());
		return parent.build();
	}


	@Override
	public void setPredicate(Predicate predicate) throws IllegalStateException {
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
