package normalClasses;
import java.util.*;
import java.time.LocalDateTime;
import enums.Estado;

public class Resultado {
    private String comentarios;
    private LocalDateTime FechaCarga;
    private Estado EstadoResultado;
    private String Resultado;

    // CONSTRUCTOR DEL RESULTADO


    public Resultado(String comentarios, LocalDateTime fechaCarga, Estado estadoResultado, String resultado) {
        this.comentarios = comentarios;
        FechaCarga = fechaCarga;
        EstadoResultado = estadoResultado;
        Resultado = resultado;
    }

    // GETTERS Y SETTERS DEL RESULTADO

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDateTime getFechaCarga() {
        return FechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        FechaCarga = fechaCarga;
    }

    public Estado getEstadoResultado() {
        return EstadoResultado;
    }

    public void setEstadoResultado(Estado estadoResultado) {
        EstadoResultado = estadoResultado;
    }

    public String getResultado() {
        return Resultado;
    }

    public void setResultado(String resultado) {
        Resultado = resultado;
    }
}
