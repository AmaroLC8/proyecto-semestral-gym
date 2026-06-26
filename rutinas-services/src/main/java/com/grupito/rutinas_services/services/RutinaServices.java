package com.grupito.rutinas_services.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.rutinas_services.exception.BadRequestException;
import com.grupito.rutinas_services.exception.ResourceNotFoundException;
import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.repository.RutinaRepository;

@Service
public class RutinaServices {

    private final RutinaRepository repo;

    private static final List<String> NIVELES_VALIDOS =
            Arrays.asList("PRINCIPIANTE", "INTERMEDIO", "AVANZADO");

    private static final int DURACION_MINIMA = 10;
    private static final int DURACION_MAXIMA = 300;

    public RutinaServices(RutinaRepository repo) {
        this.repo = repo;
    }

    /**
     * Guarda una rutina aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: La duracion de la rutina debe estar entre 10 y 300 minutos.
     *          Menos de 10 minutos no es una rutina valida. Mas de 300 minutos
     *          (5 horas) es excesivo y no se permite.
     *
     * REGLA 2: El nivel de dificultad debe ser uno de los valores permitidos:
     *          PRINCIPIANTE, INTERMEDIO o AVANZADO.
     *
     * REGLA 3: El nombre de la rutina debe ser unico. No se puede crear dos rutinas
     *          con exactamente el mismo nombre (sin importar mayusculas/minusculas).
     */
    public Rutina guardar(Rutina r) {

        // REGLA 1: Duracion entre 10 y 300 minutos
        if (r.getDuracionMinutos() < DURACION_MINIMA || r.getDuracionMinutos() > DURACION_MAXIMA) {
            throw new BadRequestException(
                    "La duracion de la rutina debe estar entre " + DURACION_MINIMA +
                    " y " + DURACION_MAXIMA + " minutos. Valor recibido: " + r.getDuracionMinutos());
        }

        // REGLA 2: Nivel de dificultad valido
        if (r.getNivelDificultad() != null &&
                !NIVELES_VALIDOS.contains(r.getNivelDificultad().toUpperCase())) {
            throw new BadRequestException(
                    "Nivel de dificultad invalido. Valores permitidos: " + NIVELES_VALIDOS);
        }

        // REGLA 3: Nombre de rutina unico (ignorando mayusculas)
        boolean nombreDuplicado = repo.findAll().stream()
                .anyMatch(existing -> existing.getNombre().equalsIgnoreCase(r.getNombre()));
        if (nombreDuplicado) {
            throw new BadRequestException("Ya existe una rutina con el nombre: " + r.getNombre());
        }

        return repo.save(r);
    }

    public List<Rutina> listar() {
        return repo.findAll();
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return repo.existsById(id);
    }

    public Rutina obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rutina no encontrada con id: " + id));
    }
}
