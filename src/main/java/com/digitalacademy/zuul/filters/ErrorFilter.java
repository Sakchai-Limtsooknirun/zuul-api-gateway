package com.digitalacademy.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ErrorFilter extends ZuulFilter {

    private static final Logger log = LogManager.getLogger(ErrorFilter.class.getName());

    @Override
    public String filterType() {
        return "error";
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
        log.error("Inside Route Filter");
        log.error("Boom");

        return null;
    }

}