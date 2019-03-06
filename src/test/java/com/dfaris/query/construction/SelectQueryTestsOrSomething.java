package com.dfaris.query.construction;

import java.sql.SQLException;

import com.dfaris.query.construction.select.SelectQuery;
import com.nm.mi.jdbi.RecordMapper;
import org.jdbi.v3.core.Jdbi;
import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.dfaris.query.construction.select.SelectQuery.select;

public class SelectQueryTestsOrSomething {

    private static Jdbi jdbi;
    private static final Logger log = LoggerFactory.getLogger(SelectQueryTestsOrSomething.class);

    @BeforeClass
    public static void before() {
        SimpleDataSource source;

        source = new SimpleDataSource();
        source.setDatabaseName("test-db");
        jdbi = Jdbi.create(source);
    }

    @Test
    public void likeATestOrSomething() {
        SelectQuery query = select("aDateTime", "aDate", "aTime")
                .from("anotherTest")
                    .innerJoin("test")
                        .alias("t")
                        .on("testId", "anotherTest", "anotherTestId")
                    .setFrom()
                .where()
                    .column("t.testId")
                    .greaterThan(1)
                    .and()
                    .column("t.bool")
                    .notNull()
                    .build()
                .build();

        runQuery(query);
    }

    @Test
    public void parenthesis() {
        SelectQuery query = select("name", "bool", "shortName")
                .from("test")
                    .setFrom()
                .where()
                    .startParenthesizedGroup()
                        .column("testId")
                        .equalTo(1)
                        .or()
                        .column("bool")
                        .isTrue()
                    .endParenthesizedGroupOr()
                    .startParenthesizedGroup()
                        .column("name")
                        .equalTo("david")
                        .and()
                        .column("bool")
                        .isFalse()
                    .endParenthesizedGroup()
                    .build()
                .build();

        runQuery(query);
    }

    @Test
    public void mixedParenthesis() {
        SelectQuery query = select("aTableId", "intValue", "textValue")
                .from("aTable")
                    .setFrom()
                .where()
                    .startParenthesizedGroup()
                        .column("aTableId")
                        .equalTo(2)
                        .and()
                        .column("intValue")
                        .in(22)
                    .endParenthesizedGroupOr()
                    .column("textValue")
                    .equalTo("different text")
                    .and()
                    .column("aTableId")
                    .greaterThan(0)
                    .build()
                .build();

        runQuery(query);
    }

    @Test
    public void compoundParenthesis() {
        SelectQuery query = select("*")
                .from("aTable")
                    .setFrom()
                .where()
                    .startParenthesizedGroup()
                        .startParenthesizedGroup()
                            .column("aTableId")
                            .greaterThan(0)
                        .endParenthesizedGroupOr()
                        .column("booleanValue")
                        .isTrue()
                    .endParenthesizedGroup()
                    .build()
                .build();

        runQuery(query);
    }

    @Test
    public void join() {
        SelectQuery query = select("test.testId", "anotherTest.anotherTestId", "aTable.aTableId")
                .from("test")
                    .join("anotherTest")
                    .on("testId", "test", "testId")
                    .fullJoin("aTable")
                    .on("aTableId", "test", "testId")
                .setFrom()
            .build();
        runQuery(query);
    }

    @Test
    public void anotherJoin() {
        SelectQuery query = select("*")
                    .from("test")
                    .crossJoin("aTable")
                    .setFrom()
                .build();

        runQuery(query);
    }

    private void runQuery(Query query) {
        log.info(query.toString());
        jdbi.withHandle(handle -> handle.select(query.toString()).map(new RecordMapper()).list())
                .forEach(record -> log.info(record.toString()));
    }

}
