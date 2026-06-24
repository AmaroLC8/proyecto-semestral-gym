package com.grupito.inventario_services.service;

import com.grupito.inventario_services.model.Producto;
import com.grupito.inventario_services.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repo;
    public ProductoService(ProductoRepository repo) { this.repo = repo; }

    public List<Producto> listar() { return repo.findAll(); }
    public Producto guardar(Producto p) { return repo.save(p); }
    public Producto obtenerPorId(Long id) { 
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado")); 
    }
    public void eliminar(Long id) { repo.deleteById(id); }
}