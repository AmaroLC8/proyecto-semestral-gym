package com.grupito.pagos_services.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.grupito.pagos_services.client.ReservaClient;
import com.grupito.pagos_services.client.UsuarioClient;
import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.repository.PagoRepository;

@Service
public class PagoService {
    private final PagoRepository pagoRepository;
    private final UsuarioClient usuarioClient;
    private final ReservaClient reservaClient;

    public PagoService(PagoRepository pagoRepository, UsuarioClient usuarioClient, ReservaClient reservaClient){
        this.pagoRepository = pagoRepository;
        this.usuarioClient = usuarioClient;
        this.reservaClient = reservaClient;
    }

    public Pago guardar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public boolean existePorId(Long id) {
        return pagoRepository.existsById(id);
    }

    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    public List<Pago> buscarPorMetodo(String metodo) {
        return pagoRepository.findByMetodoPago(metodo);
    }

    public List<Pago> buscarPorRangoFechas(Date desde, Date hasta) {
        return pagoRepository.findByFechaPagoBetween(desde, hasta);
    }

    public List<Pago> buscarPorIdSocio(int idSocio) {
        return pagoRepository.findByIdSocio(idSocio);
    }

    public Map<String, Object> obtenerUsuarioPorId(Long id) {
        return usuarioClient.getUsuarioByIdBlocking(id);
    }

    public List<Map<String, Object>> obtenerReservasPorSocio(int idSocio) {
        return reservaClient.getReservasBySocioBlocking(idSocio);
    }

    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago actualizar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
}