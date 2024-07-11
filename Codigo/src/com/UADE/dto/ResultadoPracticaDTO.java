package com.UADE.dto;

public class ResultadoPracticaDTO {
    private final Integer codigo;
    private final Integer codPractica;
    private final Integer codPeticion;
    private Float resultadoNumerico;
    private String resultadoLiteral;
    private String transcription;

    public ResultadoPracticaDTO(Integer codigo, Integer codPractica, Integer codPeticion, Float resultadoNumerico, String resultadoLiteral, String transcription) {
        this.codigo = codigo;
        this.codPractica = codPractica;
        this.codPeticion = codPeticion;
        this.resultadoNumerico = resultadoNumerico;
        this.resultadoLiteral = resultadoLiteral;
        this.transcription = transcription;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Integer getCodPeticion() {
        return codPeticion;
    }

    public Integer getCodPractica() {
        return codPractica;
    }

    public Float getResultadoNumerico() {
        return resultadoNumerico;
    }

    public void setResultadoNumerico(Float resultadoNumerico) {
        this.resultadoNumerico = resultadoNumerico;
    }

    public String getResultadoLiteral() {
        return resultadoLiteral;
    }

    public void setResultadoLiteral(String resultadoLiteral) {
        this.resultadoLiteral = resultadoLiteral;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }
}
