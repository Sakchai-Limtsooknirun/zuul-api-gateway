package com.digitalacademy.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

public class RouteFilter extends ZuulFilter {

    private static final Logger log = LogManager.getLogger(RouteFilter.class.getName());

    @Override
    public String filterType() {
        return ROUTE_TYPE;
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
        RequestContext ctx = RequestContext.getCurrentContext();
        log.info(ctx);
        HttpServletRequest request = ctx.getRequest();
        log.info(request);

        return null;
    }

}