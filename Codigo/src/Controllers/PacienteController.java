package Controllers;
import normalClasses.Paciente;
import java.util.HashMap;
import java.util.Map;
import enums.ObraSocial;
import java.util.*;
import java.util.stream.Collectors;


public class PacienteController {
    private Map<Integer, Paciente> pacientes = new HashMap<>();

    // Constructor
    public PacienteController() {
        // Inicialización, si es necesario, podría ir aquí
    }

    public List<Paciente> obtenerTodosLosPacientes() {
        return new ArrayList<>(pacientes.values());
    }


    // Método para dar de alta a un paciente
    public void altaPaciente(Paciente paciente) {
        if (pacientes.containsKey(paciente.getDNI())) {
            throw new IllegalArgumentException("Ya existe un paciente con este DNI.");
        }
        pacientes.put(paciente.getDNI(), paciente);
    }

    // Método para modificar a un paciente
    public void modificarPaciente(int dni, Paciente nuevoPaciente) {
        if (!pacientes.containsKey(dni)) {
            throw new IllegalArgumentException("No existe un paciente con el DNI proporcionado.");
        }
        pacientes.put(dni, nuevoPaciente);
    }

    // Método para dar de baja a un paciente
    public void bajaPaciente(int dni) {
        if (!pacientes.containsKey(dni)) {
            throw new IllegalArgumentException("No existe un paciente con el DNI proporcionado para eliminar.");
        }
        pacientes.remove(dni);
    }

    // Método para obtener un paciente por su DNI
    public Paciente obtenerPaciente(int dni) {
        if (!pacientes.containsKey(dni)) {
            throw new IllegalArgumentException("No se encontró un paciente con ese DNI.");
        }
        return pacientes.get(dni);
    }

    // Opcionalmente, un método para obtener todos los pacientes de una obra social específica
    public List<Paciente> obtenerPacientesPorObraSocial(ObraSocial obraSocial) {
        return pacientes.values().stream()
                .filter(p -> p.getObraSocial() == obraSocial)
                .collect(Collectors.toList());
    }
}
