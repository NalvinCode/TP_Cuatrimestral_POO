package Controllers;
import normalClasses.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import enums.RolUsuario;


public class UsuarioController {
    private Map<Integer, Usuario> usuarios = new HashMap<>();

    // Constructor
    public UsuarioController() {
        // Inicialización, si es necesario, podría ir aquí
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios.values());}

    // Método para dar de alta a un usuario
    public void altaUsuario(Usuario usuario) {
        if (usuarios.containsKey(usuario.getDNI())) {
            throw new IllegalArgumentException("Ya existe un usuario con el DNI especificado.");
        }
        usuarios.put(usuario.getDNI(), usuario);
    }

    // Método para modificar a un usuario
    public void modificarUsuario(int DNI, Usuario nuevoUsuario) {
        if (!usuarios.containsKey(DNI)) {
            throw new IllegalArgumentException("No existe un usuario con el DNI proporcionado.");
        }
        usuarios.put(DNI, nuevoUsuario);
    }

    // Método para dar de baja a un usuario
    public void bajaUsuario(int DNI) {
        if (!usuarios.containsKey(DNI)) {
            throw new IllegalArgumentException("No existe un usuario con el DNI proporcionado para eliminar.");
        }
        usuarios.remove(DNI);
    }

    // Método para obtener un usuario por su DNI
    public Usuario obtenerUsuario(int DNI) {
        if (!usuarios.containsKey(DNI)) {
            throw new IllegalArgumentException("No se encontró un usuario con ese DNI.");
        }
        return usuarios.get(DNI);
    }

    // Método para asignar un rol a un usuario
    public void asignarRolUsuario(int DNI, RolUsuario rol) {
        if (!usuarios.containsKey(DNI)) {
            throw new IllegalArgumentException("No se puede asignar un rol a un usuario inexistente.");
        }
        Usuario usuario = usuarios.get(DNI);
        // Asumimos que la clase Usuario tiene un campo para Rol que no está definido actualmente en la clase.
        // usuario.setRol(rol); // Descomentar esta línea una vez que se añade el campo `rol` a la clase Usuario.
    }
}
