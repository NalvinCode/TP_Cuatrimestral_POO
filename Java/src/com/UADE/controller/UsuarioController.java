package com.UADE.controller;

import com.UADE.dao.SucursalDAO;
import com.UADE.dao.UsuarioDAO;
import com.UADE.dto.UsuarioDTO;
import com.UADE.enums.RolSistema;
import com.UADE.model.Sucursal;
import com.UADE.model.Usuario;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsuarioController {
    private final UsuarioDAO DAO_Usuario;
    private final List<Usuario> usuarios;

    public UsuarioController() throws Exception {
        DAO_Usuario = new UsuarioDAO();
        usuarios = DAO_Usuario.getAll();

        this.nuevoUsuario(new UsuarioDTO(1, "admin", "1234", "uade@uade.edu.ar", "administrador", "lima 1", 10444322, new Date(), RolSistema.ADMINISTRADOR));
    }

    private Integer getNuevoCodigo() {
        if (usuarios.size() > 0) {
            return usuarios.get(usuarios.size() - 1).getCodigo() + 1;
        } else {
            return 1;
        }
    }

    public Integer nuevoUsuario(UsuarioDTO nuevo) throws Exception {
        if (buscarUsuarioPorNombreUsuario(nuevo.getNombreUsuario()) == null && buscarUsuarioPorDNI(nuevo.getDni()) == null) {
            Usuario u = new Usuario(this.getNuevoCodigo(), nuevo.getNombreUsuario(), nuevo.getPassword(), nuevo.getEmail(), nuevo.getNombreCompleto(), nuevo.getDomicilio(), nuevo.getDni(), nuevo.getFechaDeNacimiento(), nuevo.getRolSistema());
            this.usuarios.add(u);
            DAO_Usuario.saveAll(usuarios);
            return u.getCodigo();
        } else {
            return null;
        }
    }

    public Boolean actualizarUsuario(UsuarioDTO usu) throws Exception {
        Usuario u = null;

        for (Usuario i : this.usuarios) {
            if (usu.getCodigo().intValue() == i.getCodigo().intValue()) {
                u = i;
                break;
            }
        }

        if (u != null) {
            if (usu.getPassword().length() > 0) { // Solo guardo la clave si fue escrita una nueva
                u.setPassword(usu.getPassword());
            }

            u.setEmail(usu.getEmail());
            u.setNombreCompleto(usu.getNombreCompleto());
            u.setDomicilio(usu.getDomicilio());
            u.setDni(usu.getDni());
            u.setFechaDeNacimiento(usu.getFechaDeNacimiento());
            u.setRolSistema(usu.getRolSistema());

            DAO_Usuario.saveAll(usuarios);

            return true;
        } else {
            return false;
        }
    }

    public UsuarioDTO buscarUsuarioPorNombreUsuario(String nombreUsuario) {
        UsuarioDTO udto = null;

        for (Usuario i : this.usuarios) {
            if (nombreUsuario.compareToIgnoreCase(i.getNombreUsuario()) == 0) {
                udto = new UsuarioDTO(i.getCodigo(), i.getNombreUsuario(), i.getPassword(), i.getEmail(), i.getNombreCompleto(), i.getDomicilio(), i.getDni(), i.getFechaDeNacimiento(), i.getRolSistema());
                break;
            }
        }

        return udto;
    }

    public UsuarioDTO buscarUsuarioPorDNI(Integer dni) {
        UsuarioDTO udto = null;

        for (Usuario i : this.usuarios) {
            if (dni.intValue() == i.getDni().intValue()) {
                udto = new UsuarioDTO(i.getCodigo(), i.getNombreUsuario(), i.getPassword(), i.getEmail(), i.getNombreCompleto(), i.getDomicilio(), i.getDni(), i.getFechaDeNacimiento(), i.getRolSistema());
                break;
            }
        }

        return udto;
    }

    public UsuarioDTO buscarUsuarioPorCodigo(Integer codigo) {
        UsuarioDTO udto = null;

        for (Usuario i : this.usuarios) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                udto = new UsuarioDTO(i.getCodigo(), i.getNombreUsuario(), i.getPassword(), i.getEmail(), i.getNombreCompleto(), i.getDomicilio(), i.getDni(), i.getFechaDeNacimiento(), i.getRolSistema());
                break;
            }
        }

        return udto;
    }

    public UsuarioDTO buscarUsuarioPorCredenciales(String nombreUsuario, String clave) {
        UsuarioDTO udto = null;

        for (Usuario i : this.usuarios) {
            if (nombreUsuario.compareToIgnoreCase(i.getNombreUsuario()) == 0 && clave.compareToIgnoreCase(i.getPassword()) == 0) {
                udto = new UsuarioDTO(i.getCodigo(), i.getNombreUsuario(), i.getPassword(), i.getEmail(), i.getNombreCompleto(), i.getDomicilio(), i.getDni(), i.getFechaDeNacimiento(), i.getRolSistema());
                break;
            }
        }

        return udto;
    }

    public List<UsuarioDTO> obtenerListaUsuarios() {
        List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();

        for (Usuario i : usuarios) {
            lista.add(new UsuarioDTO(i.getCodigo(), i.getNombreUsuario(), i.getPassword(), i.getEmail(), i.getNombreCompleto(), i.getDomicilio(), i.getDni(), i.getFechaDeNacimiento(), i.getRolSistema()));
        }

        return lista;
    }

    public void borrarUsuario(Integer codigo) throws Exception {
        for (Usuario i : this.usuarios) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                this.usuarios.remove(i);
                DAO_Usuario.saveAll(usuarios);
                break;
            }
        }
    }
}

