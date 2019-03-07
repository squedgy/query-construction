package com.dfaris.query.construction.where;

public class BindableMultiWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractMultiWhereBuilder<Parent,
            BindableMultiWhereClauseBuilder<Parent>,
            BindableMultiWhereClauseBuilder<Parent>,
            BindableParenthesizedWhereClauseBuilder<BindableMultiWhereClauseBuilder<Parent>>,
            Void> implements WhereAppender{

    BindableMultiWhereClauseBuilder(AbstractBindableWhereClauseBuilder<Parent, ?, ?, ?, ?> builder, WhereClause a, String andOr){
        super(builder.parent, a, andOr);
    }

    BindableMultiWhereClauseBuilder(BindableIndividualWhereClauseBuilder<Parent> builder, String andOr) {
        this(builder, builder.buildClause(), andOr);
    }

    @Override
    public BindableMultiWhereClauseBuilder<Parent> and() {
        a = buildClause();
        this.andOr = "and";
        return refe;
    }

    @Override
    public BindableMultiWhereClauseBuilder<Parent> or() {
        a = buildClause();
        this.andOr = "or";
        return refe;
    }

    @Override
    public BindableParenthesizedWhereClauseBuilder<BindableMultiWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new BindableParenthesizedWhereClauseBuilder<>(this);
    }

    @Override
    public Parent endParenthesizedGroup() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

    @Override
    public Void endParenthesizedGroupAnd() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

    @Override
    public Void endParenthesizedGroupOr() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

    @Override
    public Parent build(){
        if(a == null) a = buildClause();
        WhereClause finalWhere;
        if(this.andOr != null){
            finalWhere = buildClause();
        } else {
            finalWhere = a;
        }
        if(this.parenGroup != null){
            if(finalWhere != null) finalWhere = new CompoundWhereClause(parenGroup.getClause(), parenGroup.getFollowedBy(), finalWhere);
            else finalWhere = parenGroup;
        }
        parent.setWhere(finalWhere);
        return parent;
    }

    @Override
    public void addWhere(WhereClause clause) {
        if (clause instanceof ParenGroup) {
            if (a == null) {
                a = clause;
            } else if (a instanceof ParenGroup) {
                ParenGroup b = (ParenGroup) a;
                a = new CompoundWhereClause(new ParenGroup(b.getClause(), null), b.getFollowedBy(), clause);
            } else if (a instanceof CompoundWhereClause) {
                CompoundWhereClause b = (CompoundWhereClause) a;
                if (b.getB() instanceof ParenGroup) {
                    ParenGroup bsB = (ParenGroup) b.getB();
                    WhereClause temp = new CompoundWhereClause(b.getA(), b.getAndOr(), bsB.getClause());
                    a = new CompoundWhereClause(temp, bsB.getFollowedBy(), clause);
                }
            }
        } else {
            throw new IllegalStateException("Cannot accept a non ParenGroup clause in MultiWhereClauseBuilder");
        }
    }
}
