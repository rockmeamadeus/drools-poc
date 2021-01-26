package com.example.controllers;

import com.example.cargaUnificada.resource.request.CargaUnificadaRuleRequest;
import com.example.cargaUnificada.resource.request.ServicioRuta;
import com.example.cargaUnificada.resource.response.Actividad;
import com.example.cargaUnificada.resource.response.CargaUnificadaRuleResponse;
import com.example.cargaUnificada.resource.response.Ot;
import com.example.ruleEngine.drools.resource.service.DroolsRuleService;
import com.example.utils.DrlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "cargaunificada")
public class CargaUnificadaRuleRestController {

    @Autowired
    private DroolsRuleService droolsRuleService;

    @PostMapping("/ruta")
    public CargaUnificadaRuleResponse evaluate(@RequestBody CargaUnificadaRuleRequest request) {

        CargaUnificadaRuleResponse cargaUnificadaRuleResponse = new CargaUnificadaRuleResponse();

        request.getServicioRutas().
                stream().map(droolsRuleService::test).
                map(ServicioRuta.class::cast).
                map(response -> {
                    com.example.cargaUnificada.resource.response.ServicioRuta servicioRuta = new com.example.cargaUnificada.resource.response.ServicioRuta();
                    servicioRuta.setCodServicio(response.getCodServicio());
                    servicioRuta.setCodProducto(response.getCodProducto());
                    servicioRuta.setIdServicio(response.getIdServicio());

                    response.getOts().stream().map(ot -> {

                        Ot ot1 = new Ot();

                        ot1.setIdOT(ot.getIdOT());
                        ot1.setCodOT(ot.getCodOT());
                        ot1.setCodTipoOT(ot.getCodTipoOT());

                        ot.getActividades().stream().map(actividad -> {

                            Actividad actividad1 = new Actividad();
                            actividad1.setCodActividad(actividad.getCodActividad());

                            ot1.getActividades().add(actividad1);
                            servicioRuta.getOts().add(ot1);

                            return null;
                        });

                        return null;
                    });

                    cargaUnificadaRuleResponse.getServicioRutas().add(servicioRuta);
                    return null;
                });

        return cargaUnificadaRuleResponse;

    }

}

