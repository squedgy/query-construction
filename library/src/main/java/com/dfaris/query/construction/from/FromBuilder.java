package com.dfaris.query.construction.from;

import java.util.LinkedList;
import java.util.List;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.from.join.FromJoinBuilder;
import com.dfaris.query.construction.from.join.JoinClause;
import com.dfaris.query.construction.group.GroupByBuilder;
import com.dfaris.query.construction.select.SelectQuery;
import com.dfaris.query.construction.select.SelectQuery.SelectQueryBuilder;
import org.slf4j.Logger;

import static com.dfaris.query.construction.from.join.JoinClause.Type.CROSS;
import static com.dfaris.query.construction.from.join.JoinClause.Type.FULL;
import static com.dfaris.query.construction.from.join.JoinClause.Type.INNER;
import static com.dfaris.query.construction.from.join.JoinClause.Type.LEFT;
import static com.dfaris.query.construction.from.join.JoinClause.Type.RIGHT;
import static org.slf4j.LoggerFactory.getLogger;

public class FromBuilder extends SelectQueryBuilder {

	private static Logger log = getLogger(FromBuilder.class);

	private String table, alias;
	private final List<JoinClause> joins;
	
	public FromBuilder(SelectQueryBuilder parent, String table, String alias, List<JoinClause> clause) {
		super(parent);
		this.joins=clause;
		this.table=table;
		this.alias=alias;
		setFrom(new FromClause(table, alias, clause));
	}
	
	public FromBuilder(FromBuilder builder) {
		this(builder, builder.table, builder.alias, builder.joins);
	}

	public FromBuilder(SelectQueryBuilder parent, String table, String alias) {
		this(parent, table, alias, new LinkedList<>());
	}

	public FromBuilder(SelectQueryBuilder parent, String table) {
		this(parent, table, table);
	}

	public FromBuilder(SelectQueryBuilder parent) {
		this(parent, null);
	}

	public FromBuilder table(String table) {
		this.table = table;
		if (this.alias == null) {
			this.alias = table;
		}
		return this;
	}

	public FromBuilder table(String table, String alias) {
		this.table = table;
		this.alias = alias;
		return this;
	}

	public FromBuilder alias(String alias) {
		this.alias = alias;
		return this;
	}

	public FromJoinBuilder join(String table) {
		return innerJoin(table);
	}

	public FromJoinBuilder join(String table, String alias) {
		return innerJoin(table, alias);
	}

	public FromJoinBuilder innerJoin(String table) {
		return new FromJoinBuilder(this, INNER, table);
	}

	public FromJoinBuilder innerJoin(String table, String alias) {
		return new FromJoinBuilder(this, INNER, table, alias);
	}

	public FromJoinBuilder leftJoin(String table) {
		return new FromJoinBuilder(this, LEFT, table);
	}

	public FromJoinBuilder leftJoin(String table, String alias) {
		return new FromJoinBuilder(this, LEFT, table, alias);
	}

	public FromJoinBuilder rightJoin(String table) {
		return new FromJoinBuilder(this, RIGHT, table);
	}

	public FromJoinBuilder rightJoin(String table, String alias) {
		return new FromJoinBuilder(this, RIGHT, table, alias);
	}

	public FromJoinBuilder fullJoin(String table) {
		return new FromJoinBuilder(this, FULL, table);
	}

	public FromJoinBuilder fullJoin(String table, String alias) {
		return new FromJoinBuilder(this, FULL, table, alias);
	}

	public FromBuilder crossJoin(String table) {
		return new FromJoinBuilder(this, CROSS, table).on(null, null, null);
	}

	public FromBuilder crossJoin(String table, String alias) {
		return new FromJoinBuilder(this, CROSS, table).alias(alias).on(null, null, null);
	}

	public void addJoin(JoinClause join) {
		this.joins.add(join);
	}

	public SelectQuery build() {
		setFrom(new FromClause(table, alias, joins));
		return super.build();
	}

}
