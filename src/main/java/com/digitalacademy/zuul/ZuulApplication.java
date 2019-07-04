package com.digitalacademy.zuul;

import com.digitalacademy.zuul.filters.ErrorFilter;
import com.digitalacademy.zuul.filters.PostFilter;
import com.digitalacademy.zuul.filters.PreFilter;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.RateLimiterErrorHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableZuulProxy
@Qualifier("RateLimit")
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}
	@Bean
	public PreFilter preFilter() {
		return new PreFilter();
	}

	@Bean
	public PostFilter post() {
		return new PostFilter();
	}

	@Bean
	public ErrorFilter error() {
		return new ErrorFilter();
	}


}
