package com.digitalacademy.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;


@RunWith(PowerMockRunner.class)
@ExtendWith(SpringExtension.class)
@PrepareForTest(RequestContext.class)
@SpringBootTest
public class PreFilterTest  {

    @InjectMocks
    private PreFilter preFilter;
    @Mock
    HttpServletRequest request;
    @Mock
    RequestContext context;

    @Before
    public void setUp() {
        this.preFilter = new PreFilter();
        when(RequestContext.getCurrentContext()).thenReturn(context);
        when(request.getMethod()).thenReturn("GET");
        when(context.getRequest()).thenReturn(request);

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
    public void TestShouldFilterReturnTrue() {
        assertTrue(preFilter.shouldFilter());
    }

    @Test
    public void testRun() {
        String testUrl = "localhost:9999/test" ;
        when(request.getRequestURL()).thenReturn(new StringBuffer(testUrl));
        assertEquals(new StringBuffer(testUrl).toString(),request.getRequestURL().toString());
    }

}