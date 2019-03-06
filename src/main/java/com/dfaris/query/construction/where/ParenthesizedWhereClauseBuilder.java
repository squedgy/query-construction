package com.dfaris.query.construction.where;

public class ParenthesizedWhereClauseBuilder<Parent extends WhereAppender>
        extends AbstractWhereBuilder<Parent,
            ParenthesizedWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>>,
            Parent> implements WhereAppender{

    private WhereClause a;
    private String andOr;
    private ParenGroup currentGroup;

    ParenthesizedWhereClauseBuilder(Parent parent){
        super(parent);
        this.refe = this;
    }


    @Override
    public ParenthesizedWhereClauseBuilder<Parent> and() {
        this.a = buildNormal();
        this.andOr = "and";
        return this;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> or() {
        this.a = buildNormal();
        this.andOr = "or";
        return this;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(this);
    }

    @Override
    public Parent endParenthesizedGroup() {
        WhereClause finalClause = buildNormal();
        if(this.currentGroup != null ) {
            if(this.currentGroup.getFollowedBy() != null) finalClause = new CompoundWhereClause(new ParenGroup(currentGroup.getClause(), null), currentGroup.getFollowedBy(), finalClause);
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

    private void setParentWhere(String andOr) {
        ParenGroup clause = new ParenGroup(buildNormal(), andOr);
        if(currentGroup != null){
            clause = new ParenGroup(new CompoundWhereClause(currentGroup.getClause(), currentGroup.getFollowedBy(), clause.getClause()), andOr);
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

    private WhereClause buildNormal() {
        WhereClause ret = buildClause(a, andOr);
        andOr = null;
        return ret;
    }

}
