package com.dfaris.query.construction.structure.predicate;

public abstract class MultiPredicateBuilder<This extends MultiPredicateBuilder,
									StartParenReturn>
		extends IndividualPredicateBuilder<This, StartParenReturn>{

	protected String andOr;
	protected Predicate a;

	public MultiPredicateBuilder(Predicate a, String andOr) {
		this.a = a;
		this.andOr = andOr;
	}

	protected final boolean canBuildCompoundClause() {
		return super.canBuildIndividualClause() && andOr != null && a != null;
	}

	protected abstract Predicate buildCompoundClause();

	public This and() {
		this.andOr = "and";
		return refe;
	}

	public This or() {
		this.andOr = "or";
		return refe;
	}

	public abstract StartParenReturn startParenthesizedGroup();

}
