package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.QueryBuilder;
import com.dfaris.query.construction.structure.Predicate;

public interface WhereParent<Ret extends Query> extends QueryBuilder<Ret> {

	void setWhere(Predicate clause);

}
