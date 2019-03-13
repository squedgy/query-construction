package com.dfaris.query.construction.from.join;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.from.FromBuilder;
import com.dfaris.query.construction.from.FromParent;
import com.dfaris.query.construction.select.SelectQuery;

public class FromJoinBuilder extends FromBuilder {

	protected String table,
			alias,
			tableColumn,
			otherTableAlias,
			onColumn;
	protected JoinClause.Type type;

	public FromJoinBuilder(FromBuilder builder, JoinClause.Type type, String table, String alias){
		super(builder);
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
			this.alias = name;
		}
		return this;
	}

	public FromJoinBuilder table(String name, String alias) {
		this.table = name;
		return alias(alias);
	}

	public FromJoinBuilder alias(String alias) {
		this.alias = alias;
		return this;
	}

	public FromBuilder on(String column, String otherTableAlias, String onColumn) {
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

	protected final JoinClause buildClause() {
		if (type == JoinClause.Type.CROSS) {
			return new CrossJoin(table);
		} else if (type == JoinClause.Type.INNER) {
			return new InnerJoin(table, alias, tableColumn, otherTableAlias, onColumn);
		} else if (type == JoinClause.Type.LEFT) {
			return new LeftJoin(table, alias, tableColumn, otherTableAlias, onColumn);
		} else if (type == JoinClause.Type.RIGHT) {
			return new RightJoin(table, alias, tableColumn, otherTableAlias, onColumn);
		} else if (type == JoinClause.Type.FULL) {
			return new FullJoin(table, alias, tableColumn, otherTableAlias, onColumn);
		}

		return null;
	}

	public SelectQuery build() {
		addJoin(buildClause());
		return super.build();
	}
}
