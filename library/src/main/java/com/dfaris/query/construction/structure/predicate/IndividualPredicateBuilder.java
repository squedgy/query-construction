package com.dfaris.query.construction.structure.predicate;

import com.dfaris.query.construction.ValueConverters;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dfaris.query.construction.ValueConverters.getBindingValueOf;

public abstract class IndividualPredicateBuilder<ThisBuilderType extends IndividualPredicateBuilder, ClauseType extends Predicate>  {

	protected String column;
	protected String operator;
	protected List<String> constants;
	protected boolean binding;
	protected ThisBuilderType refe;

	public IndividualPredicateBuilder() {
		this.constants = new LinkedList<>();
	}

	public final ThisBuilderType column(String column) {
		this.column = column;
		return refe;
	}

	public final ThisBuilderType in(Object... values) {
		this.operator = "in";
		this.values(values);
		return refe;
	}

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

	public final ThisBuilderType values(Object... constants) {
		return values(Arrays.asList(constants));
	}

	public ThisBuilderType values(List<?> constants) {
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

	public ThisBuilderType value(Object constant) {
		this.constants = Collections.singletonList(convertObjectToString(constant));
		return refe;
	}

	public ThisBuilderType reset() {
		this.constants = null;
		this.column = null;
		this.operator = null;
		return refe;
	}

	public final ThisBuilderType binding() {
		binding = true;
		return refe;
	}

	public final ThisBuilderType literal() {
		binding = false;
		return refe;
	}

	protected final boolean canBuildIndividualClause() {
		return column != null && operator != null && constants.size() > 0;
	}

	public final boolean isBinding() { return binding; }

	protected String convertObjectToString(Object o) {
		if(this.isBinding()) return getBindingValueOf(o);
		return ValueConverters.getSqlValueOf(o);
	}

	protected abstract ClauseType buildIndividualClause();

}
