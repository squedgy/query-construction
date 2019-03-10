package com.dfaris.query.construction.from.join;

import com.dfaris.query.construction.Clause;

public abstract class JoinClause extends Clause {

	@Override
	public String getClauseStarter() {
		return "JOIN";
	}

	public enum Type {
		CROSS,
		INNER,
		LEFT,
		RIGHT,
		FULL;
	}

}
