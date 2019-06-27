package com.digitalacademy.zuul.filters;

import com.digitalacademy.zuul.constants.StatusResponse;
import com.digitalacademy.zuul.response.ResponseModel;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

@Component
public class ErrorFilter extends ZuulFilter {
    private static final Logger log = LogManager.getLogger(ErrorFilter.class.getName());
    @Override
    public String filterType() {
        return ERROR_TYPE;
    }

    @Override
    public int filterOrder() {
        return SEND_ERROR_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return RequestContext.getCurrentContext().getThrowable() instanceof ZuulException;

    }

    @Override
    public Object run() {
        log.info("Inside Error Filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        final Throwable throwable = ctx.getThrowable();
        if (throwable instanceof ZuulException) {
            log.error("Zuul failure detected: " + throwable.getMessage(), throwable);

            ctx.remove("throwable");


            ResponseModel responseModel = new ResponseModel();
            responseModel.setCode(StatusResponse.GET_TECHNICAL_ERROR_EXCEPTION.getCode());
            responseModel.setMessage(StatusResponse.GET_TECHNICAL_ERROR_EXCEPTION.getMessage());
            ctx.setResponseBody(responseModel.toString());
            ctx.setResponseStatusCode(500);
            ctx.getResponse().setContentType("application/json");

        }
        return null;
    }
}