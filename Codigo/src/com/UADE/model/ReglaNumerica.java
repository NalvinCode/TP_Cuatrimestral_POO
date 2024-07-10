package com.UADE.model;

import com.UADE.enums.Criterio;

public class ReglaNumerica extends ReglaPractica {

    private Float valorCritico;
    private Float valorReservado;

    public ReglaNumerica(Integer codigoR, Float valorCritico, Float valorReservado, Criterio criterio) {
        super(codigoR, criterio, (valorReservado != null || valorCritico != null));
        this.valorCritico = valorCritico;
        this.valorReservado = valorReservado;
    }

    public Float getValorCritico() {
        return valorCritico;
    }

    public void setValorCritico(Float valorCritico) {
        this.valorCritico = valorCritico;
    }

    public Float getValorReservado() {
        return valorReservado;
    }

    public void setValorReservado(Float valorReservado) {
        this.valorReservado = valorReservado;
    }

}
