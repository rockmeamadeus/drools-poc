package com.example.ruleEngine.drools.resource.service;

import com.example.config.DroolsBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroolsRuleService {

    @Autowired
    private DroolsBeanFactory droolsBeanFactory;


    public Object test(Object object) {
        return object;
    }

    public void add(byte[] file) {

        String drl = droolsBeanFactory.getDrlFromExcel(file);

    }


}
