package com.example.cargaUnificada.resource.request;

import java.util.ArrayList;
import java.util.List;

public class CargaUnificadaRuleRequest {

	private List<ServicioRuta> servicioRutas = new ArrayList<>();

	public List<ServicioRuta> getServicioRutas() {
		return servicioRutas;
	}

	public void setServicioRutas(List<ServicioRuta> servicioRutas) {
		this.servicioRutas = servicioRutas;
	}
}
