package com.dfaris.query.construction.where;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractBindableMultiWhereClauseBuilder<Parent, This, AndOrReturn, StartParenReturn, EndParenReturn>
    extends AbstractMultiWhereBuilder<Parent, This, AndOrReturn, StartParenReturn, EndParenReturn>{

    AbstractBindableMultiWhereClauseBuilder(Parent parent, WhereClause a, String andOr) {
        super(parent, a, andOr);
    }

    private boolean allString(Object[] values) {
        for(Object o : values) if(!(o instanceof String)) return false;
        return true;
    }

    private List<String> questionMarks(int marks) {
        List<String> ret = new ArrayList<>(marks);
        for (int i = 0; i < marks; i++) {
            ret.add("?");
        }
        return ret;
    }

    @Override
    public This values(Object... constants) {
        if(allString(constants)){
            this.constants = Arrays.stream(constants)
                    .map(o -> ":" + o)
                    .collect(Collectors.toList());
        } else {
            this.constants = questionMarks(constants.length);
        }
        return refe;
    }

    @Override
    public This values(Collection<?> constants) {
        return values(constants.toArray());
    }

    @Override
    public This value(Object constant) {
        this.constants = Collections.singletonList("?");
        return refe;
    }

}
