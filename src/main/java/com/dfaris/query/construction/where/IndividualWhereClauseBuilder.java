package com.dfaris.query.construction.where;

public class IndividualWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractWhereBuilder<Parent,
            IndividualWhereClauseBuilder<Parent>,
            MultiWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>>,
            Void>
        implements WhereParent{

    private WhereClause clause;

    public IndividualWhereClauseBuilder(Parent parent) {
        super(parent);
        this.refe = this;
    }

    public MultiWhereClauseBuilder<Parent> and() {
        return new MultiWhereClauseBuilder<>(this, "and");
    }

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
    public ParenthesizedWhereClauseBuilder<MultiWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(new MultiWhereClauseBuilder<>(this, null), this, null, null);
    }

    @Override
    public Parent endParenthesizedGroup() {
        return build();
    }

    @Override
    public Void endParenthesizedGroupAnd() {
        return null;
    }

    @Override
    public Void endParenthesizedGroupOr() {
        return null;
    }

    public Parent build() {
        parent.setWhere(buildClause());
        return parent;
    }

    IndividualWhereClause buildClause() {
        if(canBuild()) return new IndividualWhereClause(column, operator, constants);
        else return null;
    }

    @Override
    public void setWhere(WhereClause clause) {

    }
}
