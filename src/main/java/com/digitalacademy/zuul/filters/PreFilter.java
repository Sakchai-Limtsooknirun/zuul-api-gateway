package com.digitalacademy.zuul.filters;

import com.digitalacademy.zuul.api.AuthServiceApi;
import com.digitalacademy.zuul.constants.StatusResponse;
import com.digitalacademy.zuul.models.Response;
import com.digitalacademy.zuul.response.ResponseModel;
import com.digitalacademy.zuul.utils.JsonToObjectConverter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

public class PreFilter extends ZuulFilter {

    private String createResponse(int code, String message) {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(code);
        responseModel.setMessage(message);
        return responseModel.toString();
    }

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
        String requestUrl = RequestContext.getCurrentContext().getRequest().getRequestURI();
        return !requestUrl.startsWith("/auth") && !requestUrl.startsWith("/user/register") && !requestUrl.startsWith(
                "/user/delete/account");
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("Inside Pre Filter");
        log.info("Request Method: " + request.getMethod() + ", Request URL : " + request.getRequestURL().toString());
        log.info("Client IP: " + request.getRemoteAddr());
        String token = ctx.getRequest().getHeader("accessToken");
        ctx.addZuulRequestHeader("Content-Type", "application/json");
        ctx.getResponse().setHeader("Content-Type", "application/json");

        try {
            if (StringUtils.isEmpty(token)) {
                log.info("No Token");
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(HttpStatus.BAD_REQUEST.value());
                ctx.setResponseBody(createResponse(StatusResponse.GET_HEADER_TOKEN_NOT_FOUND.getCode(),
                        StatusResponse.GET_HEADER_TOKEN_NOT_FOUND.getMessage()));

            } else {
                ResponseEntity response = authServiceApi.verifyUser(token);
                JSONObject data = new JSONObject(response.getBody().toString());
                Response dataObj = JsonToObjectConverter.readValue(data.toString(), Response.class);
                ctx.addZuulRequestHeader("accessToken", null);
                ctx.addZuulRequestHeader("id", dataObj.getData().getUser_id().toString());
                log.info("user id: " + dataObj.getData().getUser_id().toString());

            }
        } catch (HttpClientErrorException.Forbidden e) {
            ctx.setSendZuulResponse(false);
            log.error("Status " + HttpStatus.FORBIDDEN.value() + ": Expired token");
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
            ctx.setResponseBody(createResponse(StatusResponse.GET_EXPIRED_ERROR_EXCEPTION.getCode(),
                    StatusResponse.GET_EXPIRED_ERROR_EXCEPTION.getMessage()));

        } catch (HttpClientErrorException.NotFound e) {
            ctx.setSendZuulResponse(false);
            log.error("Status " + HttpStatus.NOT_FOUND.value() + ": Invalid token");
            ctx.setResponseStatusCode(HttpStatus.NOT_FOUND.value());
            ctx.setResponseBody(createResponse(StatusResponse.GET_NOT_FOUND_ERROR_EXCEPTION.getCode(),
                    StatusResponse.GET_NOT_FOUND_ERROR_EXCEPTION.getMessage()));

        } catch (Exception e) {
            log.error("Entered Exception with : " + e.getClass().getName());
            log.error("Throw to error filter");
            ZuulException zuulException = new ZuulException(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
            ctx.setThrowable(zuulException);
            throw zuulException;

        }
        return null;
    }

}