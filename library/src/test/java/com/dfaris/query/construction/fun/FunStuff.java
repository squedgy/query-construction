package com.dfaris.query.construction.fun;

import com.dfaris.query.construction.Query;

import static com.dfaris.query.construction.select.SelectQuery.select;

public class FunStuff {

	public static void main(String[] args) {
		Query q = select("t.name", "t.age")
				.from("People", "t")
					.fullJoin("Eggs", "e").on("personId", "t", "personId")
				.where()
					.column("t.age").greaterThan(18)
					.and()
					.column("t.name").like("D%")
					.and()
					.startParenthesizedGroup()
						.binding()
						.column("t.address").like("address")
						.or()
						.literal()
						.column("t.zipcode").in("53355", "53018", "00019")
					.endParenthesizedGroup()
				.build();
		System.out.println(q);

	}

}
