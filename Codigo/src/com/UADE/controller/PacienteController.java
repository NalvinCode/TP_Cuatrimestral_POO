package com.UADE.controller;

import com.UADE.dao.PacienteDAO;
import com.UADE.dao.PeticionDAO;
import com.UADE.dto.PacienteDTO;
import com.UADE.enums.EstadoPeticion;
import com.UADE.model.Paciente;
import com.UADE.model.Peticion;

import java.util.ArrayList;
import java.util.List;

public class PacienteController {
    private final PacienteDAO DAO_Paciente;
    private final List<Paciente> pacientes;

    public PacienteController() throws Exception {
        DAO_Paciente = new PacienteDAO();
        pacientes = DAO_Paciente.getAll();
    }

    private Integer getNuevoCodigoPaciente() {
        if (pacientes.size() > 0) {
            return pacientes.get(pacientes.size() - 1).getCodigo() + 1;
        } else {
            return 1;
        }
    }

    public Integer nuevoPaciente(PacienteDTO pac) throws Exception {
        if (buscarPacientePorDNI(pac.getDni()) == null) {
            Paciente p = new Paciente(this.getNuevoCodigoPaciente(), pac.getDni(), pac.getNombreCompleto(), pac.getDomicilio(), pac.getEmail(), pac.getSexo(), pac.getEdad());
            this.pacientes.add(p);
            DAO_Paciente.saveAll(pacientes);
            return p.getCodigo();
        } else {
            return null;
        }
    }

    public Boolean actualizarPaciente(PacienteDTO pac) throws Exception {

        Paciente p = null;

        for (Paciente i : this.pacientes) {
            if (i.getCodigo().intValue() == pac.getCodigo().intValue()) {
                p = i;
                break;
            }
        }

        if (buscarPacientePorDNI(pac.getDni()) != null && pac.getCodigo() != p.getCodigo()){
            return false;
        }

        if (p != null) {
            p.setDni(pac.getDni());
            p.setNombreCompleto(pac.getNombreCompleto());
            p.setDomicilio(pac.getDomicilio());
            p.setEmail(pac.getEmail());
            p.setSexo(pac.getSexo());
            p.setEdad(pac.getEdad());
        } else {
            return false;
        }

        DAO_Paciente.saveAll(pacientes);

        return true;
    }

    public void borrarPaciente(Integer codigo) throws Exception {
        List<Peticion> peticiones = new PeticionDAO().getAll();

        for (Peticion i : peticiones) { // Regla de negocio
            if (i.getCodPaciente().intValue() == codigo.intValue()) {
                if (i.getEstadoPeticion() == EstadoPeticion.FINALIZADO) { // Verificacion de estado
                    return;
                }
            }
        }

        for (Paciente i : this.pacientes) {
            if (i.getCodigo().intValue() == codigo.intValue()) {
                pacientes.remove(i);
                break;
            }
        }

        DAO_Paciente.saveAll(pacientes);
    }

    public Integer buscarPacientePorDNI(Integer dni) {
        for (Paciente i : this.pacientes) {
            if (dni.intValue() == i.getDni().intValue()) {
                return i.getCodigo();
            }
        }

        return null;
    }

    public PacienteDTO obtenerPaciente(Integer codigo) {
        PacienteDTO pac = null;

        for (Paciente i : this.pacientes) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                pac = new PacienteDTO(i.getCodigo(), i.getDni(), i.getNombreCompleto(), i.getDomicilio(), i.getEmail(), i.getSexo(), i.getEdad());
                break;
            }
        }

        return pac;
    }

    public List<PacienteDTO> obtenerListaPacientes() throws Exception {
        List<PacienteDTO> lista = new ArrayList<PacienteDTO>();

        for (Paciente i : pacientes) {
            lista.add(new PacienteDTO(i.getCodigo(), i.getDni(), i.getNombreCompleto(), i.getDomicilio(), i.getEmail(), i.getSexo(), i.getEdad()));
        }

        return lista;
    }

}