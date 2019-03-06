package com.dfaris.query.construction.where;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.dfaris.query.construction.ValueConverters;

public abstract class AbstractWhereBuilder<Parent, This, AndOrReturn, StartParenReturn, EndParenReturn> {

    protected final Parent parent;
    protected String column;
    protected String operator;
    protected List<String> constants;
    protected This refe;

    AbstractWhereBuilder(Parent parent) {
        this.parent = parent;
    }

    public Parent getParent() { return parent; }

    public This column(String column) {
        this.column = column;
        return refe;
    }

    public This withOperator(String operator) {
        this.operator = operator;
        return refe;
    }

    public This in(){
        this.operator = "in";
        return refe;
    }

    public This in(Object... values){
        this.operator = "in";
        this.values(values);
        return refe;
    }

    public This greaterThan(){
        this.operator = ">";
        return refe;
    }

    public This greaterThan(Object value){
        greaterThan();
        value(value);
        return refe;
    }

    public This lessThan(){
        this.operator = "<";
        return refe;
    }

    public This lessThan(Object value){
        lessThan();
        value(value);
        return refe;
    }

    public This greaterThanOrEqualTo(){
        this.operator = ">=";
        return refe;
    }

    public This greaterThanOrEqualTo(Object value){
        greaterThanOrEqualTo();
        value(value);
        return refe;
    }

    public This lessThanOrEqualTo(){
        this.operator = "<=";
        return refe;
    }

    public This lessThanOrEqualTo(Object value){
        lessThanOrEqualTo();
        value(value);
        return refe;
    }

    public This equalTo(){
        this.operator = "=";
        return refe;
    }

    public This equalTo(Object value){
        equalTo();
        value(value);
        return refe;
    }

    public This notEqualTo(){
        this.operator = "<>";
        return refe;
    }

    public This notEqualTo(Object value){
        notEqualTo();
        value(value);
        return refe;
    }

    public This notNull() {
        this.operator = "IS NOT";
        this.constants = Collections.singletonList("NULL");
        return refe;
    }

    public This isNull() {
        this.operator = "IS";
        this.constants = Collections.singletonList("NULL");
        return refe;
    }

    public This values(Object... constants) {
        values(Arrays.asList(constants));
        return refe;
    }

    public This values(Collection<?> constants) {
        this.constants = constants
                .stream()
                .map(ValueConverters::getSqlValueOf)
                .collect(Collectors.toList());
        return refe;
    }

    public This value(Object constant) {
        this.constants = Collections.singletonList(ValueConverters.getSqlValueOf(constant));
        return refe;
    }

    public This value(String constant) {
        this.constants = Collections.singletonList(constant);
        return refe;
    }

    public This reset() {
        this.constants = null;
        this.column = null;
        this.operator = null;
        return refe;
    }

    public abstract AndOrReturn and();
    public abstract AndOrReturn or();

    public abstract StartParenReturn startParenthesizedGroup();
    public abstract Parent endParenthesizedGroup();
    public abstract EndParenReturn endParenthesizedGroupAnd();
    public abstract EndParenReturn endParenthesizedGroupOr();

    public abstract Parent build();

}
