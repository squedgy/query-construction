package com.dfaris.query.construction.where;

public class BindableMultiWhereClauseBuilder<Parent>
        extends AbstractBindableWhereClauseBuilder<Parent,
            BindableMultiWhereClauseBuilder<Parent>,
            BindableMultiWhereClauseBuilder<Parent>,
        BindableParenthesizedWhereClauseBuilder<BindableMultiWhereClauseBuilder<Parent>>,
            Void>{

    BindableMultiWhereClauseBuilder(Parent parent) {
        super(parent);
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
