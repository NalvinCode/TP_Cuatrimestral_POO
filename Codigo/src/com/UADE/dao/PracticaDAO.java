package com.UADE.dao;

import com.UADE.model.Practica;

public class PracticaDAO extends GenericDAO<Practica> {
    public PracticaDAO() throws Exception {
        super(Practica.class, "dao/Practica.dao");
    }
}

