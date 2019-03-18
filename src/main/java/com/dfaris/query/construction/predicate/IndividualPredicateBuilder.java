package com.dfaris.query.construction.predicate;

import com.dfaris.query.construction.ValueConverters;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dfaris.query.construction.ValueConverters.getBindingValueOf;

/**
 * builds an individual basic {@link Predicate} with no inner predicates.
 * If extending this class ensure to set refe to an instance of this after calling super;
 * @param <ThisBuilderType> The builder type which extends this class
 * @param <ClauseType> The type of clause that's being built
 */
public abstract class IndividualPredicateBuilder<ThisBuilderType extends IndividualPredicateBuilder, ClauseType extends Predicate>  {

	protected String column;
	protected String operator;
	protected List<String> constants;
	protected boolean binding;
	protected ThisBuilderType refe;

	public IndividualPredicateBuilder() {
		this.constants = new LinkedList<>();
	}

	/**
	 * Set the left side operator of the predicate
	 * @param column the column you're testing
	 * @return refe
	 */
	public final ThisBuilderType column(String column) {
		this.column = column;
		return refe;
	}

	/**
	 * The list/array of values the column should be within
	 * @param values List of values the column can be within
	 * @return refe
	 */
	public final ThisBuilderType in(Object... values) {
		this.operator = "in";
		this.values(values);
		return refe;
	}

	/**
	 * SQL like % 0-infinity characters _ any single character.
	 * If you need more look it up.
	 * @param like the String the column should be like
	 * @return refe
	 */
	public final ThisBuilderType like(String like) {
		this.operator = "LIKE";
		this.value(like);
		return refe;
	}

	public final ThisBuilderType greaterThan(Object value) {
		this.operator = ">";
		value(value);
		return refe;
	}

	public final ThisBuilderType lessThan(Object value) {
		this.operator = "<";
		value(value);
		return refe;
	}

	public final ThisBuilderType greaterThanOrEqualTo(Object value) {
		this.operator = ">=";
		value(value);
		return refe;
	}

	public final ThisBuilderType lessThanOrEqualTo(Object value) {
		this.operator = "<=";
		value(value);
		return refe;
	}

	public final ThisBuilderType equalTo(Object value) {
		this.operator = "=";
		value(value);
		return refe;
	}

	public final ThisBuilderType notEqualTo(Object value) {
		this.operator = "<>";
		value(value);
		return refe;
	}

	public final ThisBuilderType notNull() {
		this.operator = "IS NOT";
		this.constants = Collections.singletonList("NULL");
		return refe;
	}

	public final ThisBuilderType isNull() {
		this.operator = "IS";
		this.constants = Collections.singletonList("NULL");
		return refe;
	}

	public final ThisBuilderType isTrue() {
		tf("<>");
		return refe;
	}

	private void tf(String op) {
		column = "CAST(" + column + " AS INTEGER)";
		operator = op;
		constants = Collections.singletonList("0");
	}

	public final ThisBuilderType isFalse() {
		tf("=");
		return refe;
	}

	protected final ThisBuilderType values(Object... constants) {
		return values(Arrays.asList(constants));
	}

	protected ThisBuilderType values(List<?> constants) {
		if(isBinding()) {
			this.constants = Collections.singletonList(convertObjectToString(constants));
		} else {
			this.constants = constants
					.stream()
					.map(this::convertObjectToString)
					.collect(Collectors.toList());
		}
		return refe;
	}

	protected ThisBuilderType value(Object constant) {
		this.constants = Collections.singletonList(convertObjectToString(constant));
		return refe;
	}

	/**
	 * probably shouldn't need this, but whatever
	 * @return this but with nullified fields
	 */
	public ThisBuilderType reset() {
		this.constants = null;
		this.column = null;
		this.operator = null;
		return refe;
	}

	/**
	 * If you want to create bind references call this method.
	 * ONLY send one of the following as values after calling this method: String, Integer (for positional bind queries), or a List&gt;String&lt; with only ONE entry for a list attribute bind
	 * @return refe
	 */
	public final ThisBuilderType binding() {
		binding = true;
		return refe;
	}

	/**
	 * Switch back to taking value Objects as what they represent.
	 * @return refe
	 */
	public final ThisBuilderType literal() {
		binding = false;
		return refe;
	}

	protected boolean canBuild() {
		return column != null && operator != null && constants.size() > 0;
	}

	public final boolean isBinding() { return binding; }

	protected String convertObjectToString(Object o) {
		if(this.isBinding()) return getBindingValueOf(o);
		return ValueConverters.getSqlValueOf(o);
	}

	/**
	 * Builds a clause of Class Type ClauseType
	 * @return a clause of ClauseType
	 */
	public abstract ClauseType build();

}
