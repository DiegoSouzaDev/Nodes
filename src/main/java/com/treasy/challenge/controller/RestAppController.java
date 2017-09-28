package com.treasy.challenge.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/rest/saas")
public class RestAppController {
	
	@RequestMapping(value = "/hi", method = RequestMethod.GET)
	public String hi() {
		return "hello there";
	}
	
}