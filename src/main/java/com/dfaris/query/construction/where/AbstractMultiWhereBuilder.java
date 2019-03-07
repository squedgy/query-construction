package com.dfaris.query.construction.where;

public abstract class AbstractMultiWhereBuilder<Parent, This, AndOrReturn, StartParenReturn>
    extends AbstractWhereBuilder<Parent, This, AndOrReturn, StartParenReturn> implements WhereParent {

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
        if(!canBuildClause()){
            if(group != null && group.getFollowedBy() == null) ret = group;
            else if(super.canBuildClause()) ret = super.buildClause();
            else throw new IllegalStateException("Cannot build clause and group either doesn't exist or must be followed by another clause");
        } else {
            if(a == null && group != null) {
                ret = new CompoundWhereClause(group, super.buildClause());
                group = null;
            } else {
                ret = new CompoundWhereClause(a, andOr, super.buildClause());
                if(group != null) {
                    ret = new CompoundWhereClause(group, ret);
                }
            }
        }
        this.andOr = null;
        this.group = null;
        return ret;
    }

}
