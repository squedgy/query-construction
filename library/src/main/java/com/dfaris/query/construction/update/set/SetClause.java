package com.dfaris.query.construction.update.set;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Stringable;

import java.util.LinkedList;
import java.util.List;

import static com.dfaris.query.construction.ValueConverters.getBindingValueOf;
import static com.dfaris.query.construction.ValueConverters.getSqlValueOf;

public class SetClause extends Stringable implements Clause {

	private final String[] columns;
	private final String[] values;

	private SetClause(List<String> columns, List<String> values) {
		if(columns.size() != values.size()) throw new IllegalArgumentException("The amount of columns are not equal to the amount of values!");
		this.columns = columns.toArray(new String[0]);
		this.values = values.toArray(new String[0]);
	}

	@Override
	public String getClauseStarter() {
		return "SET ";
	}

	@Override
	public String toString() {
		StringBuilder clause = new StringBuilder();

		for (int i = 0; i < columns.length; i++) clause.append(columns[i]).append(" = ").append(values[i]);

		return clause.toString();
	}

	public static class SetClauseBuilder {

		private final List<String> columns = new LinkedList<>();
		private final List<String> values = new LinkedList<>();
		private boolean binding = false;

		private SetClauseBuilder() { }

		public static SetClauseBuilder set(){
			return new SetClauseBuilder();
		}

		public SetClauseBuilder set(Object... maps) {
			columns.clear();
			values.clear();
			if(maps.length %2 != 0) throw new IllegalArgumentException("Must provide and even number of arguments to set (String followed by value object/binding name).");
			for(int i = 0; i < maps.length; i+=2) {
				if(!(maps[i] instanceof String)) throw new IllegalArgumentException("The first object in a set of 2 MUST be a string as it's a column.");
				columns.add((String)maps[i]);
				values.add(binding ? getBindingValueOf(maps[i+1]) : getSqlValueOf(maps[i+1]));
			}

			return this;
		}

		public SetClauseBuilder set(String column, Object to) {
			columns.add(column);
			values.add(binding ? getBindingValueOf(to) : getSqlValueOf(to));
			return this;
		}

		public SetClauseBuilder binding() {
			this.binding = true;
			return this;
		}

		public SetClauseBuilder literal() {
			this.binding = false;
			return this;
		}

		public SetClause build() {
			return new SetClause(columns, values);
		}

	}

}
