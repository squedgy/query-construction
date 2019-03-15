package com.dfaris.query.construction.predicate;

import com.dfaris.query.construction.Clause;

public abstract class Predicate implements Clause {

    private String left, operator, right;

    public Predicate(String left, String operator, String right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Predicate(Predicate p) {
        this(p.left, p.operator, p.right);
    }

    @Override
    public String toString() {
        String format = "%s %s %s";
        if(operator.equals("in")) format = "%s %s (%s)";
        return String.format(format, left, operator, right);
    }
}
