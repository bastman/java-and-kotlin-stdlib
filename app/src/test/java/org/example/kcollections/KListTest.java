package org.example.kcollections;

import org.junit.jupiter.api.Test;

class KListTest {

    record User(
            String name, Integer age, String countryCode
    ) {
    }

    ;

    @Test
    void foo1() {
        var users = java.util.List.of(
                new User("u_001", 10, "DE"),
                new User("u_002", 19, "DE"),
                new User("u_003", 20, "DE"),
                new User("u_101", 10, "US"),
                new User("u_102", 19, "US"),
                new User("u_103", 19, "US")
        );
        var result = KList.of(users)
                .filter(u -> u.age > 18)
                .map(User::name)
                .groupBy(name -> name.charAt(0))
                .toMap();

        System.out.println(result);
    }

    @Test
    void foo2() {
        var users = java.util.List.of(
                new User("u_001", 10, "DE"),
                new User("u_002", 19, "DE"),
                new User("u_003", 20, "DE"),
                new User("u_101", 10, "US"),
                new User("u_102", 19, "US"),
                new User("u_103", 19, "US")
        );
        var result = KList.of(users)
                .filter(u -> u.age > 18)
                .map(User::name)
                .groupBy(name -> name.charAt(0))
                .toMap();

        System.out.println(result);
    }

}