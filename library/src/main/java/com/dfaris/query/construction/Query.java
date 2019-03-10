package com.dfaris.query.construction;

import com.dfaris.query.construction.Clause.ClauseType;

import java.util.Arrays;
import java.util.LinkedList;

import static com.dfaris.query.construction.Clause.ClauseType.*;

public abstract class Query {

	/**
	 * Returns a string representative of the query
	 *
	 * @return a query string
	 */
	@Override
	public abstract String toString();

	private static final class ClauseOrder {

		public final ClauseType type;
		public final boolean required;

		private ClauseOrder(ClauseType clauseType, boolean required) {
			this.type = clauseType;
			this.required = required;
		}
	}

	public enum Type {
		SELECT(
				new ClauseOrder(FROM, false),
				new ClauseOrder(WHERE, false),
				new ClauseOrder(GROUP_BY, false),
				new ClauseOrder(HAVING, false),
				new ClauseOrder(WINDOW, false),
				new ClauseOrder(ORDER_BY, false),
				new ClauseOrder(LIMIT, false),
				new ClauseOrder(OFFSET, false),
				new ClauseOrder(FETCH, false),
				new ClauseOrder(FOR, false)
		),
		UPDATE(
				new ClauseOrder(SET, true),
				new ClauseOrder(FROM, false),
				new ClauseOrder(WHERE, false),
				new ClauseOrder(RETURNING, false)
		),
		INSERT(
				new ClauseOrder(OVERRIDING, false),
				new ClauseOrder(VALUES, true),
				new ClauseOrder(CONFLICT, false),
				new ClauseOrder(RETURNING, false)
		),
		DELETE(
				new ClauseOrder(FROM, true),
				new ClauseOrder(USING, false),
				new ClauseOrder(RETURNING, false)
		);

		private final LinkedList<ClauseOrder> clauseOrder = new LinkedList<>();

		Type(ClauseOrder... clauseOrder) {
			this.clauseOrder.addAll(Arrays.asList(clauseOrder));
		}

		public ClauseOrder[] clauseOrder() {
			return clauseOrder.toArray(new ClauseOrder[0]);
		}
	}

}
