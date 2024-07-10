package com.UADE.model;

import com.UADE.enums.Criterio;

public class ReglaAlfa extends ReglaPractica {

    private String valorCritico;
    private String valorReservado;

    public ReglaAlfa(Integer codigoR, String valorCritico, String valorReservado, Criterio criterio) {
        super(codigoR, criterio, (valorReservado != null || valorCritico != null));
        this.valorCritico = valorCritico;
        this.valorReservado = valorReservado;
    }

    public String getValorCritico() {
        return valorCritico;
    }

    public void setValorCritico(String valorCritico) {
        this.valorCritico = valorCritico;
    }

    public String getValorReservado() {
        return valorReservado;
    }

    public void setValorReservado(String valorReservado) {
        this.valorReservado = valorReservado;
    }

}
