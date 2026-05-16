package com.grupito.seguimientos_services.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.grupito.seguimientos_services.client.UsuarioClient;
import com.grupito.seguimientos_services.model.Seguimiento;
import com.grupito.seguimientos_services.repository.SeguimientoRepository;

@Service
public class SeguimientoService {
    private final SeguimientoRepository seguimientoRepository;
    private final UsuarioClient usuarioClient;

    public SeguimientoService(SeguimientoRepository seguimientoRepository, UsuarioClient usuarioClient){
        this.seguimientoRepository = seguimientoRepository;
        this.usuarioClient = usuarioClient;
    }

    public Seguimiento guardar(Seguimiento seguimiento) {
        return seguimientoRepository.save(seguimiento);
    }

    public boolean existePorId(Long id) {
        return seguimientoRepository.existsById(id);
    }

    public List<Seguimiento> listar() {
        return seguimientoRepository.findAll();
    }

    public List<Seguimiento> buscarPorIdSocio(int idSocio) {
        return seguimientoRepository.findByIdSocio(idSocio);
    }

    public List<Seguimiento> buscarPorRangoFechas(Date desde, Date hasta) {
        return seguimientoRepository.findByFechaRegistroBetween(desde, hasta);
    }

    public Map<String, Object> obtenerUsuarioPorId(Long id) {
        return usuarioClient.getUsuarioByIdBlocking(id);
    }

    public Seguimiento obtenerPorId(Long id) {
        return seguimientoRepository.findById(id).orElse(null);
    }

    public Seguimiento actualizar(Seguimiento seguimiento) {
        return seguimientoRepository.save(seguimiento);
    }

    public void eliminar(Long id) {
        seguimientoRepository.deleteById(id);
    }
}