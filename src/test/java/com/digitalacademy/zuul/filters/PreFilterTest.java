package com.digitalacademy.zuul.filters;

import com.digitalacademy.zuul.api.AuthServiceApi;
import com.digitalacademy.zuul.constants.StatusResponse;
import com.digitalacademy.zuul.models.GetAuthResponse;
import com.digitalacademy.zuul.models.Response;
import com.digitalacademy.zuul.models.StatusModel;
import com.digitalacademy.zuul.response.ResponseModel;
import com.digitalacademy.zuul.utils.JsonToObjectConverter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.monitoring.CounterFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.zuul.metrics.EmptyCounterFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@PowerMockIgnore("javax.management.*")
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@PrepareForTest(RequestContext.class)
@SpringBootTest
public class PreFilterTest {

    @InjectMocks
    private PreFilter preFilter;
    @Mock
    ZuulException exception;
    @Mock
    RequestContext context;
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    AuthServiceApi authServiceApi;
    @Mock
    RestTemplate restTemplate;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(preFilter).build();
        CounterFactory.initialize(new EmptyCounterFactory());
        when(request.getMethod()).thenReturn("GET");
        when(context.getRequest()).thenReturn(request);
        when(context.getResponse()).thenReturn(response);
        when(request.getRequestURL()).thenReturn(new StringBuffer("/test"));

    }

    @Test
    public void filterType() {
        assertEquals(PRE_TYPE, preFilter.filterType());
    }

    @Test
    public void filterOrder() {
        assertEquals(1, preFilter.filterOrder());

    }

    @DisplayName("Test should filter Return True")
    @Test
    public void shouldFilterReturnTrueForRequestAccessToken() {
        RequestContext.testSetCurrentContext(context);
        String uriParam = "/test";
        when(request.getRequestURI()).thenReturn(uriParam);
        boolean should = preFilter.shouldFilter();
        assertTrue(should);

    }

    @DisplayName("Test should filter Return False if request from auth service to get accessToken")
    @Test
    public void shouldFilterReturnFalseForRequestAccessToken() {
        RequestContext.testSetCurrentContext(context);
        String uriParam = "/auth";
        when(request.getRequestURI()).thenReturn(uriParam);
        boolean should = preFilter.shouldFilter();
        assertFalse(should);

    }

    @DisplayName("Test should filter Return False if request from user service to register user")
    @Test
    public void shouldFilterReturnFalseForRequestToRegisterUser() {
        RequestContext.testSetCurrentContext(context);
        String uriParam = "/user/register";
        when(request.getRequestURI()).thenReturn(uriParam);
        boolean should = preFilter.shouldFilter();
        assertFalse(should);

    }

    @DisplayName("Test should filter Return False if request from user service to delete user")
    @Test
    public void shouldFilterReturnFalseForRequestToDeleteUser() {
        RequestContext.testSetCurrentContext(context);
        String uriParam = "/user/delete/account";
        when(request.getRequestURI()).thenReturn(uriParam);
        boolean should = preFilter.shouldFilter();
        assertFalse(should);

    }

    @DisplayName("Test Not Found Token in Header")
    @Test
    public void TestRequestNoToken() throws JSONException, ZuulException {
        JSONObject mockHeader = new JSONObject();
        mockHeader.put("accessToken", null);

        when(request.getHeader("accessToken")).thenReturn(null);
        doNothing().when(context).addZuulRequestHeader("Content-Type", "application/json");
        doNothing().when(response).setHeader("Content-Type", "application/json");
        assertTrue(request.getHeader("accessToken") == null);
        RequestContext.testSetCurrentContext(context);
        preFilter.run();
    }

    private Response mockResAuthVerify() {
        GetAuthResponse id = new GetAuthResponse();
        id.setUser_id(1L);
        StatusModel status = new StatusModel();
        status.setCode(1000);
        status.setMessage("success");
        Response res = new Response();
        res.setData(id);
        res.setStatus(status);

        return res;
    }

    public static ResponseEntity<String> prepareResponseEntitySuccessForVerify() {
        return ResponseEntity.ok(
                "{\"status\":{\"code\": 1000 ,\"message\":\"success\"},\"data\":{\"user_id\":1}}"
        );
    }

    @DisplayName("Test token verify success")
    @Test
    public void TestTokenVerifySuccess() throws Exception {

        JSONObject mockHeader = new JSONObject();
        mockHeader.put("accessToken", "token");
        when(request.getHeader("accessToken")).thenReturn("token");
        doNothing().when(context).addZuulRequestHeader("Content-Type", "application/json");
        doNothing().when(response).setHeader("Content-Type", "application/json");

        Mockito.when(restTemplate.exchange(
                Matchers.anyString(),
                Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(),
                Matchers.<Class<String>>any()
        )).thenReturn(this.prepareResponseEntitySuccessForVerify());

        when(authServiceApi.verifyUser("token")).thenReturn(prepareResponseEntitySuccessForVerify());
        RequestContext.testSetCurrentContext(context);
        preFilter.run();
    }

    @DisplayName("Test wrong token in header throw NotFound exception")
    @Test
    public void TestTokenNotFound() throws Exception {

        JSONObject mockHeader = new JSONObject();
        mockHeader.put("accessToken", "invalid token");
        when(request.getHeader("accessToken")).thenReturn("invalid token");
        doNothing().when(context).addZuulRequestHeader("Content-Type", "application/json");
        doNothing().when(response).setHeader("Content-Type", "application/json");

//        String responseJson = "{ \"status\": { \"code\": \"1699\", \"message\": \"not found\" } }";

//        ResponseModel resp = new ResponseModel();
//        resp.setCode(StatusResponse.GET_NOT_FOUND_ERROR_EXCEPTION.getCode());
//        resp.setMessage(StatusResponse.GET_NOT_FOUND_ERROR_EXCEPTION.getMessage());

        when(authServiceApi.verifyUser("invalid token")).thenThrow(HttpClientErrorException.NotFound.class);
        RequestContext.testSetCurrentContext(context);
//        System.err.println(context.getResponseStatusCode());
//        assertEquals(HttpStatus.NOT_FOUND.value(), context.getResponseStatusCode());
        preFilter.run();
    }

    @DisplayName("Test expired token in header throw Forbidden exception")
    @Test
    public void TestTokenExpired() throws Exception {

        JSONObject mockHeader = new JSONObject();
        mockHeader.put("accessToken", "expired token");
        when(request.getHeader("accessToken")).thenReturn("expired token");
        doNothing().when(context).addZuulRequestHeader("Content-Type", "application/json");
        doNothing().when(response).setHeader("Content-Type", "application/json");

        when(authServiceApi.verifyUser("expired token")).thenThrow(HttpClientErrorException.Forbidden.class);
        RequestContext.testSetCurrentContext(context);
        preFilter.run();
    }

    @DisplayName("Test Internal server error throw Exception")
    @Test (expected = Exception.class)
    public void TestServerError() throws Exception {

        RequestContext.testSetCurrentContext(context);
        preFilter.run();
    }

}