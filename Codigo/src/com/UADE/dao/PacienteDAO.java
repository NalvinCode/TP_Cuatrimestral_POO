package com.UADE.dao;

import com.UADE.model.Paciente;

public class PacienteDAO extends GenericDAO<Paciente> {
    public PacienteDAO() throws Exception {
        super(Paciente.class, "dao/Paciente.dao");
    }
}
