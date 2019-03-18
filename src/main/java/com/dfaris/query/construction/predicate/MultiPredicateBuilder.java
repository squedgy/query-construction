package com.dfaris.query.construction.predicate;

import java.util.LinkedList;

/**
 * Allows a PredicateBuilder to create CompoundPredicates.
 * @param <This> Whatever class is implementing this one
 * @param <PredicateType> The Predicate this class creates
 * @param <StartParenReturn> When you start a parenthesized group what do you make
 */
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

	/**
	 * Finish the current predicate and prepare for another one joined to previous one via "AND"
	 * @return this
	 */
	public This and() {
		a = build();
		this.andOr = "and";
		return refe;
	}

	/**
	 * Finish the current predicate and prepore for another one joined to previous one via "OR"
	 * @return this
	 */
	public This or() {
		a = build();
		this.andOr = "or";
		return refe;
	}

	/**
	 * Create a parenthesized group
	 * @return this
	 */
	public abstract StartParenReturn startParenthesizedGroup();

}
