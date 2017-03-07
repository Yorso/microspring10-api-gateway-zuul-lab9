package com.jorge;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

@SpringBootApplication
@EnableZuulProxy //Zuul automatically uses Eureka service discovery (@EnableDiscoveryClient is not necessary).
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	
	//ETags can be used to eliminate the need to send a payload to the client when nothing has changed.
	@Bean
	public Filter shallowEtagHeaderFilter() {
	    return new ShallowEtagHeaderFilter();
	}  
}
