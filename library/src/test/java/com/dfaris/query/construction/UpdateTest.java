package com.dfaris.query.construction;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.dfaris.query.construction.update.UpdateQuery.UpdateBuilder.update;

public class UpdateTest {

	@BeforeClass
	public static void before() throws Exception {
		Helper.init();
	}

	@Test
	public void updateSet() {
		Query q = update("test")
				.set(builder -> builder.set("name", "david").build())
				.build();

		Helper.runUpdate(q);
	}

	@Test
	public void updateWhere() {
		Query q = update("test")
				.set(builder -> builder.set("name", "david").build())
				.where(builder -> builder.column("testId").equalTo(4).build())
				.build();

		Helper.runUpdate(q);
	}

	@Test
	public void updateWhereWithNamedBinds() {
		HashMap<String, Object> binds = new HashMap<>();
		binds.put("david", "david");
		binds.put("test", 5);

		Query q = update("test")
				.set(builder -> builder.binding().set("name", "david").build())
				.where(builder -> builder.binding().column("testId").equalTo("test").build())
				.build();

		Helper.runUpdate(q, binds);
	}

	@Test
	public void updateWhereWithPositionalBinds() {
		List<Object> binds = Arrays.asList("david", 6);

		Query q = update("test")
				.set(builder -> builder.binding().set("name", 1).build())
				.where(builder -> builder.binding().column("testId").equalTo(1).build())
				.build();

		Helper.runUpdate(q, binds);
	}

}
