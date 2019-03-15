package com.dfaris.query.construction.select;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.predicate.where.DefaultWhereClauseBuilder;
import com.dfaris.query.construction.predicate.where.WhereClause;
import com.dfaris.query.construction.predicate.where.WhereParent;
import com.dfaris.query.construction.select.from.FromBuilder;
import com.dfaris.query.construction.select.from.FromClause;
import com.dfaris.query.construction.select.from.FromParent;
import com.dfaris.query.construction.select.group.GroupByBuilder;
import com.dfaris.query.construction.select.group.GroupByClause;
import com.dfaris.query.construction.select.group.GroupByParent;
import com.dfaris.query.construction.select.having.DefaultHavingBuilder;
import com.dfaris.query.construction.select.having.HavingClause;
import com.dfaris.query.construction.select.having.HavingParent;
import com.dfaris.query.construction.select.order.OrderByBuilder;
import com.dfaris.query.construction.select.order.OrderByClause;
import com.dfaris.query.construction.predicate.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
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

	public static class SelectQueryBuilder implements FromParent, WhereParent<SelectQuery>, GroupByParent, HavingParent {

		protected final String[] columns;
		protected FromClause from;
		protected Predicate where;
		protected GroupByClause groupBy;
		protected Predicate having;
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

		public FromBuilder from() {
			return new FromBuilder(this);
		}

		public FromBuilder from(String table) {
			return new FromBuilder(this, table);
		}

		public FromBuilder from(String table, String alias) {
			return new FromBuilder(this, table, alias);
		}

		public DefaultWhereClauseBuilder<SelectQuery, SelectQueryBuilder> where() {
			return WhereClause.where(this);
		}

		public GroupByBuilder groupBy(String... columns) {
			return new GroupByBuilder(this, columns);
		}

		public DefaultHavingBuilder having() { return HavingClause.having(this); }

		public OrderByBuilder orderBy(String... columns) {
			return new OrderByBuilder(this, columns);
		}

		@Override
		public SelectQuery build() {
			log.debug("columns: " + Arrays.toString(columns));
			log.debug("from: " + from);
			log.debug("where: " + where);
			log.debug("group: " + groupBy);
			log.debug("having: " + having);
			log.debug("order: " + orderBy);
			return new SelectQuery(columns, from, Optional.ofNullable(where), Optional.ofNullable(groupBy), Optional.ofNullable(having), Optional.ofNullable(orderBy));
		}

		@Override
		public void setFrom(FromClause from) {
			this.from = from;
		}

		@Override
		public void setPredicate(Predicate clause) {
			if(clause instanceof WhereClause) this.where = clause;
			if(clause instanceof HavingClause) this.having = clause;
		}

		@Override
		public void setGroupBy(GroupByClause columns) {
			this.groupBy = columns;
		}

		public void setOrderBy(OrderByClause orderBy) {
			this.orderBy = orderBy;
		}

	}

}
