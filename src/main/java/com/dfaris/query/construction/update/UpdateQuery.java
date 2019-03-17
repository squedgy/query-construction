package com.dfaris.query.construction.update;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.predicate.where.DefaultWhereClauseBuilder;
import com.dfaris.query.construction.predicate.where.WhereClause;
import com.dfaris.query.construction.update.set.SetClause;

import java.util.Optional;
import java.util.function.Function;

public class UpdateQuery extends Query {

	private final String table;
	private final SetClause set;
	private final Optional<WhereClause> where;

	public UpdateQuery(String table, SetClause set, Optional<WhereClause> where) {
		this.table = table;
		this.set = set;
		this.where = where;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder("UPDATE ").append(table)
				.append(' ')
				.append(set.getClauseStarter())
				.append(set.toString())
				.append(' ');
		where.ifPresent(w -> builder.append(w.getClauseStarter()).append(w.toString()));

		return builder.toString();
	}

	public static class UpdateBuilder {

		private final String table;
		private SetClause set;
		private WhereClause where;

		private UpdateBuilder(String table) {
			this.table = table;
		}

		public static UpdateBuilder update(String table) {
			return new UpdateBuilder(table);
		}

		public UpdateBuilder set(Function<SetClause.SetClauseBuilder, SetClause> callback) {
			this.set = callback.apply(SetClause.SetClauseBuilder.set());
			return this;
		}

		public UpdateBuilder where(Function<DefaultWhereClauseBuilder, WhereClause> callback) {
			this.where = callback.apply(WhereClause.where());
			return this;
		}

		public UpdateQuery build() {
			return new UpdateQuery(table, set, Optional.ofNullable(where));
		}

	}

}
