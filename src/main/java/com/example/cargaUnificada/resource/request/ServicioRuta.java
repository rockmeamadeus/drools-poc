package com.example.cargaUnificada.resource.request;


import java.util.ArrayList;
import java.util.List;

public class ServicioRuta {

	private String idServicio;
	private String codServicio;
	private String codProducto;
	private List<Ot> Ots = new ArrayList<>();

	public String getIdServicio() {
		return idServicio;
	}

	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}

	public String getCodServicio() {
		return codServicio;
	}

	public void setCodServicio(String codServicio) {
		this.codServicio = codServicio;
	}

	public String getCodProducto() {
		return codProducto;
	}

	public void setCodProducto(String codProducto) {
		this.codProducto = codProducto;
	}

	public List<Ot> getOts() {
		return Ots;
	}

	public void setOts(List<Ot> ots) {
		Ots = ots;
	}
}
