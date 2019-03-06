package com.dfaris.query.construction.where;

import java.util.List;
import java.util.Stack;

public class ParenthesizedWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractWhereBuilder<Parent,
            ParenthesizedWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>>,
            ParenthesizedWhereClauseBuilder<Parent>> implements WhereParent{

    private final Stack<ParenGroup> groups;
    private WhereClause a;
    private ParenGroup currentGroup;

    ParenthesizedWhereClauseBuilder(Parent parent, String column, String operator, List<String> constants, WhereClause a){
        super(parent);
        this.refe = this;
        this.column = column;
        this.operator = operator;
        this.constants = constants;
        this.groups = new Stack<>();
        this.a = a;
    }

    ParenthesizedWhereClauseBuilder(Parent parent, AbstractWhereBuilder<?, ?, ?, ?, ?> builder, WhereClause a){
        this(parent, builder.column, builder.operator, builder.constants, a);
    }

    ParenthesizedWhereClauseBuilder(Parent parent, WhereClause currentClause) {
        this(parent, null, null, null, currentClause);
    }


    @Override
    public ParenthesizedWhereClauseBuilder<Parent> and() {
        return null;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> or() {
        return null;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(this,);
    }

    @Override
    public Parent endParenthesizedGroup() {
        return null;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> endParenthesizedGroupAnd() {
        return null;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> endParenthesizedGroupOr() {
        return null;
    }

    @Override
    public Parent build() {
        if(parent instanceof WhereParent) {
            ((WhereParent) parent).setWhere(currentGroup);
        }
        return null;
    }

    @Override
    public void setWhere(WhereClause clause) {
        if(clause instanceof ParenGroup) this.currentGroup = (ParenGroup) clause;
        else throw new IllegalArgumentException("WhereClause must be a ParenGroup for ParenthesizedWhereClauseBuilder!");
    }
}
