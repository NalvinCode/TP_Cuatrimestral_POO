package com.UADE.dao;

import com.UADE.model.ReglaNumerica;

public class ReglaNumericaDAO extends GenericDAO<ReglaNumerica> {
    public ReglaNumericaDAO() throws Exception {
        super(ReglaNumerica.class, "dao/ReglaNumerica.dao");
    }
}
