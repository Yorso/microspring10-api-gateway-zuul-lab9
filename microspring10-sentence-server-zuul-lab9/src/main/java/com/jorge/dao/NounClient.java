package com.jorge.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jorge.domain.Word;

@FeignClient("NOUN") //"NOUN" is a Eureka client-ID => mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=noun". 
public interface NounClient {

	@RequestMapping(value="/", method=RequestMethod.GET)
	Word getWord();
	
}
