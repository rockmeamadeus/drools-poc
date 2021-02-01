package com.example.cargaUnificada.resource.request;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Ot {

    private String idOT;

    private String codOT;

    private String codTipoOT;

    private String entidad;

    private List<Actividad> actividades = new ArrayList<>();

}
