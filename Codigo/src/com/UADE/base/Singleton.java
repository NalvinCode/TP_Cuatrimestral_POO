package com.UADE.base;

import com.UADE.controller.*;
import com.UADE.enums.RolSistema;

public final class Singleton {
    private static Singleton instance;
    public PacienteController pacienteController = new PacienteController();
    public PeticionController peticionController = new PeticionController();
    public PracticaController practicaController = new PracticaController();
    public SucursalController sucursalController = new SucursalController();
    public UsuarioController usuarioController = new UsuarioController();
    public Integer codigoUsuario;
    public String nombreUsuario;
    public RolSistema rolSistema;

    private Singleton() throws Exception {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static Singleton getInstance() throws Exception {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}