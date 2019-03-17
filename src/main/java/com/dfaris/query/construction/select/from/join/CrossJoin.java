package com.dfaris.query.construction.select.from.join;

public class CrossJoin extends JoinClause {

	private final String table;
	private final String alias;

	CrossJoin(String table, String alias) {
		this.table = table;
		this.alias = alias;
	}

	CrossJoin(String table) {
		this(table, table);
	}

	@Override
	public String toString() {
		return String.format("CROSS JOIN %s %s)", table, alias);
	}
}
