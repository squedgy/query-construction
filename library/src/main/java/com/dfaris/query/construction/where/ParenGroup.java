package com.dfaris.query.construction.where;

public class ParenGroup extends WhereClause {

	private String followedBy;
	private WhereClause clause;

	ParenGroup(WhereClause clause, String followedBy) {
		this.followedBy = followedBy;
		this.clause = clause;
	}

	ParenGroup(ParenGroup a){
		this(a.clause, null);
	}

	ParenGroup(ParenGroup a, ParenGroup b) {
		clause = new CompoundWhereClause(new ParenGroup(a), a.followedBy, new ParenGroup(b));
		followedBy = b.followedBy;
	}

	String getFollowedBy() {
		return followedBy;
	}

	WhereClause getClause() {
		return clause;
	}

	@Override
	public String toString() {
		return '(' + clause.toString() + ")" + (followedBy != null ? " " + followedBy : "");
	}
}
