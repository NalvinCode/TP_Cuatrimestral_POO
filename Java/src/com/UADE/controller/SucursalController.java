package com.UADE.controller;

import com.UADE.dao.PeticionDAO;
import com.UADE.dao.SucursalDAO;
import com.UADE.dao.UsuarioDAO;
import com.UADE.dto.SucursalDTO;
import com.UADE.dto.UsuarioDTO;
import com.UADE.enums.EstadoPeticion;
import com.UADE.model.Paciente;
import com.UADE.model.Peticion;
import com.UADE.model.Sucursal;
import com.UADE.model.Usuario;

import java.util.ArrayList;
import java.util.List;

public class SucursalController {
    private final SucursalDAO DAO_Sucursal;
    private final List<Sucursal> sucursales;

    public SucursalController() throws Exception {
        DAO_Sucursal = new SucursalDAO();
        sucursales = DAO_Sucursal.getAll();
    }

    private Integer getNuevoCodigoSucursal() {
        if (sucursales.size() > 0) {
            return sucursales.get(sucursales.size() - 1).getCodigo() + 1;
        } else {
            return 1;
        }
    }

    public Integer nuevaSucursal(SucursalDTO sucdto) throws Exception {
        Sucursal sucursal = new Sucursal(this.getNuevoCodigoSucursal(), sucdto.getDireccion(), sucdto.getTelefono(), sucdto.getCodUsuarioRespTecnico());
        sucursales.add(sucursal);

        DAO_Sucursal.saveAll(sucursales);

        return sucursal.getCodigo();
    }

    public SucursalDTO obtenerDatosSucursal(Integer codigo) {
        SucursalDTO sucdto = null;

        for (Sucursal i : this.sucursales) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                sucdto = new SucursalDTO(i.getCodigo(), i.getDireccion(), i.getTelefono(), i.getCodUsuarioRespTecnico());
                break;
            }
        }

        return sucdto;
    }

    public boolean eliminarSucursal(Integer codigo) throws Exception {
        List<Peticion> peticiones = new PeticionDAO().getAll();

        for (Peticion i : peticiones) { // Regla de negocio
            if (i.getCodSucursal().intValue() == codigo.intValue()) {
                if (i.getEstadoPeticion() == EstadoPeticion.FINALIZADO) {
                    return false;
                }
            }
        }

        for (Sucursal i : this.sucursales) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                sucursales.remove(i);
                break;
            }
        }

        DAO_Sucursal.saveAll(sucursales);

        return true;
    }

    public boolean sucursalTienePeticionesActivas(Integer codigo) throws Exception {
        List<Peticion> peticiones = new PeticionDAO().getAll();

        for (Peticion i : peticiones) { // Regla de negocio
            if (i.getCodSucursal().intValue() == codigo.intValue()) {
                if (i.getEstadoPeticion() != EstadoPeticion.FINALIZADO) {
                    return true;
                }
            }
        }

        return false;
    }

    public void actualizarSucursal(SucursalDTO sucdto) throws Exception {
        for (Sucursal i : this.sucursales) {
            if (sucdto.getCodigo().intValue() == i.getCodigo().intValue()) {
                i.setDireccion(sucdto.getDireccion());
                i.setTelefono(sucdto.getTelefono());
                i.setCodUsuarioRespTecnico(sucdto.getCodUsuarioRespTecnico());
                DAO_Sucursal.saveAll(sucursales);
                break;
            }
        }
    }

    public List<SucursalDTO> obtenerListaSucursales() {
        List<SucursalDTO> suclist = new ArrayList<SucursalDTO>();

        for (Sucursal i : sucursales) {
            suclist.add(new SucursalDTO(i.getCodigo(), i.getDireccion(), i.getTelefono(), i.getCodUsuarioRespTecnico()));
        }

        return suclist;
    }
}
