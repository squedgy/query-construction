package com.dfaris.query.construction;

import com.dfaris.query.construction.fun.FunStuff;
import com.dfaris.query.construction.select.SelectQuery;
import fun.mike.io.alpha.IO;
import org.jdbi.v3.core.Jdbi;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dfaris.query.construction.select.SelectQuery.select;

public class SelectQueryTest {

	private static Jdbi jdbi;
	private static final Logger log = LoggerFactory.getLogger(SelectQueryTest.class);

	@BeforeClass
	public static void before() throws Exception {
		jdbi = Helper.init();
	}

	@Test
	public void likeATestOrSomething() {
		SelectQuery query = select("aDateTime", "aDate", "aTime")
				.from("anotherTest", builder ->builder
						.innerJoin("test").alias("t").on("testId", "anotherTest", "anotherTestId")
						.build()
				)
				.where(builder -> builder.column("t.testId").greaterThan(1)
						.and()
						.column("t.bool").notNull()
						.build()
				)
				.build();

		Helper.runQuery(query);
	}

	@Test
	public void parenthesis() {
		SelectQuery query = select("name", "bool", "shortName")
				.from("test")
				.where(builder -> builder.startParenthesizedGroup()
							.column("testId").equalTo(1)
							.or()
							.column("bool").isTrue()
						.endParenthesizedGroup()
						.or()
						.startParenthesizedGroup()
							.column("name").equalTo("david")
							.and()
							.column("bool").isFalse()
						.endParenthesizedGroup()
						.build()
				)
				.build();

		Helper.runQuery(query);
	}

	@Test
	public void mixedParenthesis() {
		SelectQuery query = select("aTableId", "intValue", "textValue")
				.from("aTable")
				.where(builder -> builder .startParenthesizedGroup()
							.column("aTableId").equalTo(2)
							.and()
							.column("intValue").in(22)
						.endParenthesizedGroup()
						.or()
						.column("textValue").equalTo("different text")
						.and()
						.column("aTableId").greaterThan(0)
						.build()
				)
				.build();

		Helper.runQuery(query);
	}

	@Test
	public void compoundParenthesis() {
		SelectQuery query = select("*")
				.from("aTable")
				.where(builder -> builder.startParenthesizedGroup()
							.startParenthesizedGroup()
								.column("aTableId")
								.greaterThan(0)
							.endParenthesizedGroup()
							.or()
							.column("booleanValue")
							.isTrue()
						.endParenthesizedGroup()
						.build()
				)
				.build();

		Helper.runQuery(query);
	}

	@Test
	public void join() {
		SelectQuery query = select("test.testId", "anotherTest.anotherTestId", "aTable.aTableId")
				.from("test", builder -> builder.join("anotherTest").on("testId", "test", "testId")
						.fullJoin("aTable").on("aTableId", "test", "testId")
						.build()
				)
				.build();
		Helper.runQuery(query);
	}

	@Test
	public void anotherJoin() {
		SelectQuery query = select("*")
				.from("test", builder -> builder.crossJoin("aTable").build())
				.build();

		Helper.runQuery(query);
	}

	@Test
	public void bindableTest() {
		SelectQuery query = select("*")
				.from("test")
				.where(builder -> builder.binding()
						.column("testId")
						.equalTo("testId")
						.build()
				)
				.build();
		Map<String,Object> binds = new HashMap<>();
		binds.put("testId", 1);
		Helper.runQuery(query, binds);
	}

	@Test
	public void parenthesizedMultiBindableTest() {
		SelectQuery query = select("*")
				.from("test")
				.where(builder -> builder.binding()
						.startParenthesizedGroup()
							.column("testId")
							.in("testIds")
							.and()
							.literal()
							.column("name")
							.like("%a%")
						.endParenthesizedGroup()
						.build()
				)
				.build();

		Map<String, Object> binds = new HashMap<>();
		binds.put("testIds", Arrays.asList(
			1,2,3,4
		));
		Helper.runQuery(query, binds);

		FunStuff.main(null);

	}

	@Test
	public void groupBy() {
		SelectQuery query = select("t.bool", "COUNT(t.name) as names")
				.from("test", "t")
				.where(builder -> builder.column("testId").greaterThan(1).build())
				.groupBy("bool")
				.build();

		Helper.runQuery(query);

	}

	@Test
	public void having() {
		SelectQuery query = select("t.bool", "COUNT(t.name) as names")
				.from("test", "t")
				.groupBy("t.bool")
				.having(builder -> builder.column("COUNT(t.name)").greaterThan(2).build())
				.build();

		Helper.runQuery(query);
	}

	@Test
	public void parenedHaving() {

		SelectQuery query = select("bool, COUNT(*) as amount")
				.from("test")
				.groupBy("bool")
				.having(builder -> builder.startParenthesizedGroup()
							.column("COUNT(*)").notEqualTo(2)
							.and()
							.column("COUNT(*)").equalTo(1)
						.endParenthesizedGroup()
						.build()
				)
				.build();

		Helper.runQuery(query);
	}

	@Test
	public void orderBy() {
		SelectQuery query = select("*")
				.from("test", "t")
				.orderBy("shortName", "bool")
				.build();

		Helper.runQuery(query);
	}

	@Test
	public void orderBy2() {
		SelectQuery query = select("*")
				.from("test", "t")
				.orderBy("1", "2")
				.build();

		Helper.runQuery(query);
	}



	@Test
	public void bigKahuna() {
		SelectQuery query = select("textValue", "booleanValue", "COUNT(*)")
				.from("aTable", "t")
				.where(builder -> builder .column("intValue").greaterThan(9999)
						.and()
						.binding()
						.startParenthesizedGroup()
							.column("timestampValue").greaterThan("time")
						.endParenthesizedGroup()
						.build()
				)
				.groupBy("textValue", "booleanValue")
				.having(builder -> builder.column("COUNT(*)").greaterThanOrEqualTo(2).build())
				.orderBy("booleanValue")
				.build();

		Map<String,Object> binds = new HashMap<>();

		binds.put("time", LocalDateTime.of(2000, 1, 1, 0, 0));

		Helper.runQuery(query, binds);
	}

}
