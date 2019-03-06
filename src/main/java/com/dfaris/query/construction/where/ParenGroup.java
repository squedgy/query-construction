package com.dfaris.query.construction.where;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ParenGroup extends WhereClause{

    private String followedBy;
    private List<WhereClause> clauses;

    ParenGroup(List<WhereClause> clauses, String followedBy) {
        this.followedBy = followedBy;
        this.clauses = clauses;
    }

    ParenGroup(List<WhereClause> clauses) {
        this(clauses, null);
    }

    ParenGroup(WhereClause... clauses) {
        this(new LinkedList<>());
        this.clauses.addAll(Arrays.asList(clauses));
    }

    String getFollowedBy() { return followedBy; }
    List<WhereClause>  getClauses() { return clauses; }
    void addClause(WhereClause clause) { clauses.add(clause); }

    @Override
    public String toString() {
        return '(' + clauses.toString() + ')';
    }
}
