package com.dfaris.query.construction.select;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.from.FromBuilder;
import com.dfaris.query.construction.from.FromClause;
import com.dfaris.query.construction.from.FromParent;
import com.dfaris.query.construction.group.GroupByBuilder;
import com.dfaris.query.construction.group.GroupByClause;
import com.dfaris.query.construction.group.GroupByParent;
import com.dfaris.query.construction.having.HavingClause;
import com.dfaris.query.construction.structure.Predicate;
import com.dfaris.query.construction.where.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class SelectQuery extends Query {

	private final String[] columns;
	private final FromClause from;
	private final List<Optional<? extends Clause>> clauses;
	private static final Logger log = LoggerFactory.getLogger(SelectQuery.class);

	SelectQuery(String[] columns,
				FromClause from,
				Optional<Predicate> where,
				Optional<GroupByClause> groupBy,
				Optional<HavingClause> having) {
		this.columns = columns;
		this.from = from;
		this.clauses = Arrays.asList(
			where,
			groupBy,
			having
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

	public static class SelectQueryBuilder implements FromParent, WhereParent<SelectQuery>, GroupByParent {

		protected final String[] columns;
		protected FromClause from;
		protected Predicate where;
		protected GroupByClause groupBy;
		protected HavingClause having;

		private SelectQueryBuilder(String[] columns) {
			this.columns = columns;
		}

		protected SelectQueryBuilder(SelectQueryBuilder builder) {
			this.columns = builder.columns;
			this.from = builder.from;
			this.where = builder.where;
			this.groupBy = builder.groupBy;
		}

		public FromBuilder from() {
			return new FromBuilder(this);
		}

		public FromBuilder from(String table) {
			return new FromBuilder(this, table);
		}

		public FromBuilder from(String table, String alias) {
			return new FromBuilder(this, table, alias);
		}

		public IndividualWhereClauseBuilder<SelectQuery, SelectQueryBuilder> where() {
			return WhereClause.where(this);
		}

		public GroupByBuilder groupBy(String... columns) {
			return new GroupByBuilder(this, columns);
		}

		public GroupByBuilder groupBy() {
			return new GroupByBuilder(this, null);
		}



		@Override
		public SelectQuery build() {
			log.debug("columns: " + Arrays.toString(columns));
			log.debug("from: " + from);
			log.debug("where: " + where);
			log.debug("group: " + groupBy);
			return new SelectQuery(columns, from, Optional.ofNullable(where), Optional.ofNullable(groupBy), Optional.ofNullable(having));
		}

		@Override
		public void setFrom(FromClause from) {
			this.from = from;
		}

		@Override
		public void setWhere(Predicate clause) {
			this.where = clause;
		}

		@Override
		public void setGroupBy(GroupByClause columns) {
			this.groupBy = columns;
		}

	}

}
