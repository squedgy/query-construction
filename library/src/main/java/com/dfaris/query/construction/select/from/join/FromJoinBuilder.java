package com.dfaris.query.construction.select.from.join;

import com.dfaris.query.construction.select.from.FromBuilder;
import com.dfaris.query.construction.select.SelectQuery;

import static com.dfaris.query.construction.select.from.join.JoinClause.Type.*;

public class FromJoinBuilder extends FromBuilder {

	protected String table,
			alias,
			tableColumn,
			otherTableAlias,
			onColumn;
	protected JoinClause.Type type;

	public FromJoinBuilder(FromBuilder builder, JoinClause.Type type, String table, String alias){
		super(builder);
		setFrom(null);
		this.type = type;
		this.table(table, alias);
	}

	public FromJoinBuilder(FromBuilder builder, JoinClause.Type type, String table) {
		this(builder, type, table, table);
	}

	public FromJoinBuilder(FromBuilder builder, JoinClause.Type type) {
		this(builder, type, null);
	}

	public FromJoinBuilder table(String name) {
		this.table = name;
		if (alias == null) {
			alias(name);
		}
		return this;
	}

	public FromJoinBuilder table(String name, String alias) {
		this.table = name;
		return alias(alias);
	}

	public FromJoinBuilder alias(String alias) {
		this.alias = alias;
		if(type == CROSS) {
			addJoin(buildClause());
			this.table = null;
			this.alias = null;
		}
		return this;
	}

	public FromBuilder on(String column, String otherTableAlias, String onColumn) {
		if(type == CROSS) {
			throw new IllegalStateException("Cross joins don't have on statements!");
		}
		this.tableColumn = column;
		this.otherTableAlias = otherTableAlias;
		this.onColumn = onColumn;
		addJoin(buildClause());
		return new FromBuilder(this);
	}

	public FromJoinBuilder reset(JoinClause.Type type) {
		this.alias = null;
		this.table = null;
		this.onColumn = null;
		this.tableColumn = null;
		this.otherTableAlias = null;
		this.type = type;
		return this;
	}

	public final JoinClause buildClause() {
		return type.build(table, alias, tableColumn, otherTableAlias, onColumn);
	}

	public SelectQuery build() {
		addJoin(buildClause());
		return super.build();
	}
}
