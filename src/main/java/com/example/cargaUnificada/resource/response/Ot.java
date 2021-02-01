package com.example.cargaUnificada.resource.response;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Ot {

	private String idOT;

	private String codOT;

	private String codTipoOT;

	private String entidad;

	private double valorMinimo;

	private double valorMaximo;

	private Set<Actividad> actividades = new HashSet<>();

}
