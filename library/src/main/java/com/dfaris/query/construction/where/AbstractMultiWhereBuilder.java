package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;

public abstract class AbstractMultiWhereBuilder<QueryType extends Query, Parent extends WhereParent<QueryType>, This, AndOrReturn, StartParenReturn>
		extends AbstractWhereBuilder<QueryType, Parent, This, AndOrReturn, StartParenReturn> {

	protected String andOr;
	protected WhereClause a;

	AbstractMultiWhereBuilder(Parent parent, WhereClause a, String andOr) {
		super(parent);
		this.a = a;
		this.andOr = andOr;
	}

	@Override
	protected boolean canBuildClause() {
		return super.canBuildClause() && andOr != null && (group != null || a != null);
	}

	protected WhereClause buildClause() {
		WhereClause ret;
		if (!canBuildClause()) {
			if (group != null && group.getFollowedBy() == null) ret = group;
			else if (super.canBuildClause()) ret = super.buildClause();
			else throw new IllegalStateException("Cannot build clause and group either doesn't exist or must be followed by another clause");
		} else {
			if (a == null && group != null) {
				ret = new CompoundWhereClause(group, super.buildClause());
				group = null;
			} else {
				ret = new CompoundWhereClause(a, andOr, super.buildClause());
				if (group != null) {
					ret = new CompoundWhereClause(group, ret);
				}
			}
		}
		this.andOr = null;
		this.group = null;
		return ret;
	}

}
