package com.dfaris.query.construction.from.join;

public class RightJoin extends InnerJoin {

	RightJoin(String joinTable, String joinTableAlias, String joinTableColumn, String onAlias, String onAliasColumn) {
		super(joinTable, joinTableAlias, joinTableColumn, onAlias, onAliasColumn);
	}

	@Override
	public String toString() {
		return String.format("RIGHT JOIN %s %s ON %s.%s = %s.%s)",
				joinTable,
				joinTableAlias,
				joinTableAlias,
				joinTableColumn,
				onAlias,
				onAliasColumn);
	}

}
