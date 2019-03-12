package com.dfaris.query.construction.where;

import com.dfaris.query.construction.Query;

public class ParenthesizedWhereClauseBuilder<QueryType extends Query,
		Parent extends WhereParent<QueryType>>
		extends AbstractParenthesizedWhereClauseBuilder<Parent,
		ParenthesizedWhereClauseBuilder<QueryType, Parent>,
		ParenthesizedWhereClauseBuilder<QueryType, Parent>,
		ParenthesizedWhereClauseBuilder<QueryType, ParenthesizedWhereClauseBuilder<QueryType, Parent>>,
		Parent> implements WhereParent<QueryType> {

	ParenthesizedWhereClauseBuilder(Parent parent) {
		super(parent, null, null);
		this.refe = this;
		if(parent instanceof AbstractWhereBuilder) binding = ((AbstractWhereBuilder) parent).binding;
	}


	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, Parent> and() {
		this.a = buildClause();
		this.andOr = "and";
		return this;
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, Parent> or() {
		this.a = buildClause();
		this.andOr = "or";
		return this;
	}

	@Override
	public ParenthesizedWhereClauseBuilder<QueryType, ParenthesizedWhereClauseBuilder<QueryType, Parent>> startParenthesizedGroup() {
		return new ParenthesizedWhereClauseBuilder<>(this);
	}

	@Override
	public Query endParenthesizedGroup() {
		WhereClause finalClause = buildClause();
		if (this.group != null) {
			if (this.group.getFollowedBy() != null)
				finalClause = new CompoundWhereClause(new ParenGroup(group.getClause(), null), group.getFollowedBy(), finalClause);
			else if (finalClause == null) finalClause = group;
		}
		this.group = new ParenGroup(finalClause, null);
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
	public QueryType build() {
		parent.setWhere(group);
		return parent.build();
	}

	private void setParentWhere(String andOr) {
		ParenGroup clause = new ParenGroup(buildClause(), andOr);
		if (group != null) {
			clause = new ParenGroup(group, clause);
		}
		parent.setWhere(clause);
	}

	@Override
	public void setWhere(WhereClause clause) {
		if (clause instanceof ParenGroup) this.group = (ParenGroup) clause;
		else
			throw new IllegalArgumentException("WhereClause must be a ParenGroup for ParenthesizedWhereClauseBuilder!");
	}
}
