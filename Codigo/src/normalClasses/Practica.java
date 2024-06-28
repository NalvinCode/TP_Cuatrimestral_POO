package normalClasses;

import java.util.List;

public class Practica {
    private int codigo;
    private String name;
    private int cantidadHoras;
    private boolean inhabilitado;
    private List<ReglaPractica> reglas;  // Lista de reglas asociadas con la práctica

    // Constructor de la práctica
    public Practica(int codigo, String name, int cantidadHoras, boolean inhabilitado, List<ReglaPractica> reglas) {
        this.codigo = codigo;
        this.name = name;
        this.cantidadHoras = cantidadHoras;
        this.inhabilitado = inhabilitado;
        this.reglas = reglas;
    }

    // Getters y setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCantidadHoras() {
        return cantidadHoras;
    }

    public void setCantidadHoras(int cantidadHoras) {
        this.cantidadHoras = cantidadHoras;
    }

    public boolean isInhabilitado() {
        return inhabilitado;
    }

    public void setInhabilitado(boolean inhabilitado) {
        this.inhabilitado = inhabilitado;
    }

    public List<ReglaPractica> getReglas() {
        return reglas;
    }

    public void setReglas(List<ReglaPractica> reglas) {
        this.reglas = reglas;
    }

}
