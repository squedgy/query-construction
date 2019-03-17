package com.dfaris.query.construction;

public interface QueryBuilder<Ret extends Query> {

	Ret build();

}
