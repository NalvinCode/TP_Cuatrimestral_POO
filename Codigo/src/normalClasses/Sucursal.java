package normalClasses;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
    private long numero;
    private String direccion;
    private Usuario responsable;
    private List<Peticion> peticiones; // Lista para almacenar peticiones

    // Constructor de la sucursal
    public Sucursal(long numero, Usuario responsable, String direccion) {
        this.numero = numero;
        this.responsable = responsable;
        this.direccion = direccion;
        this.peticiones = new ArrayList<>(); // Inicializar la lista de peticiones
    }

    // Getters y setters
    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario getResponsable() {
        return responsable;
    }

    public void setResponsable(Usuario responsable) {
        this.responsable = responsable;
    }

    public List<Peticion> getPeticiones() {
        return peticiones;
    }

    public void setPeticiones(List<Peticion> peticiones) {
        this.peticiones = peticiones;
    }

    // Método para añadir peticiones a la sucursal
    public void addPeticion(Peticion peticion) {
        this.peticiones.add(peticion);
    }
}

