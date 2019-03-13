package com.dfaris.query.construction.where;

import com.dfaris.query.construction.structure.Predicate;

public class IndividualWhereClause extends WhereClause {

	public IndividualWhereClause(String column, String operator, String constant) {
		super(
				ensureColumn(column, operator, constant),
				operator,
				constant
		);
	}

	public IndividualWhereClause(Predicate p) {
		super(p);
	}

	private static String ensureColumn(String column, String operator, String constants) {
		if(operator.matches("=|<>") && constants.equals("0")){
			return "CAST(" + column + " AS INTEGER)";
		}
		return column;
	}

}
