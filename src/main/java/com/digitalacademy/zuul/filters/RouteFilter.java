package com.digitalacademy.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RouteFilter extends ZuulFilter {

    private static final Logger log = LogManager.getLogger(RouteFilter.class.getName());

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        log.info("Inside Route Filter");

        return null;
    }

}