package com.dfaris.query.construction.where;

import java.util.List;

public class IndividualWhereClause extends WhereClause {
    private String column;
    private String operator;
    private List<String> constants;

    IndividualWhereClause(String column, String operator, List<String> constants){
        this.column = column;
        this.operator = operator;
        this.constants = constants;
    }

    IndividualWhereClause(IndividualWhereClause clause) {
        this(clause.column, clause.operator, clause.constants);
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<String> getConstants() {
        return constants;
    }

    public void setConstants(List<String> constants) {
        this.constants = constants;
    }

    @Override
    public String toString(){
        String values;

        if(constants.size() > 1 || operator.equals("in")) {
            values = "(" + String.join(",", constants) + ") ";
        } else if (constants.size() == 1) {
            values = constants.get(0);
        } else {
            throw new RuntimeException("Constants didn't exist: " + constants);
        }
        String format = "%s %s %s ";
        return String.format(format, column, operator, values);
    }

}
