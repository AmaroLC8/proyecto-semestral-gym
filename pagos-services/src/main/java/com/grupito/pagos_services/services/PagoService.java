package com.grupito.pagos_services.services;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.grupito.pagos_services.client.ReservaClient;
import com.grupito.pagos_services.client.UsuarioClient;
import com.grupito.pagos_services.exception.BadRequestException;
import com.grupito.pagos_services.exception.ResourceNotFoundException;
import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final UsuarioClient usuarioClient;
    private final ReservaClient reservaClient;

    private static final List<String> MEDIOS_VALIDOS =
            Arrays.asList("EFECTIVO", "TARJETA_CREDITO", "TARJETA_DEBITO", "TRANSFERENCIA");

    private static final int DESCUENTO_MAXIMO = 50;
    private static final int VALOR_NETO_MAXIMO = 10_000_000;

    public PagoService(PagoRepository pagoRepository, UsuarioClient usuarioClient, ReservaClient reservaClient) {
        this.pagoRepository = pagoRepository;
        this.usuarioClient = usuarioClient;
        this.reservaClient = reservaClient;
    }

    /**
     * Procesa y guarda un pago aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: El descuento no puede superar el 50%. Un descuento mayor puede
     *          indicar un error en los datos o un intento de fraude en el sistema.
     *
     * REGLA 2: El valor neto no puede superar los 10.000.000. Pagos por montos
     *          mayores requieren aprobacion manual fuera del sistema automatico.
     *
     * REGLA 3: El medio de pago debe ser uno de los valores validos del sistema:
     *          EFECTIVO, TARJETA_CREDITO, TARJETA_DEBITO o TRANSFERENCIA.
     *
     * Ademas se calcula automaticamente el IVA (19%) y el total a pagar.
     */
    public Pago procesarYGuardarPago(Pago pago) {

        // REGLA 1: Descuento maximo 50%
        if (pago.getDescuento() != null && pago.getDescuento() > DESCUENTO_MAXIMO) {
            throw new BadRequestException(
                    "El descuento no puede superar el " + DESCUENTO_MAXIMO +
                    "%. Valor recibido: " + pago.getDescuento() + "%");
        }

        // REGLA 2: Valor neto maximo
        if (pago.getValorNeto() != null && pago.getValorNeto() > VALOR_NETO_MAXIMO) {
            throw new BadRequestException(
                    "El valor neto supera el maximo permitido de $" + VALOR_NETO_MAXIMO +
                    ". Los pagos de mayor cuantia requieren aprobacion manual.");
        }

        // REGLA 3: Medio de pago valido
        if (pago.getMedioPago() != null &&
                !MEDIOS_VALIDOS.contains(pago.getMedioPago().toUpperCase())) {
            throw new BadRequestException(
                    "Medio de pago invalido. Valores permitidos: " + MEDIOS_VALIDOS);
        }

        // Calculo automatico: descuento + IVA (19%) + total
        int descuento = pago.getDescuento() != null ? pago.getDescuento() : 0;
        int montoDescuento = (pago.getValorNeto() * descuento) / 100;
        int subtotal = pago.getValorNeto() - montoDescuento;
        int iva = (subtotal * 19) / 100;
        pago.setIva(iva);
        pago.setTotalPagar(subtotal + iva);

        if (pago.getFecha() == null) {
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
        Pago existente = obtenerPorId(pago.getId());
        if (pago.getFecha() == null) {
            pago.setFecha(existente.getFecha());
        }
        return procesarYGuardarPago(pago);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }
}