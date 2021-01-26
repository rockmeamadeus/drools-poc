package com.example.cargaUnificada.resource.request;


import java.util.ArrayList;
import java.util.List;

public class Ot {

    private String idOT;

    private String codOT;

    private String codTipoOT;

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

    public void agregarActividad(String actividad) {
        Actividad actividad1 = new Actividad();
        actividad1.setCodActividad(actividad);
        this.getActividades().add(actividad1);

    }

    public void removerActividad(String actividad) {
        this.getActividades().removeIf(actividad1 -> actividad.equalsIgnoreCase(actividad1.getCodActividad()));

    }
}
