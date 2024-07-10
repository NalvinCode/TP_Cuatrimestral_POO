package com.UADE.dto;

public class ListaPracticasDTO {
    private final Integer codigo;
    private String nombre;

    public ListaPracticasDTO(Integer codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
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
}
