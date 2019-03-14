package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.QueryBuilder;
import com.dfaris.query.construction.structure.predicate.Predicate;
import com.dfaris.query.construction.structure.predicate.PredicateParent;

public interface WhereParent<Ret extends Query> extends PredicateParent<Ret, Predicate> { }
