package com.dfaris.query.construction.where;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.ValueConverters;
import com.dfaris.query.construction.structure.Predicate;

import static com.dfaris.query.construction.ValueConverters.*;

public abstract class AbstractWhereBuilder<QueryType extends Query,
										Parent extends WhereParent<QueryType>,
										This,
										AndOrReturn,
										ParenReturn> {

	protected final Parent parent;
	protected String column;
	protected String operator;
	protected List<String> constants;
	protected boolean binding;
	protected This refe;

	AbstractWhereBuilder(Parent parent) {
		this.parent = parent;
		this.constants = new LinkedList<>();
	}

	public Parent getParent() {
		return parent;
	}

	public This column(String column) {
		this.column = column;
		return refe;
	}

	public This in(Object... values) {
		this.operator = "in";
		this.values(values);
		return refe;
	}

	public This like(String like) {
		this.operator = "LIKE";
		this.value(like);
		return refe;
	}

	public This greaterThan(Object value) {
		this.operator = ">";
		value(value);
		return refe;
	}

	public This lessThan(Object value) {
		this.operator = "<";
		value(value);
		return refe;
	}

	public This greaterThanOrEqualTo(Object value) {
		this.operator = ">=";
		value(value);
		return refe;
	}

	public This lessThanOrEqualTo(Object value) {
		this.operator = "<=";
		value(value);
		return refe;
	}

	public This equalTo(Object value) {
		this.operator = "=";
		value(value);
		return refe;
	}

	public This notEqualTo(Object value) {
		this.operator = "<>";
		value(value);
		return refe;
	}

	public This notNull() {
		this.operator = "IS NOT";
		this.constants = Collections.singletonList("NULL");
		return refe;
	}

	public This isNull() {
		this.operator = "IS";
		this.constants = Collections.singletonList("NULL");
		return refe;
	}

	public This isTrue() {
		this.operator = "<>";
		this.constants = Collections.singletonList("0");
		return refe;
	}

	public This isFalse() {
		this.operator = "=";
		this.constants = Collections.singletonList("0");
		return refe;
	}

	public This values(Object... constants) {
		return values(Arrays.asList(constants));
	}

	public This values(List<?> constants) {
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

	public This value(Object constant) {
		this.constants = Collections.singletonList(convertObjectToString(constant));
		return refe;
	}

	public This reset() {
		this.constants = null;
		this.column = null;
		this.operator = null;
		return refe;
	}

	protected String convertObjectToString(Object o) {
		if(this.isBinding()) return getBindingValueOf(o);
		return ValueConverters.getSqlValueOf(o);
	}

	public Parent continueBuilding() {
		parent.setWhere(buildClause());
		return parent;
	}

	public abstract ParenReturn startParenthesizedGroup();

	public abstract AndOrReturn and();

	public abstract AndOrReturn or();

	protected boolean canBuildClause() {
		return column != null && operator != null && constants.size() > 0;
	}

	public final boolean isBinding() { return binding; }

	public final This binding() {
		binding = true;
		return refe;
	}

	public final This literal() {
		binding = false;
		return refe;
	}

	protected Predicate buildClause() {
		WhereClause ret = new IndividualWhereClause(column, operator, String.join(",", constants));
		column = null;
		operator = null;
		constants = new LinkedList<>();
		return ret;
	}

	public QueryType build() {
		parent.setWhere(buildClause());
		return parent.build();
	}

}
