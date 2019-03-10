package com.dfaris.query.construction;

import java.util.List;

public abstract class Clause extends Stringable {
	/**
	 * An overriden to string that is a formatted clause
	 * Ex.
	 * <pre>
	 *     person_id = 4 or
	 *     person_fk in (1,2,3,4,5,6,7,8)
	 *     (person_id = 4 or person_fk in (1,2,3,4,5,6,7,8)) and
	 *     person_id = ?
	 *     person_fk in ? and
	 *     person p join address a on p.address_id = a.address_id
	 *     p.person_id as id, a.address as address, a.address2 as 'line 2'
	 *     p.person_id desc, a.address asc
	 *     0, 1, 2
	 *     p.person_id
	 *     *
	 * </pre>
	 *
	 * @return a formatted clause String
	 */
	public abstract String toString();

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
