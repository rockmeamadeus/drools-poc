package com.example.controllers;

import com.example.cargaUnificada.resource.request.CargaUnificadaRuleRequest;
import com.example.cargaUnificada.resource.request.Ot;
import com.example.cargaUnificada.resource.request.ServicioRuta;
import com.example.cargaUnificada.resource.response.Actividad;
import com.example.cargaUnificada.resource.response.CargaUnificadaRuleResponse;
import com.example.ruleEngine.drools.resource.service.DroolsRuleService;
import com.example.utils.DrlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "cargaunificada")
public class CargaUnificadaRuleRestController {


    private DroolsRuleService droolsRuleService = new DroolsRuleService();

    @PostMapping("/ruta")
    public ResponseEntity<CargaUnificadaRuleResponse> evaluate(@RequestBody CargaUnificadaRuleRequest request) {

        CargaUnificadaRuleResponse cargaUnificadaRuleResponse = new CargaUnificadaRuleResponse();

        request.getServicioRutas().

                stream()
                .peek((servicioRuta) -> {
                    System.out.println("***inicio****");
                    System.out.println(servicioRuta.getIdServicio());
                    System.out.println("****fin inicio****");
                }).
                map(sr -> {

                    com.example.cargaUnificada.resource.response.ServicioRuta servicioRuta = new com.example.cargaUnificada.resource.response.ServicioRuta();
                    servicioRuta.setCodServicio(sr.getCodServicio());
                    servicioRuta.setCodProducto(sr.getCodProducto());
                    servicioRuta.setIdServicio(sr.getIdServicio());

                    sr.getOts().stream().
                            map(droolsRuleService::test).
                            map(Ot.class::cast).
                            map(ot -> {

                                com.example.cargaUnificada.resource.response.Ot ot1 = new com.example.cargaUnificada.resource.response.Ot();

                                ot1.setCodTipoOT(ot.getCodTipoOT());
                                ot1.setCodOT(ot.getCodOT());
                                ot1.setIdOT(ot.getIdOT());

                                ot.getActividades().stream().map(actividad -> {
                                    Actividad actividad1 = new Actividad();

                                    actividad1.setCodActividad(actividad.getCodActividad());
                                    ot1.getActividades().add(actividad1);

                                    return null;
                                }).collect(Collectors.toList());

                                servicioRuta.getOts().add(ot1);


                                return null;
                            }).collect(Collectors.toList());

                    cargaUnificadaRuleResponse.getServicioRutas().add(servicioRuta);

                    return null;

                }).collect(Collectors.toList());


        return ResponseEntity.ok(cargaUnificadaRuleResponse);

    }

}

