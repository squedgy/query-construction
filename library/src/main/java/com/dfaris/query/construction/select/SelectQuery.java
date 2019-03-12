package com.dfaris.query.construction.select;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.QueryBuilder;
import com.dfaris.query.construction.from.FromBuilder;
import com.dfaris.query.construction.from.FromClause;
import com.dfaris.query.construction.from.FromParent;
import com.dfaris.query.construction.group.GroupByBuilder;
import com.dfaris.query.construction.group.GroupByParent;
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
	private final Optional<List<String>> groupBy;
	private final Optional<WhereClause> where;
	private static final Logger log = LoggerFactory.getLogger(SelectQuery.class);

	SelectQuery(String[] columns, FromClause from, Optional<WhereClause> where, Optional<List<String>> groupBy) {
		this.columns = columns;
		this.groupBy = groupBy;
		this.where = where;
		this.from = from;
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

		where.ifPresent(w -> query.append(w.getClauseStarter())
				.append(w.toString())
				.append(' '));

		groupBy.ifPresent(o -> query.append("ORDER BY ")
				.append(String.join(",", o))
				.append(' ')
		);

		return query.toString();
	}

	public static SelectQueryBuilder select(String... columns) {
		return new SelectQueryBuilder(columns);
	}

	public static class SelectQueryBuilder implements FromParent, WhereParent<SelectQuery>, GroupByParent {

		protected final String[] columns;
		protected FromClause from;
		protected WhereClause where;
		protected List<String> groupBy;

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

		@Override
		public SelectQuery build() {
			log.debug("columns: " + Arrays.toString(columns));
			log.debug("from: " + from);
			log.debug("where: " + where);
			log.debug("group: " + groupBy);
			return new SelectQuery(columns, from, Optional.ofNullable(where), Optional.ofNullable(groupBy));
		}

		@Override
		public void setFrom(FromClause from) {
			this.from = from;
		}

		@Override
		public void setWhere(WhereClause clause) {
			this.where = clause;
		}

		@Override
		public void setGroupBy(List<String> columns) {
			this.groupBy = columns;
		}

		@Override
		public void addGroup(String group) {
			if(groupBy == null) groupBy = new LinkedList<>();
			groupBy.add(group);
		}
	}

}
