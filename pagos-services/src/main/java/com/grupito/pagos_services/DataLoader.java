package com.grupito.pagos_services;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.grupito.pagos_services.model.Pago;
import com.grupito.pagos_services.repository.PagoRepository;

import java.util.Date;
import java.util.Random;

import net.datafaker.Faker;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final PagoRepository pagoRepository;
    public DataLoader(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (pagoRepository.count() == 0) {
            Faker faker = new Faker();
            Random random = new Random();
            
            for (int i = 0; i < 5; i++) {
                Pago pago = new Pago();
                pago.setIdCompra((long) random.nextInt(3) + 1);
                
                int valorNeto = faker.number().numberBetween(10000, 100000);
                int descuento = faker.number().numberBetween(0, 50);
                
                int montoDescuento = (valorNeto * descuento) / 100;
                int subtotal = valorNeto - montoDescuento;
                int iva = (subtotal * 19) / 100;
                
                pago.setValorNeto(valorNeto);
                pago.setIva(iva);
                pago.setDescuento(descuento);
                pago.setTotalPagar(subtotal + iva);
                pago.setMedioPago(faker.options().option("Tarjeta de Crédito", "Transferencia", "Efectivo"));
                pago.setFecha(new Date());
                
                pagoRepository.save(pago);
            }
            System.out.println("Carga inicial de Pagos completada exitosamente.");
        }
    }
}