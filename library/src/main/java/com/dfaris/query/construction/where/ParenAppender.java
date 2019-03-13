package com.dfaris.query.construction.where;

import com.dfaris.query.construction.structure.ParenedPredicate;

public interface ParenAppender {

	void addPredicate(ParenedPredicate clause);

}
