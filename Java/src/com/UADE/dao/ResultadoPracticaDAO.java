package com.UADE.dao;

import com.UADE.model.ResultadoPractica;

public class ResultadoPracticaDAO extends GenericDAO<ResultadoPractica> {
    public ResultadoPracticaDAO() throws Exception {
        super(ResultadoPractica.class, "dao/ResultadoPractica.dao");
    }
}

