package com.dfaris.query.construction.where;

public class IndividualWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractWhereBuilder<Parent,
            IndividualWhereClauseBuilder<Parent>,
            MultiWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>>>
        implements ParenAppender {

    private WhereClause clause;
    private ParenGroup parenGroup;

    public IndividualWhereClauseBuilder(Parent parent) {
        super(parent);
        this.refe = this;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(new MultiWhereClauseBuilder<>(this, null, null));
    }

    @Override
    public MultiWhereClauseBuilder<Parent> and() {
        return new MultiWhereClauseBuilder<>(this, "and");
    }

    @Override
    public MultiWhereClauseBuilder<Parent> or() {
        return new MultiWhereClauseBuilder<>(this, "or");
    }

    public boolean canBuild() {
        return column != null &&
                operator != null &&
                constants != null &&
                constants.size() > 0;
    }

    @Override
    public Parent build() {
        WhereClause finalWhere = buildClause();
        if(parenGroup != null) finalWhere = new CompoundWhereClause(parenGroup, finalWhere);
        parent.setWhere(finalWhere);
        return parent;
    }

    @Override
    public void addWhere(ParenGroup clause) {
        if(this.parenGroup == null) parenGroup = clause;
        else this.parenGroup = new ParenGroup(parenGroup, clause);
    }

}
