package com.example.cargaUnificada.resource.request;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ServicioRuta {

	private String idServicio;
	private String codServicio;
	private String codProducto;
	private List<Ot> Ots = new ArrayList<>();
}
