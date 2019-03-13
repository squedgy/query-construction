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
		try {
			log.info("drop db");
			Process p = new ProcessBuilder(new String[] {"/bin/sh", "-c", "dropdb test-db"}).start();
			p.waitFor();
		} catch (IOException e) { }
		Process p = new ProcessBuilder(new String[] {"/bin/sh", "-c", "createdb test-db"}).start();
		p.waitFor();
		if(p.exitValue() != 0) {
			throw new RuntimeException(new BufferedReader(new InputStreamReader(p.getErrorStream())).lines().collect(Collectors.joining()));
		}
		SimpleDataSource source;

		source = new SimpleDataSource();
		source.setDatabaseName("test-db");
		source.setPortNumber(5432);
		source.setServerName("localhost");
		source.setUser("postgres");
		jdbi = Jdbi.create(source);
		jdbi.useHandle(handle -> {
			handle.createScript(IO.slurp(Thread.currentThread().getContextClassLoader().getResource("create.sql"))).execute();
		});
	}

	@AfterClass
	public static void after() throws Exception {
		log.info("drop db");
		Process p = new ProcessBuilder(new String[] {"/bin/sh", "-c", "dropdb test-db"}).start();
		p.waitFor();
	}

	@Test
	public void likeATestOrSomething() {
		SelectQuery query = select("aDateTime", "aDate", "aTime")
				.from("anotherTest")
				.innerJoin("test").alias("t").on("testId", "anotherTest", "anotherTestId")
				.where()
					.column("t.testId").greaterThan(1)
					.and()
					.column("t.bool").notNull()
				.build();

		runQuery(query);
	}

	@Test
	public void parenthesis() {
		SelectQuery query = select("name", "bool", "shortName")
				.from("test")
				.where()
					.startParenthesizedGroup()
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
				.build();

		runQuery(query);
	}

	@Test
	public void mixedParenthesis() {
		SelectQuery query = select("aTableId", "intValue", "textValue")
				.from("aTable")
				.where()
					.startParenthesizedGroup()
						.column("aTableId").equalTo(2)
						.and()
						.column("intValue").in(22)
					.endParenthesizedGroup()
					.or()
					.column("textValue").equalTo("different text")
					.and()
					.column("aTableId").greaterThan(0)
				.build();

		runQuery(query);
	}

	@Test
	public void compoundParenthesis() {
		SelectQuery query = select("*")
				.from("aTable")
				.where()
				.startParenthesizedGroup()
					.startParenthesizedGroup()
						.column("aTableId")
						.greaterThan(0)
					.endParenthesizedGroup()
					.or()
					.column("booleanValue")
					.isTrue()
				.endParenthesizedGroup()
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
				.build();
		runQuery(query);
	}

	@Test
	public void anotherJoin() {
		SelectQuery query = select("*")
				.from("test")
					.crossJoin("aTable")
				.build();

		runQuery(query);
	}

	@Test
	public void bindableTest() {
		SelectQuery query = select("*")
				.from("test")
				.where()
					.binding()
					.column("testId")
					.equalTo("testId")
				.build();
		Map<String,Object> binds = new HashMap<>();
		binds.put("testId", 1);
		runBoundQuery(query, binds);
	}

	@Test
	public void parenthesizedMultiBindableTest() {
		SelectQuery query = select("*")
				.from("test")
				.where()
					.binding()
					.startParenthesizedGroup()
						.column("testId")
						.in("testIds")
						.and()
						.literal()
						.column("name")
						.like("%a%")
					.endParenthesizedGroup()
				.build();

		Map<String, Object> binds = new HashMap<>();
		binds.put("testIds", Arrays.asList(
			1,2,3,4
		));
		runBoundQuery(query, binds);

		FunStuff.main(null);

	}

	@Test
	public void groupBy() {
		SelectQuery query = select("t.bool", "COUNT(t.name) as names")
				.from("test", "t")
				.where()
					.column("testId").greaterThan(1)
					.continueBuilding()
				.groupBy("bool")
				.build();

		runQuery(query);

	}

	private void runBoundQuery(Query query, Map<String,Object> binds) {
		log.info(query.toString());
		jdbi.withHandle(handle -> {
			org.jdbi.v3.core.statement.Query q = handle.select(query.toString());
			binds.forEach((k, bind) -> {
				if(bind instanceof List){
					q.bindList(k, ((List) bind).toArray());
				} else {
					q.bind(k, bind);
				}
			});
			return q.map(new RecordMapper()).list();
		}).forEach(r -> log.info(r.toString()));
	}

	private void runQuery(Query query) {
		log.info(query.toString());
		jdbi.withHandle(handle -> handle.select(query.toString()).map(new RecordMapper()).list())
				.forEach(record -> log.info(record.toString()));
	}

}
