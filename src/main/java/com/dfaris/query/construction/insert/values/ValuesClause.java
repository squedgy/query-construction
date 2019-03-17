package com.dfaris.query.construction.insert.values;

import com.dfaris.query.construction.Clause;
import com.dfaris.query.construction.Stringable;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class ValuesClause extends Stringable implements Clause {

	private final List<Object> values;
	private final int columns;

	public ValuesClause(List<Object> values, int col) {
		this.values = values;
		this.columns = col;
	}

	@Override
	public String getClauseStarter() {
		return "VALUES ";
	}

	protected void stringify(StringBuilder builder, List<?> list) {

		AtomicInteger i = new AtomicInteger(0);
		AtomicBoolean b = new AtomicBoolean(false);

		list.forEach(o -> {
			// if we just ended a group we're going to start making a new one so prepare
			if(builder.charAt(builder.length()-1) == ')') builder.append(",(");

			// If it's a list do what we're doing rn
			if(o instanceof List) {
				if(notNewBuilderGroup(builder)) throw new IllegalArgumentException("Bad assortment of items! Didn't finish a set of values before using a List!");
				stringify(builder, (List<?>) o);
				// If it's a list binding/attribute just make that the entire group and trust the individual
			} else if (o instanceof String && o.toString().matches("<.*>")) {
				if(notNewBuilderGroup(builder)) throw new IllegalArgumentException("Bad assortment of items! Didn't finish a set of values before using a List Binding!");
				builder.append(o);
				b.set(true);
				// If using the builder it's already been transformed to a SQL value (USE THE BUILDER)
			} else {
				builder.append(o);
				if(i.incrementAndGet()%columns != 0) builder.append(",");
				else b.set(true);
			}
			if(b.get()) {
				builder.append(')');
				b.set(false);
			}
		});

	}

	protected boolean notNewBuilderGroup(StringBuilder builder) {
		return !(builder.charAt(builder.length()-1) == '(');
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append('(');

		stringify(builder, values);

		return builder.toString();
	}

}
