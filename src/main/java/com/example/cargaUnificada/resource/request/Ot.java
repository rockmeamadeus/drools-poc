package com.example.cargaUnificada.resource.request;


import java.util.ArrayList;
import java.util.List;

public class Ot {

    private String idOT;

    private String codOT;

    private String codTipoOT;

    private String entidad;

    private String producto;

    private List<Actividad> actividades = new ArrayList<>();

    public String getIdOT() {
        return idOT;
    }

    public void setIdOT(String idOT) {
        this.idOT = idOT;
    }

    public String getCodOT() {
        return codOT;
    }

    public void setCodOT(String codOT) {
        this.codOT = codOT;
    }

    public String getCodTipoOT() {
        return codTipoOT;
    }

    public void setCodTipoOT(String codTipoOT) {
        this.codTipoOT = codTipoOT;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
