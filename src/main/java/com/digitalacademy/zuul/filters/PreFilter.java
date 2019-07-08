package com.digitalacademy.zuul.filters;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

public class PreFilter extends ZuulFilter {

    private static final Logger log = LogManager.getLogger(PreFilter.class.getName());

    @Override
    public String filterType() {
        return PRE_TYPE;
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
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.debug("Inside Pre Filter");
        log.info("Request Method : " + request.getMethod() + ", Request URL : " + request.getRequestURL().toString());
        return null;
    }

}