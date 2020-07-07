package br.com.southsystem.processfile.model.dat;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log
public class DocumentTest {

    @Test
    public void testDocument() {

        log.info("Iniciando teste unitário de criação do Documento.");
        Document document = new Document();
        document.setMostExpensiveSaleId("08");
        document.setNameWorstSeller("Paulo");
        document.setTotalCustomer(1);
        document.setTotalSalesman(1);

        Assertions.assertNotNull(document.getMostExpensiveSaleId());
        Assertions.assertNotNull(document.getNameWorstSeller());
        Assertions.assertNotNull(document.getTotalCustomer());
        Assertions.assertNotNull(document.getTotalSalesman());

        log.info("Finalizado teste unitário de criação do Documento.");
    }
}
