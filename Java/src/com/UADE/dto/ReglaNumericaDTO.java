package com.UADE.dto;

import com.UADE.enums.Criterio;

public class ReglaNumericaDTO extends ReglaPracticaDTO {

    private Float valorCritico;
    private Float valorReservado;

    public ReglaNumericaDTO(Integer codigo, Float valorCritico, Float valorReservado, Criterio criterio) {
        super(codigo, criterio, (valorReservado != null || valorCritico != null));
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
