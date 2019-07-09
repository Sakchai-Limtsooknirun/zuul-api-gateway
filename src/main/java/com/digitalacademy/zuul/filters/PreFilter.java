package com.digitalacademy.zuul.filters;

import com.digitalacademy.zuul.api.AuthServiceApi;
import com.digitalacademy.zuul.models.ResponseModel;
import com.digitalacademy.zuul.utils.JsonToObjectConverter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class PreFilter extends ZuulFilter {
    @Autowired
    private AuthServiceApi authServiceApi;

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
        return !RequestContext.getCurrentContext().getRequest().getRequestURI().startsWith("/auth");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("Inside Pre Filter");
        log.info("Request Method : " + request.getMethod() + ", Request URL : " + request.getRequestURL().toString());
        String token = ctx.getRequest().getHeader("accessToken");
        log.info(token);

        try {
            if (StringUtils.isEmpty(token)) {
                log.info("No Token");
                ZuulException zuulException = new ZuulException("token not found", HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
                ctx.setThrowable(zuulException);
                throw zuulException;
            } else {
                String data = this.authServiceApi.verifyUser(token);
                ResponseModel dataObj = JsonToObjectConverter.readValue(data, ResponseModel.class);
                log.info(dataObj != null);
                log.info(dataObj.getStatus().getCode());
                if (dataObj.getStatus().getCode() == 1000) {
                    System.out.println("correct");
                    ctx.remove("accessToken");
                    ctx.addZuulRequestHeader("id", dataObj.getData().getUser_id().toString());
                    ctx.addZuulRequestHeader("Content-Type", "application/json");
                    log.info("user id : " + dataObj.getData().getUser_id().toString());
                } else {
                    System.out.println("Incorrect");
                    System.out.println(dataObj.getStatus().toString());
                    ctx.setResponseBody(dataObj.getStatus().toString());
                }

            }
        } catch (ZuulException e) {
            System.out.println("Entered ZuulException" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Entered Exception" + e.getMessage());
        }
        return null;
    }

}