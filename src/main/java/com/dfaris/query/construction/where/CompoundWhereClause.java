package com.dfaris.query.construction.where;

public class CompoundWhereClause extends WhereClause {

    private final WhereClause a, b;
    private final String andOr;

    CompoundWhereClause(WhereClause a, String andOr, WhereClause b) {
        if(!andOr.matches("and|or")){
            throw new IllegalArgumentException("andOr must be either \"and\" OR \"or\" exactly. found: \"" + andOr + "\"");
        }
        this.andOr = andOr;
        this.a = a;
        this.b = b;
    }

    WhereClause getA(){ return a; }
    WhereClause getB(){ return b; }
    String getAndOr() { return andOr; }

    @Override
    public String toString() {
        return String.format("%s %s %s", a, andOr.toUpperCase(), b);
    }

}
