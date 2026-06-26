package com.grupito.inventario_services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupito.inventario_services.model.Producto;
import com.grupito.inventario_services.repository.ProductoRepository;

@Component
public class DataLoader implements CommandLineRunner {
    private final ProductoRepository repo;
    
    public DataLoader(ProductoRepository repo) { 
        this.repo = repo; 
    }

    @Override
    public void run(String... args) throws Exception {
        if (repo.count() == 0) {
            Producto p1 = new Producto();
            p1.setNombre("Esterilla");
            p1.setStock(50);
            p1.setPrecio(8000.0);
            p1.setCategoria("Yoga");
            repo.save(p1);
        
            Producto p2 = new Producto();
            p2.setNombre("Mancuernas");
            p2.setStock(20);
            p2.setPrecio(15000.0);
            p2.setCategoria("Pesas");
            repo.save(p2);
        }
    }
}
