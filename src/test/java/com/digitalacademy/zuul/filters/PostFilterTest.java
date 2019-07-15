package com.digitalacademy.zuul.filters;


import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.monitoring.CounterFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.zuul.metrics.EmptyCounterFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@PowerMockIgnore("javax.management.*")
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@PrepareForTest(RequestContext.class)
@SpringBootTest
public class PostFilterTest {

    @InjectMocks
    PostFilter postFilter ;
    @Mock
    RequestContext context;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.postFilter = new PostFilter();
        when(request.getMethod()).thenReturn("GET");
        when(context.getRequest()).thenReturn(request);
        when(context.getResponse()).thenReturn(response);
    }
    @Test
    public void filterType() {
        assertEquals(POST_TYPE, postFilter.filterType());
    }

    @Test
    public void filterOrder() {
        assertEquals(1, postFilter.filterOrder());

    }

    @Test
    public void shouldFilter() {
        assertTrue(postFilter.shouldFilter());
    }

    @Test
    public void run() {
        StringBuffer Url = new StringBuffer("/test");
        when(request.getRequestURL()).thenReturn(Url);
        when(context.getResponseStatusCode()).thenReturn(123);
        assertEquals(Url,request.getRequestURL());
        assertEquals(123,context.getResponseStatusCode());
        assertEquals("GET",request.getMethod());
        RequestContext.testSetCurrentContext(context);
        postFilter.run();

    }
}