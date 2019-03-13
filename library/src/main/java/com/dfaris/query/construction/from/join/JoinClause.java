package com.dfaris.query.construction.from.join;

import com.dfaris.query.construction.Clause;

public abstract class JoinClause extends Clause {

	@Override
	public String getClauseStarter() {
		return "JOIN";
	}

	public enum Type {
		CROSS((t,a,c,o,oc) -> new CrossJoin(t, a)),
		INNER(InnerJoin::new),
		LEFT(LeftJoin::new),
		RIGHT(RightJoin::new),
		FULL(FullJoin::new);

		private final JoinBuilder builder;
		Type(JoinBuilder builder) {
			this.builder = builder;
		}

		public JoinClause build(String table, String alias, String column, String otherTableAlias, String otherTableColumn) {
			return builder.build(table, alias, column, otherTableAlias, otherTableColumn);
		}
	}

	@FunctionalInterface
	private static interface JoinBuilder {
		JoinClause build(String table, String alias, String column, String otherTableAlias, String otherTableColumn);
	}

}
