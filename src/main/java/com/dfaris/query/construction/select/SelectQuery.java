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

/**
 * A class representing a Select Query
 *
 * If you wish to create a select query see the method {@link com.dfaris.query.construction.select.SelectQuery#select(String...)}
 */
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

	/**
	 * Returns the query that this SelectQuery represents
	 * @return a query string
	 */
	@Override
	public String toString() {
		StringBuilder query = new StringBuilder("SELECT ");
		for (int i = 0; i < columns.length - 1; i++) {
			query.append(columns[i]).append(", ");
		}
		query.append(columns[columns.length - 1])
				.append(' ')
				.append(from.getClauseStarter())
				.append(from.toString())
				.append(' ');

		clauses.forEach(optional -> {
			optional.ifPresent(clause -> {
				query.append(clause.getClauseStarter())
						.append(clause.toString())
						.append(' ');
			});
		});

		return query.toString();
	}

	/**
	 * Creates a {@link com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder} while selecting the given columns
	 * @param columns all the columns you want to select (you can add as clauses within the column String yourself if you with them to be aliased"
	 * @return a new {@link com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder}
	 */
	public static SelectQueryBuilder select(String... columns) {
		return new SelectQueryBuilder(columns);
	}

	/**
	 * It builds a SelectQuery
	 *
	 * The way to create a builder is using the method {@link com.dfaris.query.construction.select.SelectQuery#select(String...)}
	 */
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

		/**
		 * Create a FromClause from the table and additional parameters can be supplied through the callback.
		 * Which returns a FromBuilder already set-up with your table.
		 * One call to any of the from methods is REQUIRED for the build to be successful.
		 * @param table the table you're grabbing fields from
		 * @param alias the alias for the table
		 * @param callback a function accepting a {@link com.dfaris.query.construction.select.from.FromBuilder} and returning a {@link com.dfaris.query.construction.select.from.FromClause}
		 * @return this
		 */
		public SelectQueryBuilder from(String table, String alias, Function<FromBuilder, FromClause> callback) {
			from = callback.apply(new FromBuilder(table, alias));
			return this;
		}

		/**
		 * Calls {@link com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder#from(String, String, Function)}
		 * with the table name as the alias
		 * @param table the original table you're pulling from
		 * @param callback a function accepting a {@link com.dfaris.query.construction.select.from.FromBuilder} and returning a {@link com.dfaris.query.construction.select.from.FromClause}
		 * @return this
		 */
		public SelectQueryBuilder from(String table, Function<FromBuilder, FromClause> callback) {
			return from(table, table, callback);
		}

		/**
		 * Calls {@link com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder#from(String, String, Function)}
		 * using table and alias as their respective params and building the from builder be default as the function.
		 * @param table the original table you're pulling from
		 * @param alias an alias for the table
		 * @return this
		 */
		public SelectQueryBuilder from(String table, String alias) {
			return from(table, alias, FromBuilder::build);
		}

		/**
		 * A mix between {@link com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder#from(String, String)}
		 * and {@link com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder#from(String, Function)}.
		 * @param table the original table you're pulling from
		 * @return this
		 */
		public SelectQueryBuilder from(String table) {
			return from(table,table);
		}

		/**
		 * Optional method to create a {@link com.dfaris.query.construction.predicate.where.WhereClause} for the {@link com.dfaris.query.construction.select.SelectQuery}
		 * @param callback a function accepting a {@link com.dfaris.query.construction.predicate.where.DefaultWhereClauseBuilder} and returning {@link com.dfaris.query.construction.predicate.where.WhereClause}
		 * @return this
		 */
		public SelectQueryBuilder where(Function<DefaultWhereClauseBuilder, WhereClause> callback) {
			where = callback.apply(WhereClause.where());
			return this;
		}

		/**
		 * a list of columns to group by
		 * @param columns columns to group by
		 * @return this
		 */
		public SelectQueryBuilder groupBy(String... columns) {
			groupBy = new GroupByClause(Arrays.asList(columns));
			return this;
		}

		/**
		 * Optional method similar to {@link com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder#where(Function)} just for the {@link com.dfaris.query.construction.select.having.HavingClause}
		 * @param callback a function accepting a {@link com.dfaris.query.construction.select.having.DefaultHavingBuilder} and returning {@link com.dfaris.query.construction.select.having.HavingClause}
		 * @return this
		 */
		public SelectQueryBuilder having(Function<DefaultHavingBuilder, HavingClause> callback) {
			having = callback.apply(HavingClause.having());
			return this;
		}

		/**
		 * A list of columns in the order you want them ordered by
		 * @param columns columns you're ordering by
		 * @return this
		 */
		public SelectQueryBuilder orderBy(String... columns) {
			orderBy = new OrderByClause(new LinkedList<>(Arrays.asList(columns)));
			return this;
		}

		/**
		 * Returns a {@link com.dfaris.query.construction.select.SelectQuery}.
		 * <br>
		 * If debug logging is enabled prints the different parts of the query BEFORE creating and returning it
		 * @return {@link com.dfaris.query.construction.select.SelectQuery}
		 */
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
