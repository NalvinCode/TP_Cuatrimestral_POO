package Controllers;
import normalClasses.Practica;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PracticaController {
    private Map<Integer, Practica> practicas = new HashMap<>();

    public void altaPractica(Practica practica) {
        if (practicas.containsKey(practica.getCodigo())) {
            throw new IllegalArgumentException("Ya existe una práctica con este código.");
        }
        practicas.put(practica.getCodigo(), practica);
    }

    public void modificarPractica(int codigo, Practica practica) {
        if (!practicas.containsKey(codigo)) {
            throw new IllegalArgumentException("No existe una práctica con el código proporcionado.");
        }
        practicas.put(codigo, practica);
    }

    public void bajaPractica(int codigo) {
        if (!practicas.containsKey(codigo)) {
            throw new IllegalArgumentException("No existe una práctica con el código proporcionado para eliminar.");
        }
        practicas.remove(codigo);
    }

    public Practica obtenerPractica(int codigo) {
        return practicas.get(codigo);
    }

    public List<Practica> getPracticas() {
        return new ArrayList<>(practicas.values());
    }
}