package com.grupito.rutinas_services.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.rutinas_services.model.Rutina;
import com.grupito.rutinas_services.repository.RutinaRepository;

/**
 * Servicio para manejar la lógica de negocio relacionada con rutinas.
 * Interactúa con el repositorio para operaciones CRUD.
 */
@Service
public class RutinaServices {
    private final RutinaRepository rutinaRepository;

    /**
     * Constructor que inyecta el repositorio de rutinas.
     * @param rutinaRepository Repositorio para acceder a la base de datos de rutinas.
     */
    public RutinaServices(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    /**
     * Guarda una rutina en la base de datos.
     * @param rutina La rutina a guardar.
     * @return La rutina guardada con ID asignado si es nueva.
     */
    public Rutina guardar(Rutina rutina) {
        return rutinaRepository.save(rutina);
    }

    /**
     * Verifica si existe una rutina por su ID.
     * @param id ID de la rutina.
     * @return true si existe, false en caso contrario.
     */
    public boolean existePorId(Long id) {
        return rutinaRepository.existsById(id);
    }

    /**
     * Lista todas las rutinas disponibles.
     * @return Lista de todas las rutinas.
     */
    public List<Rutina> listar() {
        return rutinaRepository.findAll();
    }

    /**
     * Obtiene una rutina por su ID.
     * @param id ID de la rutina.
     * @return La rutina si existe, null en caso contrario.
     */
    public Rutina obtenerPorId(Long id) {
        return rutinaRepository.findById(id).orElse(null);
    }

    /**
     * Actualiza una rutina existente.
     * @param rutina La rutina a actualizar.
     * @return La rutina actualizada.
     */
    public Rutina actualizar(Rutina rutina) {
        return rutinaRepository.save(rutina);
    }

    /**
     * Elimina una rutina por su ID.
     * @param id ID de la rutina a eliminar.
     */
    public void eliminar(Long id) {
        rutinaRepository.deleteById(id);
    }
}
