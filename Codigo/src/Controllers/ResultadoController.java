package Controllers;
import normalClasses.Resultado;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import enums.Estado;

public class ResultadoController {
    private Map<Integer, Resultado> resultados = new HashMap<>();

    // Constructor
    public ResultadoController() {
        // Inicialización, si es necesario, podría ir aquí
    }

    // Método para agregar un resultado
    public void agregarResultado(Resultado resultado) {
        int idResultado = resultado.hashCode(); // Simulando un ID único basado en el hash del objeto
        if (resultados.containsKey(idResultado)) {
            throw new IllegalArgumentException("Ya existe un resultado con este ID.");
        }
        resultados.put(idResultado, resultado);
    }

    // Método para actualizar un resultado
    public void actualizarResultado(int idResultado, Resultado resultado) {
        if (!resultados.containsKey(idResultado)) {
            throw new IllegalArgumentException("No existe un resultado con el ID proporcionado.");
        }
        resultados.put(idResultado, resultado);
    }

    // Método para eliminar un resultado
    public void eliminarResultado(int idResultado) {
        if (!resultados.containsKey(idResultado)) {
            throw new IllegalArgumentException("No existe un resultado con el ID proporcionado para eliminar.");
        }
        resultados.remove(idResultado);
    }

    // Método para obtener un resultado por ID
    public Resultado obtenerResultado(int idResultado) {
        if (!resultados.containsKey(idResultado)) {
            throw new IllegalArgumentException("No se encontró un resultado con ese ID.");
        }
        return resultados.get(idResultado);
    }

    // Método para buscar resultados por estado
    public List<Resultado> buscarResultadosPorEstado(Estado estado) {
        return resultados.values().stream()
                .filter(r -> r.getEstadoResultado() == estado)
                .collect(Collectors.toList());
    }
}
