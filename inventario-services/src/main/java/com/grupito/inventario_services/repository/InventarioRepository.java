package com.grupito.inventario_services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupito.inventario_services.model.Inventarios;

@Repository
public interface InventarioRepository extends JpaRepository<Inventarios, Long> {

}

