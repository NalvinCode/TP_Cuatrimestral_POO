package com.UADE.dao;

import com.UADE.model.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> {
    public UsuarioDAO() throws Exception {
        super(Usuario.class, "dao/Usuario.dao");
    }
}
