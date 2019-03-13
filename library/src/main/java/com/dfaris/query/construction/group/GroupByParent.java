package com.dfaris.query.construction.group;

import java.util.List;

public interface GroupByParent {

    void setGroupBy(List<String> columns);

    void addGroup(String group);

}
