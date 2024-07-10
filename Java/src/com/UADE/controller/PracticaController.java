package com.UADE.controller;

import com.UADE.dao.PeticionDAO;
import com.UADE.dao.PracticaDAO;
import com.UADE.dao.ReglaAlfaDAO;
import com.UADE.dao.ReglaNumericaDAO;
import com.UADE.dto.*;
import com.UADE.enums.Criterio;
import com.UADE.model.*;

import java.util.ArrayList;
import java.util.List;

public class PracticaController {
    private final PracticaDAO DAO_Practica;
    private final ReglaAlfaDAO DAO_ReglaAlfa;
    private final ReglaNumericaDAO DAO_ReglaNumerica;
    private final PeticionDAO DAO_Peticion;
    private final List<Practica> practicas;
    private final List<Peticion> peticiones;
    private final List<ReglaAlfa> reglasA;
    private final List<ReglaNumerica> reglasN;
    private final List<ReglaPractica> reglasP = new ArrayList<>();

    public PracticaController() throws Exception {
        DAO_Practica = new PracticaDAO();
        DAO_ReglaAlfa = new ReglaAlfaDAO();
        DAO_ReglaNumerica = new ReglaNumericaDAO();
        DAO_Peticion = new PeticionDAO();
        practicas = DAO_Practica.getAll();
        peticiones = DAO_Peticion.getAll();
        reglasA = DAO_ReglaAlfa.getAll();
        reglasN = DAO_ReglaNumerica.getAll();
        reglasP.addAll(reglasA);
        reglasP.addAll(reglasN);
    }

    private Integer getNuevoCodigoPractica() {
        if (practicas.size() > 0) {
            return practicas.get(practicas.size() - 1).getCodigo() + 1;
        } else {
            return 1;
        }
    }

    private Integer getNuevoCodigoReglaP() {
        if (reglasP.size() > 0) {
            return reglasP.get(reglasP.size() - 1).getCodigo() + 1;
        } else {
            return 1;
        }
    }

    public Integer nuevaPractica(PracticaDTO pracdto) throws Exception {
        Practica practica = new Practica(this.getNuevoCodigoPractica(), pracdto.getNombre(), pracdto.getTiempoEstimado(), pracdto.getCodSubPracticas(), new ArrayList<>());
        practicas.add(practica);

        DAO_Practica.saveAll(practicas);

        return practica.getCodigo();
    }

    public Integer nuevaReglaP (ReglaPracticaDTO reglaPDTO, PracticaDTO practicaDTO) throws Exception {
        ReglaPractica reglaP = null;

        if(reglaPDTO instanceof ReglaAlfaDTO){
            ReglaAlfaDTO reglaADTO = (ReglaAlfaDTO) reglaPDTO;
            reglaP = new ReglaAlfa(this.getNuevoCodigoReglaP(),reglaADTO.getValorCritico(), reglaADTO.getValorReservado(), reglaADTO.getCriterio());
            reglasA.add((ReglaAlfa) reglaP);
            DAO_ReglaAlfa.saveAll(reglasA);
        }
        if(reglaPDTO instanceof ReglaNumericaDTO){
            ReglaNumericaDTO reglaADTO = (ReglaNumericaDTO) reglaPDTO;
            reglaP = new ReglaNumerica(this.getNuevoCodigoReglaP(),reglaADTO.getValorCritico(), reglaADTO.getValorReservado(), reglaADTO.getCriterio());
            reglasN.add((ReglaNumerica) reglaP);
            DAO_ReglaNumerica.saveAll(reglasN);
        }

        reglasP.add(reglaP);

        List<Integer> newReglas = practicaDTO.getReglasPractica();
        newReglas.add(reglaP.getCodigo());
        practicaDTO.setReglasPractica(newReglas);
        actualizarPractica(practicaDTO);

        return reglaP.getCodigo();
    }



    public PracticaDTO obtenerDatosPractica(Integer codigo) {
        PracticaDTO practdto = null;

        for (Practica i : this.practicas) {
            if (codigo.intValue() == i.getCodigo().intValue()) {
                List<Integer> reglasPracticaDTO = new ArrayList<>();
                for(Integer codReglaP : i.getReglasPractica()){
                    reglasPracticaDTO.add(codReglaP);
                }
                practdto = new PracticaDTO(i.getCodigo(), i.getNombre(), i.getTiempoEstimado(), i.getCodSubPracticas(), reglasPracticaDTO);
                break;
            }
        }

        return practdto;
    }

