package com.dfaris.query.construction.select;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.predicate.where.DefaultWhereClauseBuilder;
import com.dfaris.query.construction.predicate.where.WhereClause;
import com.dfaris.query.construction.select.from.FromBuilder;
import com.dfaris.query.construction.select.from.FromClause;
import com.dfaris.query.construction.select.group.GroupByClause;
import com.dfaris.query.construction.select.having.DefaultHavingBuilder;
import com.dfaris.query.construction.select.having.HavingClause;
import com.dfaris.query.construction.select.order.OrderByClause;
import com.dfaris.query.construction.predicate.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SelectQuery extends Query {

	private final String[] columns;
	private final FromClause from;
	private final List<Optional<? extends Clause>> clauses;
	private static final Logger log = LoggerFactory.getLogger(SelectQuery.class);

	SelectQuery(String[] columns,
				FromClause from,
				Optional<Predicate> where,
				Optional<GroupByClause> groupBy,
				Optional<Predicate> having,
				Optional<OrderByClause> orderBy) {
		this.columns = columns;
		this.from = from;
		this.clauses = Arrays.asList(
			where,
			groupBy,
			having,
			orderBy
		);
	}

	@Override
	public String toString() {
		StringBuilder query = new StringBuilder("SELECT ");
		for (int i = 0; i < columns.length - 1; i++) {
			query.append(columns[i]).append(", ");
		}
		query.append(columns[columns.length - 1])
				.append(' ')
				.append(from.getClauseStarter())
				.append(from.toString());

		clauses.forEach(optional -> {
			optional.ifPresent(clause -> {
				query.append(clause.getClauseStarter())
						.append(clause.toString())
						.append(' ');
			});
		});

		return query.toString();
	}

	public static SelectQueryBuilder select(String... columns) {
		return new SelectQueryBuilder(columns);
	}

	public static class SelectQueryBuilder {

		protected final String[] columns;
		protected FromClause from;
		protected WhereClause where;
		protected GroupByClause groupBy;
		protected HavingClause having;
		protected OrderByClause orderBy;

		private SelectQueryBuilder(String[] columns) {
			this.columns = columns;
		}

		protected SelectQueryBuilder(SelectQueryBuilder builder) {
			this.columns = builder.columns;
			this.from = builder.from;
			this.where = builder.where;
			this.groupBy = builder.groupBy;
			this.having = builder.having;
			this.orderBy = builder.orderBy;
		}

		public SelectQueryBuilder from(String table, Function<FromBuilder, FromClause> callback) {
			return from(table, table, callback);
		}

		public SelectQueryBuilder from(String table, String alias, Function<FromBuilder, FromClause> callback) {
			from = callback.apply(new FromBuilder(table, alias));
			return this;
		}

		public SelectQueryBuilder from(String table, String alias) {
			from = new FromBuilder(table,alias).build();
			return this;
		}

		public SelectQueryBuilder from(String table) {
			return from(table, table);
		}

		public SelectQueryBuilder where(Function<DefaultWhereClauseBuilder, WhereClause> callback) {
			where = callback.apply(WhereClause.where());
			return this;
		}

		public SelectQueryBuilder groupBy(String... columns) {
			groupBy = new GroupByClause(Arrays.asList(columns));
			return this;
		}

		public SelectQueryBuilder having(Function<DefaultHavingBuilder, HavingClause> callback) {
			having = callback.apply(HavingClause.having());
			return this;
		}

		public SelectQueryBuilder orderBy(String... columns) {
			orderBy = new OrderByClause(new LinkedList<>(Arrays.asList(columns)));
			return this;
		}

		public SelectQuery build() {
			log.debug("columns: " + Arrays.toString(columns));
			log.debug("from: " + from);
			log.debug("where: " + where);
			log.debug("group: " + groupBy);
			log.debug("having: " + having);
			log.debug("order: " + orderBy);
			return new SelectQuery(columns, from, Optional.ofNullable(where), Optional.ofNullable(groupBy), Optional.ofNullable(having), Optional.ofNullable(orderBy));
		}

	}

}
