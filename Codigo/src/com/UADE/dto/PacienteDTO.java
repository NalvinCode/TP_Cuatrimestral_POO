package com.UADE.dto;

import com.UADE.enums.Sexo;

public class PacienteDTO {
    private final Integer codigo;
    private Integer dni;
    private String nombreCompleto;
    private String domicilio;
    private String email;
    private Sexo sexo;
    private Integer edad;

    public PacienteDTO(Integer codigo, Integer dni, String nombreCompleto, String domicilio, String email, Sexo sexo, Integer edad) {
        this.codigo = codigo;
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.domicilio = domicilio;
        this.email = email;
        this.sexo = sexo;
        this.edad = edad;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}
