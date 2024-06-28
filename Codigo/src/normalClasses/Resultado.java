package normalClasses;
import java.util.*;
import java.time.LocalDateTime;
import enums.Estado;

public class Resultado {
    private String comentarios;
    private LocalDateTime fechaCarga;
    private Estado estadoResultado;
    private String resultado;
    private Practica practica;

    // CONSTRUCTOR DEL RESULTADO


    public Resultado(String comentarios, LocalDateTime fechaCarga, Estado estadoResultado, String resultado, Practica practica) {
        this.comentarios = comentarios;
        this.fechaCarga = fechaCarga;
        this.estadoResultado = estadoResultado;
        this.resultado = resultado;
        this.practica = practica;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDateTime getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(LocalDateTime fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Estado getEstadoResultado() {
        return estadoResultado;
    }

    public void setEstadoResultado(Estado estadoResultado) {
        this.estadoResultado = estadoResultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Practica getPractica() {
        return practica;
    }

    public void setPractica(Practica practica) {
        this.practica = practica;
    }
}
