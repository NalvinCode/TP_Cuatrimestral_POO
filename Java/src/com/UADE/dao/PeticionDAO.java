package com.UADE.dao;

import com.UADE.model.Peticion;

public class PeticionDAO extends GenericDAO<Peticion> {
    public PeticionDAO() throws Exception {
        super(Peticion.class, "dao/Peticion.dao");
    }
}