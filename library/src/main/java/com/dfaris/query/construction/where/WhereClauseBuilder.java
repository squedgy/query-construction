package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.predicate.MultiPredicateBuilder;
import com.dfaris.query.construction.structure.predicate.Predicate;

import java.util.LinkedList;

public abstract class WhereClauseBuilder<QueryType extends Query,
											Parent extends WhereParent<QueryType>,
											This extends WhereClauseBuilder,
											StartParenReturn>
		extends MultiPredicateBuilder<This, StartParenReturn>
		implements WhereParent<QueryType> {

	protected final Parent parent;

	WhereClauseBuilder(Parent parent, Predicate a, String andOr) {
		super(a, andOr);
		this.parent = parent;
	}

	WhereClauseBuilder(Parent parent) {
		this(parent, null, null);
	}

	@Override
	protected Predicate buildIndividualClause() {
		if(canBuildIndividualClause()) return new IndividualWhereClause(column, operator, String.join(",", constants));
		throw new IllegalStateException(String.format("Column, Operator, Or constants where not correctly setup for a clause.\n\tcolumn=\"%s\"\n\toperator=\"%s\"\n\tconstants=%s",
				column,
				operator,
				constants));
	}


	@Override
	protected Predicate buildCompoundClause() {
		Predicate ret;
		if (!canBuildCompoundClause()) {
			if (canBuildIndividualClause()) ret = buildIndividualClause();
			else if(a != null) ret = a;
			else throw new IllegalStateException("Cannot build clause and group either doesn't exist or must be followed by another clause");
		} else {
			ret = new CompoundWhereClause(a, andOr, buildIndividualClause());
		}
		clear();
		return ret;
	}

	public void clear() {
		this.constants = new LinkedList<>();
		this.operator = null;
		this.column = null;
		this.andOr = null;
		this.a = null;
	}

	public Parent getParent() {
		return parent;
	}

	public Parent continueBuilding() {
		parent.setPredicate(buildCompoundClause());
		return parent;
	}

	public QueryType build() {
		parent.setPredicate(buildCompoundClause());
		return parent.build();
	}


	@Override
	public void setPredicate(Predicate predicate) {
		if(a == null ) a = predicate;
		else if (andOr != null){
			Predicate temp = new CompoundWhereClause(a, andOr, predicate);
			clear();
			a = temp;
		} else {
			throw new IllegalStateException("Attempted to set predicate on an existing builder without an and/or set.");
		}

	}
}
