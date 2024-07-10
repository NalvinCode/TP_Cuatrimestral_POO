package com.UADE.dao;

import com.UADE.model.Peticion;
import com.UADE.model.ReglaAlfa;

public class ReglaAlfaDAO extends GenericDAO<ReglaAlfa> {
    public ReglaAlfaDAO() throws Exception {
        super(ReglaAlfa.class, "dao/ReglaAlfa.dao");
    }
}
