# Query Constructor

## Select

Examples are good things:

```java
import com.dfaris.query.construction.Query;
import static com.dfaris.query.construction.Select.select;

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
```

the above outputs

`SELECT t.name, t.age FROM (People t FULL JOIN Eggs e ON e.personId = t.personId) WHERE (t.address LIKE :address OR t.zipcode in ('53355','53018','00019')) `

