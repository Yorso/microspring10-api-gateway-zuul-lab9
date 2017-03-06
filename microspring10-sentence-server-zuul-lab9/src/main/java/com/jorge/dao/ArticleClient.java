package com.jorge.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jorge.domain.Word;

@FeignClient("ARTICLE")
public interface ArticleClient {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public Word getWord();

}
