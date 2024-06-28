package Controllers;
import normalClasses.Sucursal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import normalClasses.Peticion;
import enums.Estado;



public class SucursalController {
    private Map<Long, Sucursal> sucursales = new HashMap<>();

    public void altaSucursal(Sucursal sucursal) {
        if (sucursales.containsKey(sucursal.getNumero())) {
            throw new IllegalArgumentException("Ya existe una sucursal con este número.");
        }
        sucursales.put(sucursal.getNumero(), sucursal);
    }

    public void modificarSucursal(long numero, Sucursal sucursal) {
        if (!sucursales.containsKey(numero)) {
            throw new IllegalArgumentException("No existe una sucursal con el número proporcionado.");
        }
        sucursales.put(numero, sucursal);
    }

    public void bajaSucursal(long numero, Long numeroSucursalDestino) {
        Sucursal sucursal = sucursales.get(numero);
        if (sucursal == null) {
            throw new IllegalArgumentException("No existe una sucursal con el número proporcionado para eliminar.");
        }

        // Verificar si hay peticiones finalizadas
        boolean tienePeticionesFinalizadas = sucursal.getPeticiones().stream()
                .anyMatch(p -> p.getEstado() == Estado.FINALIZADO);

        if (tienePeticionesFinalizadas) {
            throw new IllegalArgumentException("La sucursal tiene peticiones finalizadas y no puede ser eliminada.");
        }

        // Mover peticiones pendientes a otra sucursal
        if (numeroSucursalDestino != null && sucursales.containsKey(numeroSucursalDestino)) {
            Sucursal sucursalDestino = sucursales.get(numeroSucursalDestino);
            List<Peticion> peticionesPendientes = sucursal.getPeticiones().stream()
                    .filter(p -> p.getEstado() == Estado.PENDIENTE)
                    .toList();
            peticionesPendientes.forEach(sucursalDestino::addPeticion);
            sucursal.getPeticiones().removeAll(peticionesPendientes); // Opcional, depende de si quieres conservar el historial en la sucursal original
        }

        // Ahora es seguro eliminar la sucursal
        sucursales.remove(numero);
    }

    public Sucursal obtenerSucursal(long numero) {
        if (!sucursales.containsKey(numero)) {
            throw new IllegalArgumentException("No se encontró una sucursal con ese número.");
        }
        return sucursales.get(numero);
    }
}
