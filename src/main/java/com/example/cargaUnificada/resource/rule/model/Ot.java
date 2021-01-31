package com.example.cargaUnificada.resource.rule.model;


import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Ot {

	private String id;

	private String codigo;

	private String codigo_tipo;

	private String entidad;

	private String producto;

	private double valorMinimo;

	private double valorMaximo;

	private List<Actividad> actividades = new ArrayList<>();


	public void agregar_actividades(List<String> actividades) {
		actividades.stream().forEach(actividad -> {
			Actividad actividad1 = new Actividad();
			actividad1.setCodActividad(actividad);
			this.actividades.add(actividad1);
		});

	}

	public void remover_actividades(List<String> actividades) {
		this.actividades.removeIf(actividad1 -> actividades.contains(actividad1));
	}

	public void establecer_valor_maximo(double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public void establecer_valor_minimo(double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}


}
