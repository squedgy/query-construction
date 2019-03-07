package com.dfaris.query.construction.where;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ParenGroup extends WhereClause{

    private String followedBy;
    private WhereClause clause;

    ParenGroup(WhereClause clause, String followedBy) {
        this.followedBy = followedBy;
        this.clause = clause;
    }

    ParenGroup(ParenGroup a, ParenGroup b){
        clause = new CompoundWhereClause(new ParenGroup(a.clause, null), a.followedBy, b);
    }

    String getFollowedBy() { return followedBy; }
    WhereClause  getClause() { return clause; }

    @Override
    public String toString() {
        return '(' + clause.toString() + ")" + (followedBy != null ? " " + followedBy : "");
    }
}
