package com.grupito.recomendaciones_services.dto;

import com.grupito.recomendaciones_services.model.Recomendaciones;

public class RecomendacionesDTO {

    private Long id;
    private String mensaje;

    public RecomendacionesDTO() {
    }

    public RecomendacionesDTO(String mensaje) {
        this.mensaje = mensaje;
    }

    public RecomendacionesDTO(Long id, String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Recomendaciones toModel() {
        Recomendaciones recomendacion = new Recomendaciones();
        recomendacion.setId(this.id);
        recomendacion.setMensaje(this.mensaje);
        return recomendacion;
    }

    public static RecomendacionesDTO fromModel(Recomendaciones recomendacion) {
        if (recomendacion == null) {
            return null;
        }
        return new RecomendacionesDTO(recomendacion.getId(), recomendacion.getMensaje());
    }
}

