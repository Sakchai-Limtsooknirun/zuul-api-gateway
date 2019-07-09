package com.digitalacademy.zuul.filters;

import com.digitalacademy.zuul.api.AuthServiceApi;
import com.digitalacademy.zuul.model.GetAuthResponse;
import com.digitalacademy.zuul.utils.JsonToObjectConverter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

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

        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String token = ctx.getRequest().getHeader("accessToken");
        System.out.println(token);
        String data;
        try {
            data = this.authServiceApi.verifyUser(token);
            System.out.println(data);
            GetAuthResponse user_id = JsonToObjectConverter.readValue(data, GetAuthResponse.class);
            System.out.println(user_id.getUser_id());
        }catch (Exception e){
            System.err.println(e);
        }
        return null;
    }

}