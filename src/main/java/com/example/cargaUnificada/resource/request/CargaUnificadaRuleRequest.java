package com.example.cargaUnificada.resource.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CargaUnificadaRuleRequest {

	private List<ServicioRuta> servicioRutas = new ArrayList<>();

}
