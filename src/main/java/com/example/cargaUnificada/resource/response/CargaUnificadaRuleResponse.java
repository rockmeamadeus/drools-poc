package com.example.cargaUnificada.resource.response;


import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class CargaUnificadaRuleResponse {

	private List<ServicioRuta> servicioRutas = new ArrayList<>();

}
