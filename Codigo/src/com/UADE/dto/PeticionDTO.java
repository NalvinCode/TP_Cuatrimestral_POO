package com.UADE.dto;

import com.UADE.enums.EstadoPeticion;

import java.util.Date;
import java.util.List;

public class PeticionDTO {
    private final Integer codigo;
    private String obraSocial;
    private Date fechaInicio;
    private Date fechaEntrega;
    private EstadoPeticion estadoPeticion;
    private Integer codPaciente;
    private Integer codSucursal;
    private List<Integer> codPracticas;

    public PeticionDTO(Integer codigo, String obraSocial, Date fechaInicio, Date fechaEntrega, EstadoPeticion estadoPeticion, Integer codPaciente, Integer codSucursal, List<Integer> codPracticas) {
        this.codigo = codigo;
        this.obraSocial = obraSocial;
        this.fechaInicio = fechaInicio;
        this.estadoPeticion = estadoPeticion;
        this.codPaciente = codPaciente;
        this.codSucursal = codSucursal;
        this.codPracticas = codPracticas;
        this.fechaEntrega = fechaEntrega;
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

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
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



