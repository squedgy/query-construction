package com.dfaris.query.construction.predicate.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.predicate.Predicate;

public class WhereClause extends Predicate {

	private Predicate p;

	public WhereClause(String col, String op, String val) {
		super(col, op, val);
	}

	private WhereClause(Predicate p) {
		super(p);
		this.p = p;
	}

	static WhereClause wrap(Predicate p) { return new WhereClause(p); }

	public static DefaultWhereClauseBuilder where() {
		return new DefaultWhereClauseBuilder();
	}

	@Override
	public final String getClauseStarter() {
		return "WHERE ";
	}

	@Override
	public String toString() {
		if(p != null) return p.toString();
		return super.toString();
	}

}
