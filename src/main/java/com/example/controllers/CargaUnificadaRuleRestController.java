package com.example.controllers;

import com.example.cargaUnificada.resource.request.CargaUnificadaRuleRequest;
import com.example.cargaUnificada.resource.response.Actividad;
import com.example.cargaUnificada.resource.response.CargaUnificadaRuleResponse;
import com.example.cargaUnificada.resource.rule.model.Ot;
import com.example.ruleEngine.drools.resource.service.DroolsRuleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "cargaunificada")
public class CargaUnificadaRuleRestController {


	private DroolsRuleService droolsRuleService = new DroolsRuleService();

	@PostMapping("/ruta")
	public CargaUnificadaRuleResponse evaluate(@RequestBody @Validated CargaUnificadaRuleRequest request) {

		CargaUnificadaRuleResponse cargaUnificadaRuleResponse = new CargaUnificadaRuleResponse();

		request.getServicioRutas().
				stream().
				peek((servicioRuta) -> System.out.println("procesando id de servicio : " + servicioRuta.getIdServicio())).
				forEach(servicioRuta -> {
					com.example.cargaUnificada.resource.response.ServicioRuta servicioRutaResponse = new com.example.cargaUnificada.resource.response.ServicioRuta();
					servicioRutaResponse.setCodServicio(servicioRuta.getCodServicio());
					servicioRutaResponse.setCodProducto(servicioRuta.getCodProducto());
					servicioRutaResponse.setIdServicio(servicioRuta.getIdServicio());

					servicioRuta.getOts().stream().
							peek((ot) -> System.out.println("procesando Ot ID : " + ot.getIdOT())).
							map(ot -> {

								com.example.cargaUnificada.resource.rule.model.Ot otToEvaluate =
										new com.example.cargaUnificada.resource.rule.model.Ot();

								otToEvaluate.setId(ot.getIdOT());
								otToEvaluate.setCodigo(ot.getCodOT());
								otToEvaluate.setEntidad(ot.getEntidad());
								otToEvaluate.setProducto(ot.getProducto());
								otToEvaluate.setCodigo_tipo(ot.getCodTipoOT());

								ot.getActividades().stream().forEach(actividad -> {

									com.example.cargaUnificada.resource.rule.model.Actividad actividad1 =
											new com.example.cargaUnificada.resource.rule.model.Actividad();

									actividad1.setCodActividad(actividad.getCodActividad());

									otToEvaluate.getActividades().add(actividad1);

								});

								return otToEvaluate;
							}).
							map(droolsRuleService::execute).
							map(Ot.class::cast).
							forEach(ot -> {
								com.example.cargaUnificada.resource.response.Ot otResponse = new com.example.cargaUnificada.resource.response.Ot();

								otResponse.setCodTipoOT(ot.getCodigo_tipo());
								otResponse.setCodOT(ot.getCodigo());
								otResponse.setIdOT(ot.getId());
								otResponse.setValorMinimo(ot.getValorMinimo());
								otResponse.setValorMaximo(ot.getValorMaximo());
								otResponse.setProducto(ot.getProducto());
								otResponse.setEntidad(ot.getEntidad());

								ot.getActividades().stream().
										peek((actividad) -> System.out.println("procesando actividad devuelta por la rule : " + actividad.getCodActividad())).
										forEach(actividad -> {
											Actividad actividad1 = new Actividad();

											actividad1.setCodActividad(actividad.getCodActividad());
											otResponse.getActividades().add(actividad1);
										});

								servicioRutaResponse.getOts().add(otResponse);

							});
					cargaUnificadaRuleResponse.getServicioRutas().add(servicioRutaResponse);
				});

		System.out.println(cargaUnificadaRuleResponse);
		return cargaUnificadaRuleResponse;
	}

}

