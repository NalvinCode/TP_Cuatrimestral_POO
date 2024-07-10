package com.UADE.model;

import com.UADE.enums.EstadoPeticion;

import java.util.Date;
import java.util.List;

public class Peticion {
    private final Integer codigo;
    private String obraSocial;
    private Date fechaInicio;
    private EstadoPeticion estadoPeticion;
    private Integer codPaciente;
    private Integer codSucursal;
    private List<Integer> codPracticas;

    public Peticion(Integer codigo, String obraSocial, Date fechaInicio, EstadoPeticion estadoPeticion, Integer codPaciente, Integer codSucursal, List<Integer> codPracticas) {
        this.codigo = codigo;
        this.obraSocial = obraSocial;
        this.fechaInicio = fechaInicio;
        this.estadoPeticion = estadoPeticion;
        this.codPaciente = codPaciente;
        this.codSucursal = codSucursal;
        this.codPracticas = codPracticas;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(String obraSocial) {
        this.obraSocial = obraSocial;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public EstadoPeticion getEstadoPeticion() {
        return estadoPeticion;
    }

    public void setEstadoPeticion(EstadoPeticion estadoPeticion) {
        this.estadoPeticion = estadoPeticion;
    }

    public Integer getCodPaciente() {
        return codPaciente;
    }

    public void setCodPaciente(Integer codPaciente) {
        this.codPaciente = codPaciente;
    }

    public Integer getCodSucursal() {
        return codSucursal;
    }

    public void setCodSucursal(Integer codSucursal) {
        this.codSucursal = codSucursal;
    }

    public List<Integer> getCodPracticas() {
        return codPracticas;
    }

    public void setCodPracticas(List<Integer> codPracticas) {
        this.codPracticas = codPracticas;
    }
}
