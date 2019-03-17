package com.dfaris.query.construction.delete;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.predicate.where.DefaultWhereClauseBuilder;
import com.dfaris.query.construction.predicate.where.WhereClause;

import java.util.Optional;
import java.util.function.Function;

public final class DeleteQuery extends Query {

	private final String table;
	private final Optional<WhereClause> where;

	public DeleteQuery(String table, Optional<WhereClause> where) {
		this.table = table;
		this.where = where;
	}

	@Override
	public String toString() {
		StringBuilder query = new StringBuilder("DELETE FROM ")
				.append(table)
				.append(' ');

		where.ifPresent(w -> query.append(w.getClauseStarter()).append(w.toString()));

		return query.toString();
	}

	public static class DeleteBuilder {

		private final String table;
		private WhereClause where;

		private DeleteBuilder(String table) {
			this.table = table;
		}

		public static DeleteBuilder deleteFrom(String table) {
			return new DeleteBuilder(table);
		}

		public DeleteBuilder where(Function<DefaultWhereClauseBuilder, WhereClause> callback) {
			this.where = callback.apply(WhereClause.where());
			return this;
		}

		public DeleteQuery build() {
			return new DeleteQuery(table, Optional.ofNullable(where));
		}

	}
}
