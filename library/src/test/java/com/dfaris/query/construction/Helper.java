package com.dfaris.query.construction;

import fun.mike.io.alpha.IO;
import org.jdbi.v3.core.Jdbi;
import org.postgresql.jdbc2.optional.SimpleDataSource;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class Helper {

	private static Logger log = getLogger(Helper.class);

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
		return jdbi;
	}

}
