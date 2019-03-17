package com.dfaris.query.construction;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.dfaris.query.construction.delete.DeleteQuery.DeleteBuilder.deleteFrom;

public class DeleteTest {

	@BeforeClass
	public static void before() throws Exception {
		Helper.init();
	}

	@Test
	public void deleteStuff() {
		Query q = deleteFrom("aTable")
				.where(builder -> builder.column("aTableId").equalTo(1).build())
				.build();

		Helper.runUpdate(q);
	}

	@Test
	public void deleteStuffBoundPositional() {
		List<Object> binds = Arrays.asList(2);

		Query q = deleteFrom("aTable")
				.where(builder -> builder.binding().column("aTableId").equalTo(1).build())
				.build();

		Helper.runUpdate(q, binds);
	}

	@Test
	public void deleteStuffBoundNamed() {
		Map<String,Object> binds = new HashMap<>();
		binds.put("id", 3);

		Query q = deleteFrom("aTable")
				.where(builder -> builder.binding().column("aTableId").equalTo("id").build())
				.build();

		Helper.runUpdate(q, binds);
	}

}
