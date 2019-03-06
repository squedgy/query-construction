package com.dfaris.query.construction.where;

public class BindableParenthesizedWhereClauseBuilder<Parent>
        extends AbstractBindableWhereClauseBuilder<Parent,
        BindableParenthesizedWhereClauseBuilder<Parent>,
        BindableParenthesizedWhereClauseBuilder<Parent>,
        BindableParenthesizedWhereClauseBuilder<BindableParenthesizedWhereClauseBuilder<Parent>>,
        Parent> {

    BindableParenthesizedWhereClauseBuilder(Parent parent) {
        super(parent);
    }

    @Override
    public BindableParenthesizedWhereClauseBuilder<Parent> and() {
        return null;
    }

    @Override
    public BindableParenthesizedWhereClauseBuilder<Parent> or() {
        return null;
    }

    @Override
    public BindableParenthesizedWhereClauseBuilder<BindableParenthesizedWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return null;
    }

    @Override
    public Parent endParenthesizedGroup() {
        return null;
    }

    @Override
    public Parent endParenthesizedGroupAnd() {
        return null;
    }

    @Override
    public Parent endParenthesizedGroupOr() {
        return null;
    }

    @Override
    public Parent build() {
        return null;
    }
}
