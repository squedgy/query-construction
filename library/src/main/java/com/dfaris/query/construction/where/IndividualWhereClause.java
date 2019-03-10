package com.dfaris.query.construction.where;

import java.util.List;

public class IndividualWhereClause extends WhereClause {
	private String column;
	private String operator;
	private List<String> constants;

	IndividualWhereClause(String column, String operator, List<String> constants) {
		if (column == null || column.length() == 0 || operator == null || operator.length() == 0 || constants == null || constants.size() == 0) {
			throw new IllegalArgumentException("column, operator, and constants must all be non-null and non-empty!");
		}
		this.column = column;
		this.operator = operator;
		this.constants = constants;
	}

	IndividualWhereClause(IndividualWhereClause clause) {
		this(clause.column, clause.operator, clause.constants);
	}

	public String getColumn() {
		return column;
	}

	public String getOperator() {
		return operator;
	}

	public List<String> getConstants() {
		return constants;
	}

	@Override
	public String toString() {
		String values;
		String column = this.column;
		if (constants.size() > 1 || operator.equals("in")) {
			values = "(" + String.join(",", constants) + ")";
		} else if (constants.size() == 1) {
			if (constants.get(0).equals("0") && operator.matches("=|<>")) {
				column = "CAST(" + column + " AS INTEGER)";
			}
			values = constants.get(0);
		} else {
			throw new RuntimeException("Constants didn't exist: " + constants);
		}
		String format = "%s %s %s";
		return String.format(format, column, operator, values);
	}

}
