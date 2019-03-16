package com.dfaris.query.construction.insert.values;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.dfaris.query.construction.insert.InsertQuery.InsertQueryBuilder;

import static com.dfaris.query.construction.ValueConverters.getBindingValueOf;
import static com.dfaris.query.construction.ValueConverters.questionMarks;

public class ValuesBuilder {

    private List<Object> constants = new LinkedList<>();
    private boolean binding = false;
    private final String table;
    private final Object[] columns;

    public ValuesBuilder(String table, Object[] columns) {
        this.table = table;
        this.columns = columns;
    }

    public ValuesBuilder insert(Object... values) {
        if(binding) {
            for(Object o : values) constants.add(getBindingValueOf(o));
        } else {
            validate(values);
            constants.addAll(Arrays.asList(values));
        }
        return this;
    }

    public ValuesBuilder insert(int questionMarks) {
        if(questionMarks % columns.length != 0) throw bad("Values", "requested '?'s", questionMarks, columns.length);
        constants.addAll(questionMarks(questionMarks));
        return this;
    }

    public ValuesBuilder binding() {
        this.binding = true;
        return this;
    }

    public ValuesBuilder literal() {
        this.binding = true;
        return this;
    }

    public List<Object> build() {
        return constants;
    }

    private void validate(Object[] objects) {
        List<Object> objects1 = new LinkedList<>();
        List<List>   lists    = new LinkedList<>();
        int counter = 0;
        for(Object o : objects) {
            if(o instanceof List){
                List l = (List) o;
                if(counter != 0) throw bad("Sequential Values", "sequential values", counter, columns.length);
                if(l.size() != columns.length) throw bad("Lists", "elements", l.size(), columns.length);
                lists.add(l);
            } else {
                counter++;
                objects1.add(o);
            }
            if(counter == columns.length) counter = 0;
        }
    }

    private static IllegalArgumentException bad(String lOrV, String valueString, int values, int columnsLength) {
        return new IllegalArgumentException(
            String.format(
                    "%s received must be evenly divisible by the amount of columns requested. Received %d %s with %d columns in a group",
                    lOrV,
                    values,
                    valueString,
                    columnsLength
            )
        );
    }

}
