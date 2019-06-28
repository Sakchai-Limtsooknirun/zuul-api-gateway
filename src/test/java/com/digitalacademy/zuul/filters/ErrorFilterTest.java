package com.digitalacademy.zuul.filters;

import com.digitalacademy.zuul.constants.StatusResponse;
import com.digitalacademy.zuul.response.ResponseModel;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.monitoring.MonitoringHelper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.ERROR_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_ERROR_FILTER_ORDER;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ErrorFilterTest {

    @InjectMocks
    private ErrorFilter errorFilter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MonitoringHelper.initMocks();
    }

    @Test
    public void filterType() {
        assertEquals(ERROR_TYPE , errorFilter.filterType());
    }

    @Test
    public void filterOrder() {
        assertEquals(SEND_ERROR_FILTER_ORDER, errorFilter.filterOrder());
    }

    @Test
    public void shouldFilter() {
        assertEquals(false,errorFilter.shouldFilter());

    }



    @Test
    public void testRun() {

        RequestContext context = mock(RequestContext.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getMethod()).thenReturn("GET");
        when(context.getRequest()).thenReturn(request);
        when(context.getResponse()).thenReturn(response);

        RequestContext.testSetCurrentContext(context);
        errorFilter.run();
    }
}