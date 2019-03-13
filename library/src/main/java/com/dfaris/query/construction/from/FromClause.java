package com.dfaris.query.construction.from;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.from.join.JoinClause;

import java.util.List;

public class FromClause implements Clause {

	private final String table;
	private final String alias;
	private final List<JoinClause> joins;

	FromClause(String tableName, String alias, List<JoinClause> joins) {
		this.table = tableName;
		this.alias = alias;
		this.joins = joins;
	}

	public String getTable() {
		return table;
	}

	public List<JoinClause> getJoins() {
		return joins;
	}

	public String getAlias() {
		return alias;
	}

	@Override
	public String toString() {
		StringBuilder joinBuilder = new StringBuilder();
		StringBuilder format = new StringBuilder();
		for (JoinClause join : joins) {
			format.append('(');
			joinBuilder.append(join.toString()).append(' ');
		}
		format.append("%s %s %s");
		return String.format(format.toString(),
				table,
				alias,
				joinBuilder);
	}

	@Override
	public String getClauseStarter() {
		return "FROM ";
	}

}
