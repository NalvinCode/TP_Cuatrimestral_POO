package Controllers;
import normalClasses.Peticion;
import enums.Estado;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PeticionController {
    private Map<Integer, Peticion> peticiones = new HashMap<>();

    // Constructor
    public PeticionController() {
        // Constructor vacío, podría inicializar con datos si fuera necesario
    }

    // Alta de una petición
    public void altaPeticion(Peticion peticion) {
        // Aquí va tu nuevo código
        // Por ejemplo, podrías querer cambiar la forma en que se manejan las excepciones
        if (peticion == null) {
            throw new IllegalArgumentException("La petición no puede ser nula.");
        }
        Integer id = peticion.getIdPeticion();
        if (id == null) {
            throw new IllegalArgumentException("El ID de la petición no puede ser nulo.");
        }
        if (peticiones.containsKey(id)) {
            throw new IllegalArgumentException("Ya existe una petición con este ID: " + id);
        }
        peticiones.put(id, peticion);
    }


    // Modificar una petición
    public void modificarPeticion(int idPeticion, Peticion peticion) {
        if (!peticiones.containsKey(idPeticion)) {
            throw new IllegalArgumentException("No existe una petición con el ID proporcionado.");
        }
        peticiones.put(idPeticion, peticion);
    }

    // Baja de una petición
    public void bajaPeticion(int idPeticion) {
        if (!peticiones.containsKey(idPeticion)) {
            throw new IllegalArgumentException("No existe una petición con el ID proporcionado para eliminar.");
        }
        peticiones.remove(idPeticion);
    }

    // Obtener una petición por ID
    public Peticion obtenerPeticion(int idPeticion) {
        if (!peticiones.containsKey(idPeticion)) {
            throw new IllegalArgumentException("No se encontró una petición con ese ID.");
        }
        return peticiones.get(idPeticion);
    }

    // Obtener peticiones por estado
    public List<Peticion> obtenerPeticionesPorEstado(Estado estado) {
        return peticiones.values().stream()
                .filter(p -> p.getEstado() == estado)
                .collect(Collectors.toList());
    }

    // listar todas las peticiones

    public List<Peticion> listarPeticiones() {
        return new ArrayList<>(peticiones.values());
    }

    // Suponiendo una lógica para identificar 'resultados críticos'
    public List<Peticion> obtenerPeticionesResultadosCriticos() {
        return peticiones.values().stream()
                .filter(p -> p.getResultados().stream()
                        .anyMatch(r -> r.getEstadoResultado() == Estado.FINALIZADO && r.getComentarios().contains("urgente")))
                .collect(Collectors.toList());
    }

}