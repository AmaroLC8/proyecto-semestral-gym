package com.grupito.inventario_services.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.inventario_services.model.Inventarios;
import com.grupito.inventario_services.repository.InventarioRepository;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public Inventarios guardar(Inventarios inventario) {
        return inventarioRepository.save(inventario);
    }

    public List<Inventarios> listar() {
        return inventarioRepository.findAll();
    }

    public Inventarios obtenerPorId(Long id) {
        return inventarioRepository.findById(id).orElse(null);
    }

    public Inventarios actualizar(Inventarios inventario) {
        return inventarioRepository.save(inventario);
    }

    public void eliminar(Long id) {
        inventarioRepository.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return inventarioRepository.existsById(id);
    }
}
