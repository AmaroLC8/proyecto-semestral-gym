package com.grupito.pagos_services.services;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.pagos_services.client.ReservaClient;
import com.grupito.pagos_services.client.UsuarioClient;
import com.grupito.pagos_services.exception.ResourceNotFoundException;
import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final UsuarioClient usuarioClient;
    private final ReservaClient reservaClient;

    public PagoService(PagoRepository pagoRepository, UsuarioClient usuarioClient, ReservaClient reservaClient) {
        this.pagoRepository = pagoRepository;
        this.usuarioClient = usuarioClient;
        this.reservaClient = reservaClient;
    }

    public Pago procesarYGuardarPago(Pago pago) {
        int montoDescuento = (pago.getValorNeto() * pago.getDescuento()) / 100;
        int subtotal = pago.getValorNeto() - montoDescuento;
        int iva = (subtotal * 19) / 100;
        pago.setIva(iva);
        pago.setTotalPagar(subtotal + iva);
        if(pago.getFecha() == null) {
            pago.setFecha(new Date());
        }
        return pagoRepository.save(pago);
    }

    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con ID: " + id));
    }

    public boolean existePorId(Long id) {
        return pagoRepository.existsById(id);
    }

    public List<Pago> buscarPorIdCompra(Long idCompra) {
        return pagoRepository.findByIdCompra(idCompra);
    }

    // MÉTODOS RESTAURADOS PARA QUE EL CONTROLLER NO FALLE
    public List<Pago> buscarPorMetodo(String metodo) {
        return pagoRepository.findByMedioPago(metodo);
    }

    public List<Pago> buscarPorRangoFechas(Date desde, Date hasta) {
        return pagoRepository.findByFechaBetween(desde, hasta);
    }

    public Object obtenerUsuarioRemoto(Long usuarioId) {
        try {
            return usuarioClient.getUsuarioByIdBlocking(usuarioId);
        } catch (Exception e) {
            return "Servicio Usuarios no disponible temporalmente";
        }
    }

    public Object obtenerReservasRemotas(Long socioId) {
        try {
            return reservaClient.getReservasBySocioBlocking(socioId);
        } catch (Exception e) {
            return "Servicio Reservas no disponible temporalmente";
        }
    }

    public Pago actualizar(Pago pago) {
        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
}