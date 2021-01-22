package com.example.controllers;

import com.example.cargaUnificada.resource.request.CargaUnificadaRuleRequest;
import com.example.cargaUnificada.resource.request.ServicioRuta;
import com.example.cargaUnificada.resource.response.CargaUnificadaRuleResponse;
import com.example.ruleEngine.drools.resource.service.DroolsRuleService;
import com.example.utils.DrlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "cargaunificada")
public class CargaUnificadaRuleRestController {

	@Autowired
	private DroolsRuleService droolsRuleService;

	@PostMapping("/ruta")
	public CargaUnificadaRuleResponse evaluate(@RequestBody CargaUnificadaRuleRequest request) {

		DrlManager manager = new DrlManager();
		CargaUnificadaRuleResponse cargaUnificadaRuleResponse = new CargaUnificadaRuleResponse();

		request.getServicioRutas().
				stream().map(droolsRuleService::evaluate).
				map(res -> {
					ServicioRuta servicioRuta2 = (ServicioRuta) res;
					com.example.cargaUnificada.resource.response.ServicioRuta servicioRuta1 = new com.example.cargaUnificada.resource.response.ServicioRuta();
				//	servicioRuta1.setCodProducto(servicioRuta2.get);
					//cargaUnificadaRuleResponse.getServicioRutas().add(servicioRuta);
					return null;
				});

		return cargaUnificadaRuleResponse;

	}

}

