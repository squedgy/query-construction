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

	public static <QueryType extends Query, Parent extends WhereParent<QueryType>> DefaultWhereClauseBuilder<QueryType, Parent> where(Parent parent) {
		return new DefaultWhereClauseBuilder<>(parent);
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
