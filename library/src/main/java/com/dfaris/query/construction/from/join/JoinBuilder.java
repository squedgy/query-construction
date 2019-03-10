package com.dfaris.query.construction.from.join;

public abstract class JoinBuilder<BuilderType, ParentType> {

	protected JoinClause.Type type;

	protected JoinBuilder(JoinClause.Type type) {
		this.type = type;
	}

	public abstract BuilderType table(String name);

	public abstract BuilderType table(String name, String alias);

	public abstract BuilderType alias(String alias);

	public abstract ParentType on(String column, String otherTableAlias, String onColumn);

	public abstract BuilderType reset(JoinClause.Type type);

}
