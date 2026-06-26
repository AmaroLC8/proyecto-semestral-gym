package com.grupito.seguimientos_services.services;

import com.grupito.seguimientos_services.exception.BadRequestException;
import com.grupito.seguimientos_services.exception.ResourceNotFoundException;
import com.grupito.seguimientos_services.model.Seguimiento;
import com.grupito.seguimientos_services.repository.SeguimientoRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class SeguimientoService {

    private final SeguimientoRepository repo;

    private static final double PESO_MINIMO = 20.0;
    private static final double PESO_MAXIMO = 300.0;
    private static final double GRASA_MAXIMA = 60.0;
    private static final int DIAS_ENTRE_REGISTROS = 3;

    public SeguimientoService(SeguimientoRepository repo) {
        this.repo = repo;
    }

    public List<Seguimiento> listar() {
        return repo.findAll();
    }

    /**
     * Guarda un seguimiento aplicando las siguientes reglas de negocio:
     *
     * REGLA 1: El peso del socio debe estar en un rango fisiologicamente valido,
     *          entre 20 kg y 300 kg. Valores fuera de este rango son considerados errores.
     *
     * REGLA 2: El porcentaje de grasa corporal no puede superar el 60%.
     *          Un valor mayor a ese es fisiologicamente imposible y se rechaza.
     *
     * REGLA 3: Un socio no puede registrar mas de un seguimiento en los ultimos 3 dias.
     *          Esto evita el doble registro accidental y asegura intervalos de medicion validos.
     */
    public Seguimiento guardar(Seguimiento seguimiento) {

        // REGLA 1: Peso en rango valido
        if (seguimiento.getPeso() < PESO_MINIMO || seguimiento.getPeso() > PESO_MAXIMO) {
            throw new BadRequestException(
                    "El peso debe estar entre " + PESO_MINIMO + " kg y " + PESO_MAXIMO +
                    " kg. Valor recibido: " + seguimiento.getPeso());
        }

        // REGLA 2: Porcentaje de grasa no mayor al 60%
        if (seguimiento.getPorcentajeGrasa() > GRASA_MAXIMA) {
            throw new BadRequestException(
                    "El porcentaje de grasa corporal no puede superar el " + GRASA_MAXIMA +
                    "%. Valor recibido: " + seguimiento.getPorcentajeGrasa());
        }

        // REGLA 3: Verificar que no haya registrado un seguimiento en los ultimos 3 dias
        long hace3Dias = System.currentTimeMillis() - (long) DIAS_ENTRE_REGISTROS * 24 * 60 * 60 * 1000;
        Date fechaLimite = new Date(hace3Dias);

        boolean registroReciente = repo.findAll().stream()
                .filter(s -> s.getIdSocio() == seguimiento.getIdSocio())
                .anyMatch(s -> s.getFechaRegistro() != null && s.getFechaRegistro().after(fechaLimite));

        if (registroReciente) {
            throw new BadRequestException(
                    "El socio con id " + seguimiento.getIdSocio() +
                    " ya tiene un seguimiento registrado en los ultimos " + DIAS_ENTRE_REGISTROS +
                    " dias. Por favor, espere antes de registrar uno nuevo.");
        }

        if (seguimiento.getFechaRegistro() == null) {
            seguimiento.setFechaRegistro(new Date());
        }

        return repo.save(seguimiento);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    public boolean existePorId(Long id) {
        return repo.existsById(id);
    }

    public Seguimiento obtenerPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Seguimiento no encontrado con id: " + id));
    }
}
