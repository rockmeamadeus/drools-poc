package com.example.cargaUnificada.resource.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Ot {

	private String idOT;

	private String codOT;

	private String codTipoOT;

	private List<Actividad> actividades = new ArrayList<>();

}
