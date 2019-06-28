package com.digitalacademy.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.monitoring.MonitoringHelper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PreFilterTest  {

    @InjectMocks
    private PreFilter preFilter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MonitoringHelper.initMocks();
    }

    @Test
    public void filterType() {
        assertEquals(PRE_TYPE , preFilter.filterType());
    }

    @Test
    public void filterOrder() {
        assertEquals(DEBUG_FILTER_ORDER, preFilter.filterOrder());
    }

    @Test
    public void shouldFilter() {
        RequestContext context = mock(RequestContext.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(context.getRequest()).thenReturn(request);
        when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:99999"));
        //when(request.getRequestURL().toString()).thenReturn(new StringBuffer("http://localhost:99999").toString());



        assertEquals(true, preFilter.shouldFilter());

    }



    @Test
    public void testRun() {

//        RequestContext context = mock(RequestContext.class);
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(request.getMethod()).thenReturn("GET");
//        when(context.getRequest()).thenReturn(request);
//        when(context.getResponse()).thenReturn(response);
//
//        RequestContext.testSetCurrentContext(context);
//        preFilter.run();
    }

}