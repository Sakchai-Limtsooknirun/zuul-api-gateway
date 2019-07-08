package com.digitalacademy.zuul.filters;

import com.digitalacademy.zuul.constants.StatusResponse;
import com.digitalacademy.zuul.response.ResponseModel;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@PowerMockIgnore("javax.management.*")
@RunWith(PowerMockRunner.class)
@ExtendWith(SpringExtension.class)
@PrepareForTest(RequestContext.class)
@SpringBootTest
public class ErrorFilterTest {

    @InjectMocks
    private ErrorFilter errorFilter;
    @Mock
    ZuulException exception;
    @Mock
    RequestContext context;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() {
        this.errorFilter = new ErrorFilter();
        when(request.getMethod()).thenReturn("GET");
        when(context.getRequest()).thenReturn(request);
        when(context.getResponse()).thenReturn(response);

    }

    @DisplayName("Test filter Type must return filter type is error")
    @Test
    public void TestFilterTypeisERROR_TYPE() {
        assertEquals(FilterConstants.ERROR_TYPE, errorFilter.filterType());
    }

    @DisplayName("Test filter order must return order is equal 0")
    @Test
    public void TestFilterOrderIsErrorOrder() {
        assertEquals(0, errorFilter.filterOrder());
    }

    @DisplayName("Test context throwable has zuulException shouldFilterTest() must return true")
    @Test
    public void TestShouldFilterReturnTrue() {

        RequestContext.testSetCurrentContext(context);
        when(RequestContext.getCurrentContext().getThrowable()).thenReturn(exception);

        assertEquals(exception, RequestContext.getCurrentContext().getThrowable());
        assertTrue(errorFilter.shouldFilter());
        verify(context,times(2)).getCurrentContext().getThrowable();
    }

    @DisplayName("Test context throwable has empty shouldFilterTest() must return false")
    @Test
    public void TestShouldFilterReturnFalse() {

        RequestContext.testSetCurrentContext(context);
        when(RequestContext.getCurrentContext().getThrowable()).thenReturn(null);

        assertEquals(null, RequestContext.getCurrentContext().getThrowable());
        assertFalse(errorFilter.shouldFilter());
        verify(context,times(2)).getCurrentContext().getThrowable();
    }

    @DisplayName("Test Handle Response with Internal Server Error in context response")
    @Test
    public void TestRunInternalServerError() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(StatusResponse.GET_INTERNAL_SERVER_ERROR_EXCEPTION.getCode());
        responseModel.setMessage(StatusResponse.GET_INTERNAL_SERVER_ERROR_EXCEPTION.getMessage());
        when(context.getResponse().getStatus()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR.value());
        when(context.getResponseBody()).thenReturn(responseModel.toString());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),context.getResponse().getStatus());
        assertTrue(responseModel.toString().equals(context.getResponseBody()));
        RequestContext.testSetCurrentContext(context);
        errorFilter.run();
        verify(context,times(1)).setResponseBody(responseModel.toString());

    }

    @DisplayName("Test Handle Response Too Many Request Error in context response")
    @Test
    public void TestRunTooManyRequestError() {
        ResponseModel responseModel = new ResponseModel();
        responseModel.setCode(StatusResponse.GET_TOO_MANY_REQUEST.getCode());
        responseModel.setMessage(StatusResponse.GET_TOO_MANY_REQUEST.getMessage());

        when(context.getResponse().getStatus()).thenReturn(HttpStatus.TOO_MANY_REQUESTS.value());
        when(context.getResponseBody()).thenReturn(responseModel.toString());
        assertEquals(HttpStatus.TOO_MANY_REQUESTS.value(),context.getResponse().getStatus());
        assertTrue(responseModel.toString().equals(context.getResponseBody()));
        RequestContext.testSetCurrentContext(context);
        errorFilter.run();
        verify(context,times(1)).setResponseBody(responseModel.toString());

    }

}
