package com.example.ruleEngine.drools.resource.service;

import org.springframework.stereotype.Service;

@Service
public class DroolsRuleService <T> {


	public <T> T evaluate(T object){

		return object;
	}

}
