package com.grupito.inventario_services.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.inventario_services.exception.BadRequestException;
import com.grupito.inventario_services.exception.ResourceNotFoundException;
import com.grupito.inventario_services.model.Producto;
import com.grupito.inventario_services.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    private static final List<String> CATEGORIAS_VALIDAS =
            Arrays.asList("EQUIPAMIENTO", "SUPLEMENTO", "ACCESORIO", "ROPA", "SERVICIO");

    private static final int STOCK_MAXIMO = 10_000;
    private static final double PRECIO_MAXIMO = 100_000_000.0;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    public List<Producto> listar() {
        return repo.findAll();
    }

    /**
     * Guarda un producto aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: El stock no puede ser negativo y no puede superar las 10.000 unidades.
     *          Un stock negativo es invalido. Un stock mayor a 10.000 sugiere un error
     *          de ingreso o requiere validacion adicional.
     *
     * REGLA 2: El precio del producto debe ser mayor a cero y no puede superar
     *          los $100.000.000. Precios superiores requieren revision administrativa.
     *
     * REGLA 3: La categoria debe ser una de las categorias validas del sistema:
     *          EQUIPAMIENTO, SUPLEMENTO, ACCESORIO, ROPA o SERVICIO.
     */
    public Producto guardar(Producto p) {

        // REGLA 1: Stock valido (no negativo y no mayor al maximo)
        if (p.getStock() != null && (p.getStock() < 0 || p.getStock() > STOCK_MAXIMO)) {
            throw new BadRequestException(
                    "El stock debe estar entre 0 y " + STOCK_MAXIMO +
                    " unidades. Valor recibido: " + p.getStock());
        }

        // REGLA 2: Precio en rango valido
        if (p.getPrecio() != null && (p.getPrecio() <= 0 || p.getPrecio() > PRECIO_MAXIMO)) {
            throw new BadRequestException(
                    "El precio debe ser mayor a 0 y no puede superar $" + PRECIO_MAXIMO +
                    ". Valor recibido: " + p.getPrecio());
        }

        // REGLA 3: Categoria valida
        if (p.getCategoria() != null &&
                !CATEGORIAS_VALIDAS.contains(p.getCategoria().toUpperCase())) {
            throw new BadRequestException(
                    "Categoria invalida. Valores permitidos: " + CATEGORIAS_VALIDAS);
        }

        return repo.save(p);
    }

    public Producto obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}
