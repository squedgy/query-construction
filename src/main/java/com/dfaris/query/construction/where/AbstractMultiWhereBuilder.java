package com.dfaris.query.construction.where;

public abstract class AbstractMultiWhereBuilder<Parent, This, AndOrReturn, StartParenReturn, EndParenReturn>
    extends AbstractWhereBuilder<Parent, This, AndOrReturn> implements WhereAppender{

    protected String andOr;
    protected WhereClause a;
    protected ParenGroup parenGroup;

    AbstractMultiWhereBuilder(Parent parent, WhereClause a, String andOr) {
        super(parent);
        this.a = a;
        this.andOr = andOr;
    }

    public abstract StartParenReturn startParenthesizedGroup();

    public abstract EndParenReturn endParenthesizedGroupAnd();
    public abstract EndParenReturn endParenthesizedGroupOr();

    public abstract Parent endParenthesizedGroup();

    protected WhereClause buildClause() {
        WhereClause ret;
        if(a != null) {
            if(andOr != null) {
                ret = new CompoundWhereClause(a, andOr, super.buildClause());
            } else if(a instanceof ParenGroup) {
                ParenGroup group = (ParenGroup) a;
                ret = new CompoundWhereClause(new ParenGroup(group.getClause(), null), group.getFollowedBy(),super.buildClause());
            } else {
                throw new IllegalStateException("AndOr is not set, and a is not a ParenGroup... wut");
            }
        } else {
            ret = super.buildClause();
        }
        this.andOr = null;
        return ret;
    }

}