    public ReglaPracticaDTO obtenerDatosRegla(Integer codigo) {
        ReglaPracticaDTO reglaDTO = null;

        for (ReglaPractica reglaP : this.reglasP) {
            if (codigo.intValue() == reglaP.getCodigo().intValue()) {
                reglaDTO = reglaPToDTO(reglaP);
                break;
            }
        }
        return reglaDTO;
    }

    public void actualizarPractica(PracticaDTO pracdto) throws Exception {
        for (Practica i : this.practicas) {
            if (pracdto.getCodigo().intValue() == i.getCodigo().intValue()) {
                i.setNombre(pracdto.getNombre());
                i.setTiempoEstimado(pracdto.getTiempoEstimado());
                i.setCodSubPracticas(pracdto.getCodSubPracticas());
                i.setCodReglasPractica(pracdto.getReglasPractica());
                DAO_Practica.saveAll(practicas);
                break;
            }
        }
    }

    public void actualizarRegla(ReglaPracticaDTO reglaP) throws Exception {
        if(reglaP instanceof ReglaAlfaDTO){
            for (ReglaAlfa reglaA : reglasA){
                if(reglaA.getCodigo().intValue() == reglaP.getCodigo().intValue()){
                    reglaA.setCriterio(reglaP.getCriterio());
                    reglaA.setValorCritico(((ReglaAlfaDTO) reglaP).getValorCritico());
                    reglaA.setValorReservado(((ReglaAlfaDTO) reglaP).getValorReservado());
                    DAO_ReglaAlfa.saveAll(reglasA);
                }
                break;
            }
        }
        if(reglaP instanceof ReglaNumericaDTO){
            for (ReglaNumerica reglaN : reglasN){
                if(reglaN.getCodigo().intValue() == reglaP.getCodigo().intValue()){
                    reglaN.setCriterio(reglaP.getCriterio());
                    reglaN.setValorCritico(((ReglaNumericaDTO) reglaP).getValorCritico());
                    reglaN.setValorReservado(((ReglaNumericaDTO) reglaP).getValorReservado());
                    DAO_ReglaNumerica.saveAll(reglasN);
                }
                break;
            }
        }
    }

    public List<PracticaDTO> obtenerListaPracticas() {
        List<PracticaDTO> listaPracticas = new ArrayList<>();

        for (Practica i : this.practicas) {
            List<Integer> reglasPracticaDTO = new ArrayList<>();
            for(Integer reglaP : i.getReglasPractica()){
                reglasPracticaDTO.add(reglaP);
            }
            listaPracticas.add(new PracticaDTO(i.getCodigo(), i.getNombre(), i.getTiempoEstimado(), i.getCodSubPracticas(), reglasPracticaDTO));
        }

        return listaPracticas;
    }

    public List<ListaPracticasDTO> obtenerListaPracticasSimplificada() {
        List<ListaPracticasDTO> listaPracticas = new ArrayList<>();

        for (Practica i : this.practicas) {
            listaPracticas.add(new ListaPracticasDTO(i.getCodigo(), i.getNombre()));
        }

        return listaPracticas;
    }

    public ReglaPracticaDTO reglaPToDTO(ReglaPractica reglaP){
        if(reglaP instanceof ReglaAlfa){
            ReglaAlfa reglaA = (ReglaAlfa) reglaP;
            return new ReglaAlfaDTO(reglaP.getCodigo(), reglaA.getValorCritico(), reglaA.getValorReservado(), reglaA.getCriterio());
        }
        if(reglaP instanceof ReglaNumerica){
            ReglaNumerica reglaN = (ReglaNumerica) reglaP;
            return new ReglaNumericaDTO(reglaP.getCodigo(), reglaN.getValorCritico(), reglaN.getValorReservado(), reglaN.getCriterio());
        }
        return null;
    }

