package com.grupito.inventario_services.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.inventario_services.exception.ResourceNotFoundException;
import com.grupito.inventario_services.model.Producto;
import com.grupito.inventario_services.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository repo;
    public ProductoService(ProductoRepository repo) { this.repo = repo; }

    public List<Producto> listar() { return repo.findAll(); }
    public Producto guardar(Producto p) {
        if (p.getStock() != null && p.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        return repo.save(p);
    }
    public Producto obtenerPorId(Long id) { 
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id)); 
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}
