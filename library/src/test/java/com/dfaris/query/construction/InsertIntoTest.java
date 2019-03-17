package com.dfaris.query.construction;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.Update;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dfaris.query.construction.insert.InsertQuery.InsertQueryBuilder.insertInto;
import static org.slf4j.LoggerFactory.getLogger;

public class InsertIntoTest {

	private static Jdbi jdbi;
	private static final Logger log = getLogger(InsertIntoTest.class);

	@BeforeClass
	public static void before() throws Exception {
		jdbi = Helper.init();
	}

	@Test
	public void insertStuff() {
		Query q = insertInto("test", "name", "bool", "shortName")
				.values(builder -> builder.insert("Flocklin", true, "james").build())
				.build();

		Helper.runUpdate(q);
	}

	@Test
	public void insertList() {
		Query q = insertInto("test", "name", "bool", "shortName")
				.values(builder -> builder.insert(Arrays.asList("Flocklin", true, "james")).build())
				.build();

		Helper.runUpdate(q);
	}

	@Test
	public void insertStuffANDList() {
		Query q = insertInto("test", "name", "bool", "shortName")
				.values(builder -> builder.insert("James", true, "Flocklin", Arrays.asList("Flocklin", false, "James")).build())
				.build();

		Helper.runUpdate(q);
	}

	@Test
	public void boundNamedInsert() {
		HashMap<String,Object> binds = new HashMap<>();
		binds.put("name", "James");
		binds.put("bool", false);
		binds.put("shor", "Flocklin");

		Query q = insertInto("test", "name", "bool", "shortName")
				.values(builder -> builder.binding().insert("name", "bool", "shor").build())
				.build();

		Helper.runUpdate(q, binds);
	}

	@Test
	public void boundPositionalInsert() {
		List<Object> binds = Arrays.asList("James", true, "Flocklin");

		Query q = insertInto("test", "name", "bool", "shortName")
				.values(builder -> builder.insert(3).build())
				.build();

		Helper.runUpdate(q, binds);
	}

	@Test
	public void multiPositionalBoundInsert() {
		List<Object> binds = Arrays.asList("James", true, "Flocklin", "Sarah", false, "Jameskii");

		Query q = insertInto("test", "name", "bool", "shortName")
				.values(builder -> builder.insert(6).build())
				.build();

		Helper.runUpdate(q, binds);
	}

	@Test
	public void multiBoundNamedInsert() {
		HashMap<String,Object> binds = new HashMap<>();
		binds.put("name", "James");
		binds.put("bool", false);
		binds.put("shor", "Flocklin");
		binds.put("name2", "Boolean");
		binds.put("bool2", true);
		binds.put("shor2", "Timothy");

		List l = Arrays.asList("name2", "bool2", "shor2");

		Query q = insertInto("test", "name", "bool", "shortName")
				.values(builder -> builder.binding().insert("name", "bool", "shor", "name2", "bool2", "shor2").build())
				.build();

		Helper.runUpdate(q, binds);
	}

	@Test
	public void listBoundInsert() {
		List<Object> bindList = Arrays.asList("James", true, "Flocklin");
		List<Object> bindList2= Arrays.asList("Flocklin", false, "James");
		Map<String,Object> binds = new HashMap<>();
		binds.put("list", bindList);
		binds.put("list2", bindList2);

		List<String> stringBind = Arrays.asList("list");
		List<String> stringBind2= Arrays.asList("list2");

		Query q = insertInto("test",  "name", "bool", "shortName" )
				.values(builder -> builder.binding().insert(stringBind, stringBind2).build())
				.build();

		Helper.runUpdate(q, binds);
	}

}
