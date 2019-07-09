package com.digitalacademy.zuul.filters;

import com.digitalacademy.zuul.constants.StatusResponse;
import com.digitalacademy.zuul.response.ResponseModel;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

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
        RequestContext context = RequestContext.getCurrentContext();
        return context.getThrowable() instanceof ZuulException;

    }

    @Override
    public Object run() {
        log.error("Inside Error Filter");
        RequestContext ctx = RequestContext.getCurrentContext();
        log.error("Zuul failure detected with error code: " + ctx.getResponseStatusCode());
        log.error(ctx.getRequest().getRequestURI());
        ResponseModel responseModel = new ResponseModel();
        ctx.remove("throwable");

        if (ctx.getResponse().getStatus() == HttpStatus.TOO_MANY_REQUESTS.value() ){
            responseModel.setCode(StatusResponse.GET_TOO_MANY_REQUEST.getCode());
            responseModel.setMessage(StatusResponse.GET_TOO_MANY_REQUEST.getMessage());
            ctx.setResponseBody(responseModel.toString());
            ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
            log.info(ctx.getResponseBody());
        }else {
            responseModel.setCode(StatusResponse.GET_INTERNAL_SERVER_ERROR_EXCEPTION.getCode());
            responseModel.setMessage(StatusResponse.GET_INTERNAL_SERVER_ERROR_EXCEPTION.getMessage());
            ctx.setResponseBody(responseModel.toString());
            ctx.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            log.info(ctx.getResponseBody());

        }
        ctx.getResponse().setContentType("application/json");

        return null;
    }
}