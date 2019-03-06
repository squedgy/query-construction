package com.dfaris.query.construction.where;

public class BindableIndividualWhereClauseBuilder<Parent>
        extends AbstractBindableWhereClauseBuilder<Parent,
            BindableIndividualWhereClauseBuilder<Parent>,
            BindableMultiWhereClauseBuilder<Parent>,
            BindableParenthesizedWhereClauseBuilder<BindableMultiWhereClauseBuilder<Parent>>,
            Void> {

    BindableIndividualWhereClauseBuilder(Parent parent) {
        super(parent);
        refe = this;
    }

    public WhereClause buildClause() {
        return new IndividualWhereClause(column, operator, constants);
    }

    @Override
    public BindableMultiWhereClauseBuilder<Parent> and() {
        return null;
    }

    @Override
    public BindableMultiWhereClauseBuilder<Parent> or() {
        return null;
    }

    @Override
    public BindableParenthesizedWhereClauseBuilder<BindableMultiWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return null;
    }

    @Override
    public Parent endParenthesizedGroup() {
        return null;
    }

    @Override
    public Void endParenthesizedGroupAnd() {
        return null;
    }

    @Override
    public Void endParenthesizedGroupOr() {
        return null;
    }

    @Override
    public Parent build() {
        return null;
    }
}
