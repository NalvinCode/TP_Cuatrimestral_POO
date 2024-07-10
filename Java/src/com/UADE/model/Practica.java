package com.UADE.model;

import java.util.ArrayList;
import java.util.List;

public class Practica {
    private final Integer codigo;
    private String nombre;
    private Integer tiempoEstimado;
    private List<Integer> codSubPracticas;
    private List<Integer> codReglasPractica;

    public Practica(Integer codigo, String nombre, Integer tiempoEstimado, List<Integer> codSubPracticas, List<Integer> codReglasPractica)
    {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tiempoEstimado = tiempoEstimado;
        this.codSubPracticas = codSubPracticas;
        this.codReglasPractica = codReglasPractica;
    }

    public List<Integer> getCodSubPracticas() {
        return codSubPracticas;
    }

    public void setCodSubPracticas(List<Integer> codSubPracticas) {
        this.codSubPracticas = codSubPracticas;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTiempoEstimado() {
        return tiempoEstimado;
    }

    public void setTiempoEstimado(Integer tiempoEstimado) {
        this.tiempoEstimado = tiempoEstimado;
    }

    public List<Integer> getReglasPractica() {
        return codReglasPractica;
    }

    public void setCodReglasPractica(List<Integer> codReglasPractica) {
        this.codReglasPractica = codReglasPractica;
    }
}
