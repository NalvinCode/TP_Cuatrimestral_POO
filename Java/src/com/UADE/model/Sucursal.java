package com.UADE.model;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private final Integer codigo;
    private String direccion;
    private String telefono;
    private Integer codUsuarioRespTecnico;

    public Sucursal(Integer codigo, String direccion, String telefono, Integer codUsuarioRespTecnico) {
        this.codigo = codigo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.codUsuarioRespTecnico = codUsuarioRespTecnico;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getCodUsuarioRespTecnico() {
        return codUsuarioRespTecnico;
    }

    public void setCodUsuarioRespTecnico(Integer codUsuarioRespTecnico) {
        this.codUsuarioRespTecnico = codUsuarioRespTecnico;
    }
}
