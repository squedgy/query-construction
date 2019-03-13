package com.dfaris.query.construction;

import java.util.List;

public interface Clause{

	public abstract String getClauseStarter();

	public enum ClauseType {
		OVERRIDING,
		VALUES,
		CONFLICT,
		EXPRESSION,
		SET,
		RETURNING,
		FROM,
		USING,
		WHERE,
		GROUP_BY,
		HAVING,
		WINDOW,
		ORDER_BY,
		LIMIT,
		OFFSET,
		FETCH,
		FOR;

		public final String convertToClauseString(List<Clause> clauses) {
			StringBuilder clauseString = new StringBuilder();
			for (Clause c : clauses) {
				if (clauseString.length() == 0) {
					clauseString.append(c.getClauseStarter());
				}
				clauseString.append(c.toString());
			}
			return clauseString.toString();
		}


	}

}
