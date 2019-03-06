package com.dfaris.query.construction;

import com.dfaris.query.construction.select.SelectQuery;
import org.junit.Assert;
import org.junit.Test;

import static com.dfaris.query.construction.select.SelectQuery.select;

public class SelectQueryTestsOrSomething {

    @Test
    public void likeATestOrSomething() {
        SelectQuery query = select("aColumn")
                .from("aTable")
                    .innerJoin("anotherTable")
                    .alias("at")
                    .on("match", "aTable", "match")
                    .setFrom()
                .where()
                    .column("aColumn")
                    .greaterThan(908)
                    .and()
                    .column("aColumn")
                    .lessThan(999)
                    .build()
                .build();

        Assert.assertEquals("SELECT aColumn FROM aTable aTable JOIN anotherTable at ON at.match = aTable.match WHERE aColumn > 908 AND aColumn < 999 ", query.toString());
    }

    @Test
    public void parenthesis() {
        SelectQuery query = select("some", "stuff", "man")
                .from("tables")
                    .setFrom()
                .where()
                    .column("some")
                    .equalTo(99)
                    .and()
                    .column("stuff")
                    .in(99, 89, 79, 9)
                    .endParenthesizedGroupOr()
                    .startParenthesizedGroup()
                    .column("man")
                    .greaterThan(55)
                    .and()
                    .column("man")
                    .lessThan(65)
                    .endParenthesizedGroup()
                .build();

        Assert.assertEquals("SELECT some, stuff, man FROM tables tables WHERE (some = 99 AND stuff in (99,89,79,9) ) OR (man > 55 AND man < 65 ) ", query.toString());
    }

}
