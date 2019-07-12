package com.digitalacademy.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class PostFilter extends ZuulFilter {

    private static final Logger log = LogManager.getLogger(PostFilter.class.getName());

    @Override
    public String filterType() {
        return "post";
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
        log.info("Inside Post Filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("Status : " + ctx.getResponseStatusCode());
        log.info("Request Method : " + request.getMethod() + ", Request URL : " + request.getRequestURL().toString());

        return null;
    }

}