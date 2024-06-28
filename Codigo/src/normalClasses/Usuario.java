package normalClasses;

import enums.RolUsuario;

import java.time.LocalDate;
import java.util.Arrays;

public class Usuario {
    private String name;
    private String mail;
    private int DNI;
    private LocalDate FechaNacimiento;
    private String nombreUsuario;
    private char[] password;
    private String domicilio;

    private RolUsuario rolUsuario;

    // CONSTRUCTOR DEL USUARIO

    public Usuario(String name, int DNI, String mail, LocalDate fechaNacimiento, String nombreUsuario, char[] password, String domicilio, RolUsuario rolUsuario) {
        this.name = name;
        this.DNI = DNI;
        this.mail = mail;
        FechaNacimiento = fechaNacimiento;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.domicilio = domicilio;
        this.rolUsuario = rolUsuario;
    }

    // GETTERS DEL USUARIO

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public int getDNI() {
        return DNI;
    }

    public LocalDate getFechaNacimiento() {
        return FechaNacimiento;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public char[] getPassword() {
        return password;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    // SETTERS DEL USUARIO


    public void setName(String name) {
        this.name = name;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        FechaNacimiento = fechaNacimiento;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public void setRolUsuario(RolUsuario rolUsuario) { this.rolUsuario = rolUsuario;}

    @Override
    public String toString() {
        return "Usuario{" +
                "name='" + name + '\'' +
                ", mail='" + mail + '\'' +
                ", DNI=" + DNI +
                ", FechaNacimiento=" + FechaNacimiento +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", rolUsuario=" + rolUsuario +
                '}';
    }
}
