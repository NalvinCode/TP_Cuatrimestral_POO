package com.UADE.dto;

import com.UADE.enums.Criterio;

public class ReglaAlfaDTO extends ReglaPracticaDTO {

    private String valorCritico;
    private String valorReservado;

    public ReglaAlfaDTO(Integer codigo, String valorCritico, String valorReservado, Criterio criterio) {
        super(codigo, criterio, (valorReservado != null || valorCritico != null));
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
