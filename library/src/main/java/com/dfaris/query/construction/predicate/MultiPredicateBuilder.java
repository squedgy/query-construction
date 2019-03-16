package com.dfaris.query.construction.predicate;

import java.util.LinkedList;

public abstract class MultiPredicateBuilder<This extends MultiPredicateBuilder,
									PredicateType extends Predicate,
									StartParenReturn>
		extends IndividualPredicateBuilder<This, PredicateType>{

	protected String andOr;
	protected PredicateType a;

	public MultiPredicateBuilder(PredicateType a, String andOr) {
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

	protected boolean canBuildCompound() {
		return super.canBuild() && andOr != null && a != null;
	}

	public This and() {
		a = build();
		this.andOr = "and";
		return refe;
	}

	public This or() {
		a = build();
		this.andOr = "or";
		return refe;
	}

	public abstract StartParenReturn startParenthesizedGroup();

}
