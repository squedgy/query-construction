package com.dfaris.query.construction.where;

public class MultiWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractWhereBuilder<Parent, MultiWhereClauseBuilder<Parent>, MultiWhereClauseBuilder<Parent>, ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>>> {
    private String andOr;
    private WhereClause a;

    MultiWhereClauseBuilder(IndividualWhereClauseBuilder<Parent> builder, String andOr){
        this(builder, builder.buildClause(), andOr);
    }

    MultiWhereClauseBuilder(AbstractWhereBuilder<Parent, ?, ?, ?> builder, WhereClause clause, String andOr){
        super(builder.parent);
        this.a = clause;
        this.andOr = andOr;
        this.refe = this;
    }

    public MultiWhereClauseBuilder<Parent>and(){
        this.a = buildCompound();
        this.andOr = "and";
        return this.refe;
    }

    public MultiWhereClauseBuilder<Parent>or(){
        this.a = buildCompound();
        this.andOr = "or";
        return this.refe;
    }

    private WhereClause buildIndividual() {
        return new IndividualWhereClause(this.column, this.operator, this.constants);
    }

    private WhereClause buildCompound() {
        return new CompoundWhereClause(a, andOr, buildIndividual());
    }

    public ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(this, this, a);
    }

    public Parent endParenthesizedGroup() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

    public ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>> endParenthesizedGroupAnd() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

    public ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>> endParenthesizedGroupOr() {
        throw new IllegalStateException("You did not create a parenthesized group!");
    }

    public Parent build(){
        if(this.andOr != null){
            WhereClause finalWhere = buildCompound();
            parent.setWhere(finalWhere);
        } else {
            parent.setWhere(a);
        }
        return parent;
    }

    public static WhereClause compoundFrom(WhereClause a, String andOr, WhereClause b){
        return new CompoundWhereClause(a, andOr, b);
    }

    public static WhereClause parenthisizedCompoundFrom(WhereClause a, String andOr, WhereClause b){
        return new ParenGroup(new CompoundWhereClause(a, andOr, b), null);
    }

}
