package com.UADE.dto;

import com.UADE.enums.Criterio;

public abstract class ReglaPracticaDTO {
    protected final Integer codigo;
    protected Criterio criterio;
    protected boolean esReservado;

    public ReglaPracticaDTO(Integer codigo) {
        this.codigo = codigo;
    }

    public ReglaPracticaDTO(Integer codigo, Criterio criterio, boolean esReservado) {
        this.codigo = codigo;
        this.criterio = criterio;
        this.esReservado = esReservado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public boolean isEsReservado() {
        return esReservado;
    }
}
