package com.UADE.model;

import com.UADE.enums.Criterio;

public abstract class ReglaPractica {
    protected final Integer codigo;
    protected Criterio criterio;
    protected boolean esReservado;

    public ReglaPractica(Integer codigo, Criterio criterio, boolean esReservado) {
        this.codigo = codigo;
        this.criterio = criterio;
        this.esReservado = esReservado;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }

    public void setEsReservado(boolean esReservado) {
        this.esReservado = esReservado;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public boolean esReservado() {
        return esReservado;
    }
}
