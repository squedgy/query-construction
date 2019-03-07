package com.nm.mi.jdbi;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import fun.mike.record.alpha.Record;
import org.jdbi.v3.core.mapper.CaseStrategy;
import org.jdbi.v3.core.mapper.MapMappers;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

public class RecordMapper implements RowMapper<Record> {
    private final boolean numberDuplicates;
    private final Function<StatementContext, UnaryOperator<String>> getCaseStrategy;

    public RecordMapper() {
        this(false, CaseStrategy.NOP);
    }

    public RecordMapper(CaseStrategy caseStrategy) {
        this(false, caseStrategy);
    }

    public RecordMapper(boolean numberDuplicates,
                        CaseStrategy caseStrategy) {
        this.numberDuplicates = numberDuplicates;
        this.getCaseStrategy = ctx -> caseStrategy;
    }

    public RecordMapper(boolean numberDuplicates) {
        this.numberDuplicates = numberDuplicates;
        this.getCaseStrategy = ctx -> ctx.getConfig(MapMappers.class).getCaseChange();
    }

    @Override
    public Record map(ResultSet rs, StatementContext ctx) throws SQLException {
        return specialize(rs, ctx).map(rs, ctx);
    }

    @Override
    public RowMapper<Record> specialize(ResultSet rs, StatementContext ctx) throws SQLException {
        final List<String> columnNames = getColumnNames(rs, getCaseStrategy.apply(ctx));

        return (r, c) -> {
            Map<String, Object> row = new LinkedHashMap<>(columnNames.size());

            for (int i = 0; i < columnNames.size(); i++) {
                String key = columnNames.get(i);
                Object value = rs.getObject(i + 1);

                if(value instanceof Timestamp) {
                    value = ((Timestamp) value).toLocalDateTime();
                }

                if(value instanceof Date) {
                    value = ((Date) value).toLocalDate();
                }

                row.put(key, value);
            }

            return new Record(row);
        };
    }

    private List<String> getColumnNames(ResultSet rs, UnaryOperator<String> caseChange) throws SQLException {
        // important: ordered and unique
        Set<String> columnNames = new LinkedHashSet<>();
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
            String columnName = meta.getColumnName(i + 1);
            String alias = meta.getColumnLabel(i + 1);

            String name = caseChange.apply(alias == null ? columnName : alias);

            boolean added = columnNames.add(name);

            if (!added) {
                if(numberDuplicates) {
                    Optional<String> nextColumnName = IntStream.range(2, 100)
                            .mapToObj(num -> String.format("%s_%d", name, num))
                            .filter(numberedName -> !name.contains(numberedName))
                            .findFirst();

                    if(nextColumnName.isPresent()) {
                        columnNames.add(nextColumnName.get());
                    }
                    else {
                        throw new RuntimeException("Too many for duplicates found for column \"" + name + "\" in ResultSet.");
                    }
                }
                else {
                    throw new RuntimeException("Column \"" + name + "\" appeared twice in this ResultSet!");
                }
            }
        }

        return new ArrayList<>(columnNames);
    }
}
