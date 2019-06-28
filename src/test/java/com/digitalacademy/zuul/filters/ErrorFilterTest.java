package com.digitalacademy.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ErrorFilterTest {

    @InjectMocks
    private ErrorFilter errorFilter;

    @Mock
    ZuulException zuulException;

    @Before
    public void setUp() throws Exception {
        this.errorFilter = new ErrorFilter();
    }

    @Test
    public void filterTypeTest() {
        assertEquals("error", errorFilter.filterType());
    }

    @Test
    public void filterOrderTest() {
        assertEquals(0, errorFilter.filterOrder());
    }

//    @Test
//    public void shouldFilterTest() throws Exception{
//        RequestContext context = mock(RequestContext.class);
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        Throwable throwable = mock(Throwable.class);
//        context.setThrowable(throwable);
//
//        when(request.getMethod()).thenReturn("GET");
//        when(context.getRequest()).thenReturn(request);
//        when(context.getResponse()).thenReturn(response);
//        when(context.getThrowable()).thenThrow(throwable);
//
//        RequestContext.testSetCurrentContext(context);
//
//        when(context.getThrowable() instanceof ZuulException).thenReturn(true);
//        assertTrue(errorFilter.shouldFilter());
//    }

    @Test
    public void runTest() {

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
