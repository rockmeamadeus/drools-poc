package com.example.cargaUnificada.resource.response;


import java.util.ArrayList;
import java.util.List;

public class CargaUnificadaRuleResponse {

	private List<ServicioRuta> servicioRutas = new ArrayList<>();

	public List<ServicioRuta> getServicioRutas() {
		return servicioRutas;
	}

	public void setServicioRutas(List<ServicioRuta> servicioRutas) {
		this.servicioRutas = servicioRutas;
	}

}
