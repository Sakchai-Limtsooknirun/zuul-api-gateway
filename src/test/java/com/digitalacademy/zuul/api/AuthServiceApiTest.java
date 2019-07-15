package com.digitalacademy.zuul.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@PowerMockIgnore("javax.management.*")
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AuthServiceApiTest {

    @Mock
    RestTemplate restTemplate ;

    @InjectMocks
    AuthServiceApi authServiceApi ;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.authServiceApi = new AuthServiceApi(restTemplate);
    }

    public static ResponseEntity<String> prepareResponseEntitySuccessForVerify() {
        return ResponseEntity.ok(
                "{\"status\":{\"code\": 1000 ,\"message\":\"success\"},\"data\":{\"user_id\":1}}"
        );
    }
    public static ResponseEntity<String> prepareResponseEntitySuccessButStatusCodeIsNot1000() {
        return ResponseEntity.ok(
                "{\"status\":{\"code\":\"1050\",\"message\":\"success\"},\"data\":{\"user_id\":1}}"
        );
    }


    @DisplayName("Test token verify success")
    @Test
    public void TestTokenVerifySuccess()  {
        Mockito.when(restTemplate.exchange(
                Matchers.anyString(),
                Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(),
                Matchers.<Class<String>>any()
        )).thenReturn(this.prepareResponseEntitySuccessForVerify());
        ResponseEntity<String> response = authServiceApi.verifyUser("token");
        assertEquals(prepareResponseEntitySuccessForVerify(),response);

    }

    @DisplayName("Test token verify success")
    @Test
    public void TestTokenVerifyNoFalil()  {
        Mockito.when(restTemplate.exchange(
                Matchers.anyString(),
                Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(),
                Matchers.<Class<String>>any()
        )).thenReturn(this.prepareResponseEntitySuccessButStatusCodeIsNot1000());
        ResponseEntity<String> response = authServiceApi.verifyUser("token");
        assertEquals(prepareResponseEntitySuccessButStatusCodeIsNot1000(),response);

    }


}