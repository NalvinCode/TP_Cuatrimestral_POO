package normalClasses;
import enums.ObraSocial;
import java.util.ArrayList;
import java.util.List;

public class Paciente {
    private int dni;
    private String name;
    private char sexo;
    private String domicilio;
    private String mail;
    private int edad;
    private List<Peticion> peticiones;  // Lista de peticiones asociadas al paciente
    private ObraSocial obraSocial;  // Añadir ObraSocial como enum

    // Constructor del paciente
    public Paciente(int dni, String name, String domicilio, String mail, char sexo, int edad, ObraSocial obraSocial) {
        this.dni = dni;
        this.name = name;
        this.domicilio = domicilio;
        this.mail = mail;
        this.sexo = sexo;
        this.edad = edad;
        this.obraSocial = obraSocial;
        this.peticiones = new ArrayList<>();  // Inicializar la lista de peticiones
    }

    // Getters y setters
    public int getDNI() {
        return dni;
    }

    public void setDNI(int dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public List<Peticion> getPeticiones() {
        return peticiones;
    }

    public void setPeticiones(List<Peticion> peticiones) {
        this.peticiones = peticiones;
    }

    public ObraSocial getObraSocial() {
        return obraSocial;
    }

    public void setObraSocial(ObraSocial obraSocial) {
        this.obraSocial = obraSocial;
    }

    // Método para añadir peticiones al paciente
    public void addPeticion(Peticion peticion) {
        this.peticiones.add(peticion);
    }
}


