package com.dfaris.query.construction.structure.predicate;

import java.util.LinkedList;

public abstract class MultiPredicateBuilder<This extends MultiPredicateBuilder,
									StartParenReturn>
		extends IndividualPredicateBuilder<This, StartParenReturn>{

	protected String andOr;
	protected Predicate a;

	public MultiPredicateBuilder(Predicate a, String andOr) {
		this.a = a;
		this.andOr = andOr;
	}

	protected void clear() {
		this.andOr = null;
		this.a = null;
		this.column = null;
		this.operator = null;
		this.constants = new LinkedList<>();
	}

	protected final boolean canBuildCompoundClause() {
		return super.canBuildIndividualClause() && andOr != null && a != null;
	}

	protected abstract Predicate buildCompoundClause();

	public This and() {
		a = buildCompoundClause();
		this.andOr = "and";
		return refe;
	}

	public This or() {
		a = buildCompoundClause();
		this.andOr = "or";
		return refe;
	}

	public abstract StartParenReturn startParenthesizedGroup();

}
