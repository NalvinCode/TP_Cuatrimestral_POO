package normalClasses;
import enums.ObraSocial;
import java.util.*;
import enums.Estado;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Peticion {
    private ObraSocial obraSocial;
    private LocalDateTime FechaCarga;
    private LocalDateTime FechaEntrega;
    private Estado estado;
    private ArrayList<Resultado> resultados;

    // CONSTRUCTOR DE UNA PETICION

    public Peticion(ObraSocial obraSocial, LocalDateTime fechaCarga, LocalDateTime fechaEntrega, Estado estado, ArrayList<Resultado> resultados) {
        this.obraSocial = obraSocial;
        FechaCarga = fechaCarga;
        FechaEntrega = fechaEntrega;
        this.estado = estado;
        this.resultados = resultados;
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
}
