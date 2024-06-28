package normalClasses;
import enums.ObraSocial;
import java.util.*;
import enums.Estado;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Peticion {
    private int idPeticion;
    private ObraSocial obraSocial;
    private LocalDateTime FechaCarga;
    private LocalDateTime FechaEntrega;
    private Estado estado;
    private ArrayList<Resultado> resultados;
    private List<Practica> practica;
    private Paciente paciente;

    // CONSTRUCTOR DE UNA PETICION

    public Peticion(ObraSocial obraSocial, int idPeticion, List<Practica> practica, LocalDateTime fechaCarga, LocalDateTime fechaEntrega, Estado estado, ArrayList<Resultado> resultados) {
        this.idPeticion = idPeticion;
        this.obraSocial = obraSocial;
        FechaCarga = fechaCarga;
        FechaEntrega = fechaEntrega;
        this.estado = estado;
        this.resultados = resultados;
        this.practica = practica;
    }

    // GETTERS Y SETTERS DE UNA PETICION


    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    public LocalDateTime getFechaCarga() {
        return FechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        FechaCarga = fechaCarga;
    }

    public LocalDateTime getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        FechaEntrega = fechaEntrega;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public ArrayList<Resultado> getResultados() {
        return resultados;
    }

    public void setResultados(ArrayList<Resultado> resultados) {
        this.resultados = resultados;
    }

    public int getIdPeticion() {
        return idPeticion;
    }

    public List<Practica> getPracticas() {
        return practica;
    }

    @Override
    public String toString() {
        return "Peticion{" +
                "idPeticion=" + idPeticion +
                ", obraSocial=" + obraSocial +
                ", FechaCarga=" + FechaCarga +
                ", FechaEntrega=" + FechaEntrega +
                ", estado=" + estado +
                ", resultados=" + resultados +
                ", practica=" + practica +
                '}';
    }

    public void setPractica(List<Practica> practica) {
        this.practica = practica;
    }

    public void setIdPeticion(int idPeticion) {
        this.idPeticion = idPeticion;
    }
}