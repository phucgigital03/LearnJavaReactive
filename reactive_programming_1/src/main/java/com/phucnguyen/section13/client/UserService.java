package com.phucnguyen.section13.client;

import reactor.util.context.Context;

import java.util.Map;
import java.util.function.Function;

// just for demo - could be a bean in real life
public class UserService {
    private static final Map<String, String> USER_CATEGORY = Map.of(
            "sam", "standard",
            "mike", "prime"
    );

    static Function<Context, Context> userCategoryContext() {
        return ctx -> ctx.<String>getOrEmpty("user")
                .filter(optUser -> USER_CATEGORY.containsKey(optUser))
                .map(optUser -> USER_CATEGORY.get(optUser))
                .map(category -> ctx.put("category", category))
                .orElse(Context.empty());
    }

}
