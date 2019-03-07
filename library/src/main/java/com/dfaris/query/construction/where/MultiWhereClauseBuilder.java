package com.dfaris.query.construction.where;

public class MultiWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractMultiWhereBuilder<Parent,
            MultiWhereClauseBuilder<Parent>,
            MultiWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>>> {
    private String andOr;
    private WhereClause a;
    private ParenGroup parenGroup;

    MultiWhereClauseBuilder(IndividualWhereClauseBuilder<Parent> builder, String andOr){
        this(builder, builder.buildClause(), andOr);
    }

    MultiWhereClauseBuilder(AbstractWhereBuilder<Parent, ?, ?, ?> builder, WhereClause a, String andOr){
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

    public Parent build(){
        WhereClause finalWhere;
        if(this.andOr != null){
            finalWhere = buildClause();
        } else {
            if(a == null){
                if(parenGroup != null){
                    finalWhere = parenGroup;
                } else {
                    finalWhere = buildClause();
                }
            } else {
                finalWhere = a;
            }
        }
        if(this.parenGroup != null){
            if(finalWhere != null) finalWhere = new CompoundWhereClause(parenGroup.getClause(), parenGroup.getFollowedBy(), finalWhere);
            else finalWhere = parenGroup;
        }
        parent.setWhere(finalWhere);
        return parent;
    }

}
