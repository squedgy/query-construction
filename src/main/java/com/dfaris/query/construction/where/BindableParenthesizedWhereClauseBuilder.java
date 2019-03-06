package com.dfaris.query.construction.where;

public class BindableParenthesizedWhereClauseBuilder<Parent extends WhereAppender>
        extends AbstractBindableWhereClauseBuilder<Parent,
        BindableParenthesizedWhereClauseBuilder<Parent>,
        BindableParenthesizedWhereClauseBuilder<Parent>,
        BindableParenthesizedWhereClauseBuilder<BindableParenthesizedWhereClauseBuilder<Parent>>,
        Parent> implements WhereAppender {

    private String andOr;
    private WhereClause a;
    private ParenGroup currentGroup;

    BindableParenthesizedWhereClauseBuilder(Parent parent){
        super(parent);
    }

    @Override
    public BindableParenthesizedWhereClauseBuilder<Parent> and() {
        a = buildNormal();
        this.andOr = "and";
        return null;
    }

    @Override
    public BindableParenthesizedWhereClauseBuilder<Parent> or() {
        a = buildNormal();
        this.andOr = "or";
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

    @Override
    public void addWhere(WhereClause clause) {

    }

    private WhereClause buildNormal() {
        WhereClause ret = buildClause(a, andOr);
        andOr = null;
        return ret;
    }


}
