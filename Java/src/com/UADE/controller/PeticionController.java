package com.UADE.controller;

import com.UADE.dao.PeticionDAO;
import com.UADE.dao.PracticaDAO;
import com.UADE.dao.ResultadoPracticaDAO;
import com.UADE.dto.PeticionDTO;
import com.UADE.dto.PracticaDTO;
import com.UADE.dto.ResultadoPracticaDTO;
import com.UADE.enums.EstadoPeticion;
import com.UADE.enums.EstadoResultado;
import com.UADE.model.Peticion;
import com.UADE.model.Practica;
import com.UADE.model.ResultadoPractica;

import java.util.ArrayList;
import java.util.List;

public class PeticionController {
    private final PeticionDAO DAO_Peticion;
    private final ResultadoPracticaDAO DAO_ResultadoPractica;
    private final List<Peticion> peticiones;
    private final List<ResultadoPractica> resultadosPracticas;

    public PeticionController() throws Exception {
        DAO_Peticion = new PeticionDAO();
        peticiones = DAO_Peticion.getAll();

        DAO_ResultadoPractica = new ResultadoPracticaDAO();
        resultadosPracticas = DAO_ResultadoPractica.getAll();
    }

    private Integer getNuevoCodigoPeticion() {
        if (peticiones.size() > 0) {
            return peticiones.get(peticiones.size() - 1).getCodigo() + 1;
        } else {
            return 1;
        }
    }

    private Integer getNuevoCodigoResultadoPractica() {
        if (resultadosPracticas.size() > 0) {
            return resultadosPracticas.get(resultadosPracticas.size() - 1).getCodigo() + 1;
        } else {
            return 1;
        }
    }

    public Integer nuevaPeticion(PeticionDTO petdto) throws Exception {
        List<Practica> practicas = new PracticaDAO().getAll();
        List<Integer> practicasfinal = new ArrayList<>();

        for (Integer cod : petdto.getCodPracticas()) { // Manejo de subpracticas / grupos de practicas
            for (Practica i : practicas) {
                if (i.getCodigo().intValue() == cod.intValue()) {
                    if (i.getCodSubPracticas().size() > 0) {
                        practicasfinal.addAll(i.getCodSubPracticas());
                    } else {
                        practicasfinal.add(i.getCodigo());
                    }
                }
            }
        }

        Peticion peticion = new Peticion(this.getNuevoCodigoPeticion(), petdto.getObraSocial(), petdto.getFechaInicio(), petdto.getEstadoPeticion(), petdto.getCodPaciente(), petdto.getCodSucursal(), practicasfinal);
        peticiones.add(peticion);

        DAO_Peticion.saveAll(peticiones);

        return peticion.getCodigo();
    }

    public PeticionDTO obtenerDatosPeticion(Integer codigo) {
        PeticionDTO petdto = null;

        for (Peticion i : this.peticiones) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                petdto = new PeticionDTO(i.getCodigo(), i.getObraSocial(), i.getFechaInicio(), i.getEstadoPeticion(), i.getCodPaciente(), i.getCodSucursal(), i.getCodPracticas());
                break;
            }
        }

        return petdto;
    }

    public List<ResultadoPracticaDTO> obtenerResultadosPeticion(Integer codigo) {
        List<ResultadoPracticaDTO> rp = new ArrayList<>();

        for (ResultadoPractica i : this.resultadosPracticas) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                rp.add(new ResultadoPracticaDTO(i.getCodigo(), i.getCodPractica(), i.getCodPeticion(), i.getResultadoNumerico(), i.getResultadoLiteral(), i.getResultadoLiteral(), i.getEstado()));
            }
        }

        return rp;
    }

    public ResultadoPracticaDTO obtenerResultadoPracticaPorCodigo(Integer codigoResultado) {
        ResultadoPracticaDTO res = null;

        for (ResultadoPractica i : this.resultadosPracticas) {
            if (codigoResultado.intValue() == i.getCodigo().intValue()) {
                res = new ResultadoPracticaDTO(i.getCodigo(), i.getCodPractica(), i.getCodPeticion(), i.getResultadoNumerico(), i.getResultadoLiteral(), i.getResultadoLiteral(), i.getEstado());
                break;
            }
        }

        return res;
    }

    public Boolean nuevoResultadoPractica(ResultadoPracticaDTO result) throws Exception {
        Peticion pet = null;

        for (Peticion i : this.peticiones) {
            if (result.getCodPeticion().intValue() == i.getCodigo().intValue()) {
                pet = i;
                break;
            }
        }

        if (pet.getEstadoPeticion() == EstadoPeticion.FINALIZADO) {
            return false;
        }

        resultadosPracticas.add(new ResultadoPractica(this.getNuevoCodigoResultadoPractica(), result.getCodPractica(), result.getCodPeticion(), result.getResultadoNumerico(), result.getResultadoLiteral(), result.getTranscription(), result.getEstado()));

        DAO_ResultadoPractica.saveAll(resultadosPracticas);

        boolean failed = false;
        List<ResultadoPracticaDTO> resultados = this.obtenerResultadosPeticion(pet.getCodigo());

        for (ResultadoPracticaDTO r : resultados) {
            if (r.getEstado() != EstadoResultado.FINALIZADO) {
                failed = true;
            }
        }

        if (pet.getCodPracticas().size() == resultados.size() && !failed) {
            pet.setEstadoPeticion(EstadoPeticion.FINALIZADO);
        } else {
            pet.setEstadoPeticion(EstadoPeticion.ENPROCESO);
        }

        DAO_Peticion.saveAll(this.peticiones);

        return true;
    }

    public boolean eliminarPeticion(Integer codigo) throws Exception {
        Peticion petABorrar = null;

        for (Peticion i : this.peticiones) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                if (i.getEstadoPeticion() == EstadoPeticion.INICIO) {
                    petABorrar = i;
                }
                break;
            }
        }

        if (petABorrar == null) {
            return false;
        } else {
            peticiones.remove(petABorrar);
            DAO_Peticion.saveAll(peticiones);
            return true;
        }
    }


    public void actualizarPeticion(PeticionDTO petdto) throws Exception {
        for (Peticion i : this.peticiones) {
            if (petdto.getCodigo().intValue() == i.getCodigo().intValue()) {
                i.setObraSocial(petdto.getObraSocial());
                i.setFechaInicio(petdto.getFechaInicio());
                i.setEstadoPeticion(petdto.getEstadoPeticion());
                i.setCodPaciente(petdto.getCodPaciente());
                i.setCodPracticas(petdto.getCodPracticas());
                i.setCodSucursal(petdto.getCodSucursal());
                DAO_Peticion.saveAll(peticiones);
                break;
            }
        }
    }

    public List<PeticionDTO> obtenerListaPeticiones() {
        List<PeticionDTO> petlist = new ArrayList<>();

        for (Peticion i : peticiones) {
            petlist.add(new PeticionDTO(i.getCodigo(), i.getObraSocial(), i.getFechaInicio(), i.getEstadoPeticion(), i.getCodPaciente(), i.getCodSucursal(), i.getCodPracticas()));
        }

        return petlist;
    }

    public void migrarSucursalPeticiones(Integer oldSucursal, Integer newSucursal) throws Exception {
        for (Peticion i : peticiones) { // Regla de negocio
            if (i.getCodSucursal().intValue() == oldSucursal.intValue()) {
                i.setCodSucursal(newSucursal);
            }
        }

        DAO_Peticion.saveAll(peticiones);
    }

}
