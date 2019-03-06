package com.dfaris.query.construction.where;

public class MultiWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractMultiWhereBuilder<Parent,
            MultiWhereClauseBuilder<Parent>,
            MultiWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>>,
            Void>
        implements WhereAppender{
    private String andOr;
    private WhereClause a;
    private ParenGroup parenGroup;

    MultiWhereClauseBuilder(IndividualWhereClauseBuilder<Parent> builder, String andOr){
        this(builder, builder.buildClause(), andOr);
    }

    MultiWhereClauseBuilder(AbstractWhereBuilder<Parent, ?, ?> builder, WhereClause a, String andOr){
        super(builder.parent, a, andOr);
        this.a = a;
        this.andOr = andOr;
        this.refe = this;
    }

    public MultiWhereClauseBuilder<Parent>and(){
        this.a = buildClause();
        this.andOr = "and";
        return this.refe;
    }

    public MultiWhereClauseBuilder<Parent>or(){
        this.a = buildClause();
        this.andOr = "or";
        return this.refe;
    }

    public ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(this);
    }

    public Parent endParenthesizedGroup() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

    public Void endParenthesizedGroupAnd() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

    public Void endParenthesizedGroupOr() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

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
        if(clause instanceof  ParenGroup){
            if(a == null) {
                a = clause;
            } else if(a instanceof ParenGroup) {
                ParenGroup b = (ParenGroup) a;
                a = new CompoundWhereClause(new ParenGroup(b.getClause(), null), b.getFollowedBy(), clause);
            } else if(a instanceof  CompoundWhereClause) {
                CompoundWhereClause b = (CompoundWhereClause) a;
                if(b.getB() instanceof ParenGroup){
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
