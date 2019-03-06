package com.dfaris.query.construction.where;

public class IndividualWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractWhereBuilder<Parent, IndividualWhereClauseBuilder<Parent>, MultiWhereClauseBuilder<Parent>, ParenthesizedWhereClauseBuilder<Parent>>{
    
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

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(this);
    }

    @Override
    public Parent endParenthesizedGroup() {
        return build();
    }

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> endParenthesizedGroupAnd() {
        return new ParenthesizedWhereClauseBuilder<>(this);
    }

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> endParenthesizedGroupOr() {
        return null;
    }

    public Parent build() {
        parent.setWhere(buildClause());
        return parent;
    }

    public IndividualWhereClause buildClause() {
        return new IndividualWhereClause(column, operator, constants);
    }

}
