package com.dfaris.query.construction.where;

public class ParenthesizedWhereClauseBuilder<Parent extends WhereParent>
        extends AbstractParenthesizedWhereClauseBuilder<Parent,
            ParenthesizedWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<Parent>,
            ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>>,
            Parent> {

    private WhereClause a;
    private String andOr;
    private ParenGroup currentGroup;

    ParenthesizedWhereClauseBuilder(Parent parent){
        super(parent, null ,null);
        this.refe = this;
    }


    @Override
    public ParenthesizedWhereClauseBuilder<Parent> and() {
        this.a = buildClause();
        this.andOr = "and";
        return this;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<Parent> or() {
        this.a = buildClause();
        this.andOr = "or";
        return this;
    }

    @Override
    public ParenthesizedWhereClauseBuilder<ParenthesizedWhereClauseBuilder<Parent>> startParenthesizedGroup() {
        return new ParenthesizedWhereClauseBuilder<>(this);
    }

    @Override
    public Parent endParenthesizedGroup() {
        WhereClause finalClause = buildClause();
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

    @Override
    public Parent endParenthesizedGroupOr() {
        setParentWhere("or");
        return parent;
    }

    @Override
    public Parent build() {
        parent.setWhere(currentGroup);
        return parent;
    }

    private void setParentWhere(String andOr) {
        ParenGroup clause = new ParenGroup(buildClause(), andOr);
        if(currentGroup != null){
            clause = new ParenGroup(currentGroup, clause);
        }
        parent.setWhere(clause);
    }

    @Override
    public void setWhere(WhereClause clause) {
        if(clause instanceof ParenGroup) this.currentGroup = (ParenGroup) clause;
        else throw new IllegalArgumentException("WhereClause must be a ParenGroup for ParenthesizedWhereClauseBuilder!");
    }
}
