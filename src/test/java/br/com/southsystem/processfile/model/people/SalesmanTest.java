package br.com.southsystem.processfile.model.people;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log
public class SalesmanTest {

    @Test
    public void testSalesman() {
        log.info("Iniciando teste unitário de criação de Vendedores.");
        log.info("Teste");
        Salesman salesman = new Salesman();
        salesman.setCpf("000.000.000-00");
        salesman.setSalary("10.00");
        salesman.setName("João");

        Assertions.assertNotNull(salesman.getCpf());
        Assertions.assertNotNull(salesman.getSalary());
        Assertions.assertNotNull(salesman.getName());

        log.info("Finalizando teste unitário de criação de Vendedores.");
    }
}
