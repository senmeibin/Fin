package myFilters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.Results;

public class SecureFilter implements Filter {
    @Override
    public Result filter(FilterChain chain, Context context) {

        // if we got no cookies we break:
        if (context.getSession() == null
                || context.getSession().get("loginStatus") == null
                || !context.getSession().get("loginStatus").equals("OK")) {

            return Results.forbidden().html().template("/views/ApplicationController/login.ftl.html");

        } else {
            return chain.next(context);
        }

    }
}