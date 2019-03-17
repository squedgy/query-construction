package com.dfaris.query.construction;

import fun.mike.io.alpha.IO;
import fun.mike.record.alpha.Record;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlStatement;
import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class Helper {

	private static Logger log = getLogger(Helper.class);

	private static Jdbi jdbi;

	public static Jdbi init() throws Exception{

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
		Jdbi jdbi = Jdbi.create(source);
		jdbi.useHandle(handle -> {
			handle.createScript(IO.slurp(Thread.currentThread().getContextClassLoader().getResource("create.sql"))).execute();
		});
		Helper.jdbi = jdbi;
		return jdbi;
	}

	public static List<Record> runQuery(Query q) {
		log.info(q.toString());

		return jdbi.withHandle(handle -> handle.createQuery(q.toString()).map(new RecordMapper()).list());
	}

	public static int runUpdate(Query q) {
		log.info(q.toString());

		return jdbi.withHandle(handle -> handle.createUpdate(q.toString()).execute());
	}

	public static List<Record> runQuery(Query q, List<Object> binds) {
		log.info(q.toString());
		log.info("BINDS: " + binds);

		return jdbi.withHandle(handle -> bind(handle.createQuery(q.toString()), binds).map(new RecordMapper()).list());
	}

	public static int runUpdate(Query q, List<Object> binds) {
		log.info(q.toString());
		log.info("BINDS: " + binds);

		return jdbi.withHandle(handle -> bind(handle.createUpdate(q.toString()), binds).execute());
	}

	public static List<Record> runQuery(Query q, Map<String,Object> binds) {
		log.info(q.toString());
		log.info("BINDS: " + binds);

		return jdbi.withHandle(handle -> bind(handle.createQuery(q.toString()), binds).map(new RecordMapper()).list());
	}

	public static int runUpdate(Query q, Map<String,Object> binds) {
		log.info(q.toString());
		log.info("BINDS: " + binds);

		return jdbi.withHandle(handle -> bind(handle.createUpdate(q.toString()), binds).execute());
	}

	public static <Return extends SqlStatement> Return bind(Return statement, List<Object> objs) {
		for(int i = 0; i < objs.size(); i++) statement.bind(i, objs.get(i));
		return statement;
	}

	public static <Return extends SqlStatement> Return bind(Return statement, Map<String, Object> binds) {
		binds.forEach((k,v) -> {
			Object o = binds.get(k);
			if(o instanceof List) statement.bindList(k, (List<?>) o);
			else statement.bind(k, v);
		});
		return statement;
	}



}
