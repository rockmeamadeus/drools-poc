package com.example.ruleEngine.drools.resource.service;

import com.example.config.DroolsBeanFactory;
import com.example.utils.DrlManager;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.stereotype.Service;

@Service
public class DroolsRuleService {

    private KieSession kSession;

    //@Autowired
   // private DroolsBeanFactory droolsBeanFactory;

    public Object execute(Object object) {

        kSession = new DroolsBeanFactory().getKieSession(DrlManager.getDrl("rule1"));
        kSession.insert(object);
        kSession.fireAllRules();
        kSession.dispose();
        return object;
    }

    public void add(byte[] bytes) {

        Resource resource = ResourceFactory.newByteArrayResource(bytes);
        kSession = new DroolsBeanFactory().getKieSession(resource);

        System.out.println("###################### resultado ###################");
        System.out.println(new DroolsBeanFactory().getDrlFromExcel(bytes));
    }


}
