package com.dfaris.query.construction.where;

import java.util.List;

public class ParenthesizedWhereClauseBuilder<Parent extends WhereAppender>
        extends AbstractWhereBuilder<Parent,
            ParenthesizedWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>>,
            Parent> implements WhereAppender{

    private WhereClause a;
    private String andOr;
    private ParenGroup currentGroup;

    ParenthesizedWhereClauseBuilder(Parent parent, String column, String operator, List<String> constants, WhereClause a, String andOr){
        super(parent);
        this.refe = this;
        this.column = column;
        this.operator = operator;
        this.constants = constants;
        this.andOr = andOr;
        this.a = a;
    }

    ParenthesizedWhereClauseBuilder(Parent parent, AbstractWhereBuilder<?, ?, ?, ?, ?> builder, WhereClause a, String andOr){
        this(parent, builder.column, builder.operator, builder.constants, a, andOr);
    }

    ParenthesizedWhereClauseBuilder(Parent parent, WhereClause a, String andOr) {
        this(parent, null, null, null, a, andOr);
    }


    @Override
    public ParenthesizedWhereClauseBuilder<Parent> and() {
        this.a = buildCompound();
        this.andOr = "and";
        return this;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> or() {
        this.a = buildCompound();
        this.andOr = "or";
        return null;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(this, null, null);
    }

    @Override
    public Parent endParenthesizedGroup() {
        WhereClause finalClause = buildCompound();
        if(this.currentGroup != null ) {
            if(this.currentGroup.getFollowedBy() != null) finalClause = new CompoundWhereClause(currentGroup.getClause(), currentGroup.getFollowedBy(), finalClause);
            else if (finalClause == null) finalClause = currentGroup;
        }
        this.currentGroup = new ParenGroup(finalClause, null);
        return build();
    }

    @Override
    public Parent endParenthesizedGroupAnd() {
        setParentWhere("and");
        return parent;
    }

    private WhereClause buildIndividual() {
        WhereClause ret = new IndividualWhereClause(column, operator, constants);
        this.constants = null;
        this.operator = null;
        this.column = null;
        return ret;
    }

    private WhereClause buildCompound() {
        WhereClause ret;
        if(a == null){
            ret = buildIndividual();
        } else if(a instanceof ParenGroup) {
            ParenGroup group = (ParenGroup) a;
            ret = new CompoundWhereClause(group.getClause(), group.getFollowedBy(), buildIndividual());
        } else {
            ret = new CompoundWhereClause(a, andOr, buildIndividual());
        }
        this.andOr = null;
        return ret;
    }

    private void setParentWhere(String andOr) {
        WhereClause clause = new ParenGroup(a == null ? buildIndividual(): buildCompound(), andOr);
        if(currentGroup != null){
            clause = new ParenGroup(new CompoundWhereClause(currentGroup.getClause(), currentGroup.getFollowedBy(), clause), andOr);
        }
        parent.addWhere(clause);
    }

    @Override
    public Parent endParenthesizedGroupOr() {
        setParentWhere("or");
        return parent;
    }

    @Override
    public Parent build() {
        parent.addWhere(currentGroup);
        return parent;
    }
    @Override
    public void addWhere(WhereClause clause) {
        if(clause instanceof ParenGroup) this.currentGroup = (ParenGroup) clause;
        else throw new IllegalArgumentException("WhereClause must be a ParenGroup for ParenthesizedWhereClauseBuilder!");
    }
}
