package com.UADE.dao;

import com.UADE.model.Sucursal;

public class SucursalDAO extends GenericDAO<Sucursal> {
    public SucursalDAO() throws Exception {
        super(Sucursal.class, "dao/Sucursal.dao");
    }
}