    public void agregarPracticaASubPractica(Integer codSubpractica, Integer codPractica) throws Exception {
        for (Practica i : this.practicas) {
            if (codPractica.intValue() == i.getCodigo().intValue()) {

                List<Integer> subpracticas = i.getCodSubPracticas();

                if (!subpracticas.contains(codSubpractica)) {
                    subpracticas.add(codSubpractica);
                }

                i.setCodSubPracticas(subpracticas);

                break;
            }
        }
        DAO_Practica.saveAll(practicas);
    }

    public boolean eliminarPractica(Integer codigo) throws Exception {
        Practica pracABorrar = null;
        for (Practica i : this.practicas) {
            if(i.getCodigo().intValue() == codigo.intValue()){
                pracABorrar = i;
                for (Peticion pet : this.peticiones){
                    if(pet.getCodPracticas().contains(codigo.intValue())){
                        pracABorrar = null;
                        break;
                    }
                }
                break;
            }
        }
        if (pracABorrar == null) {
            return false;
        } else {
            for(Integer rp : pracABorrar.getReglasPractica()){
                eliminarRegla(rp);
            }
            practicas.remove(pracABorrar);
            DAO_Practica.saveAll(practicas);
            return true;
        }
    }

    public boolean eliminarRegla(Integer codigo) throws Exception{
        if(codigo == null){
            return false;
        }
        ReglaPractica reglaP = null;
        for(ReglaPractica rp : this.reglasP){
            if(rp.getCodigo().intValue() == codigo.intValue()){
                reglaP = rp;
                for(Practica p : this.practicas){
                    if(p.getReglasPractica().contains(codigo)){
                        List<Integer> newReglasP = p.getReglasPractica().stream().filter(r -> r != codigo).toList();
                        actualizarPractica(new PracticaDTO(p.getCodigo(), p.getNombre(),p.getTiempoEstimado(), p.getCodSubPracticas(), newReglasP));
                        break;
                    }
                }
                break;
            }
        }

        reglasP.remove(reglaP);

        if(reglaP instanceof ReglaAlfa){
            reglasA.remove(reglaP);
            DAO_ReglaAlfa.saveAll(reglasA);
        }

        if(reglaP instanceof ReglaNumerica){
            reglasN.remove(reglaP);
            DAO_ReglaNumerica.saveAll(reglasN);
        }

        return true;
    }

    public void retirarPracticaDeSubPractica(Integer codSubpractica, Integer codPractica) throws Exception {
        for (Practica i : this.practicas) {
            if (codPractica.intValue() == i.getCodigo().intValue()) {

                List<Integer> subpracticas = i.getCodSubPracticas();

                subpracticas.remove(codSubpractica);

                i.setCodSubPracticas(subpracticas);

                break;
            }
        }
        DAO_Practica.saveAll(practicas);
    }

    public boolean resultadoCritico(Integer codigoP, ResultadoPracticaDTO resultado){
        boolean resultadoC = false;
        for(ReglaPractica reglaP : reglasP){
            if(reglaP.getCodigo().intValue() == codigoP.intValue()){

                if(reglaP instanceof ReglaAlfa){
                    if(reglaP.getCriterio() == Criterio.IGUAL){
                        resultadoC = ((ReglaAlfa) reglaP).getValorCritico().equals(resultado.getResultadoLiteral());
                    }
                    if(reglaP.getCriterio() == Criterio.DISTINTO){
                        resultadoC = !((ReglaAlfa) reglaP).getValorCritico().equals(resultado.getResultadoLiteral());
                    }
                }

                if(reglaP instanceof ReglaNumerica){
                    if(reglaP.getCriterio() == Criterio.IGUAL){
                        resultadoC = ((ReglaNumerica) reglaP).getValorCritico().equals(resultado.getResultadoNumerico());
                    }
                    if(reglaP.getCriterio() == Criterio.MAYOR){
                        resultadoC = ((ReglaNumerica) reglaP).getValorCritico() < resultado.getResultadoNumerico();
                    }
                    if(reglaP.getCriterio() == Criterio.MENOR){
                        resultadoC = ((ReglaNumerica) reglaP).getValorCritico() > resultado.getResultadoNumerico();
                    }
                }

                break;
            }
        }
        return resultadoC;
    }
}
