package br.com.southsystem.processfile.model.people;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log
public class CustomerTest {

    @Test
    public void testCustomer() {
        log.info("Iniciando teste unitário de criação de Clientes.");
        Customer customer = new Customer();

        customer.setBusinessArea("Rural");
        customer.setCnpj("59.107.297/0001-31");
        customer.setName("Nome Fantasia");

        Assertions.assertNotNull(customer.getBusinessArea());
        Assertions.assertNotNull(customer.getCnpj());
        Assertions.assertNotNull(customer.getName());

        log.info("Finalizando teste unitário de criação de Clientes.");
    }
}
