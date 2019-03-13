package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;
import com.dfaris.query.construction.structure.Predicate;

public abstract class AbstractMultiWhereBuilder<QueryType extends Query, Parent extends WhereParent<QueryType>, This, AndOrReturn, StartParenReturn>
		extends AbstractWhereBuilder<QueryType, Parent, This, AndOrReturn, StartParenReturn> {

	protected String andOr;
	protected Predicate a;

	AbstractMultiWhereBuilder(Parent parent, Predicate a, String andOr) {
		super(parent);
		this.a = a;
		this.andOr = andOr;
	}

	@Override
	protected boolean canBuildClause() {
		return super.canBuildClause() && andOr != null && a != null;
	}

	protected Predicate buildClause() {
		Predicate ret;
		if (!canBuildClause()) {
			if (super.canBuildClause()) ret = super.buildClause();
			else if(a != null) ret = a;
			else throw new IllegalStateException("Cannot build clause and group either doesn't exist or must be followed by another clause");
		} else {
			ret = new CompoundWhereClause(a, andOr, super.buildClause());

		}
		this.andOr = null;
		this.a = null;
		return ret;
	}

}
