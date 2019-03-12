package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.QueryBuilder;

public interface WhereParent<Ret extends Query> extends QueryBuilder<Ret> {

	void setWhere(WhereClause clause);

}
