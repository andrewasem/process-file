package br.com.southsystem.processfile.model.sale;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log
public class SaleTest {

    @Test
    public void testSale() {
        log.info("Iniciando teste unitário de criação de Venda.");

        List<Item> items = new ArrayList<>();
        items.add(new Item());
        items.add(new Item());

        Sale sale = new Sale();
        sale.setSaleId("01");
        sale.setSalesmanName("João");
        sale.setSoldItems(items);
        sale.setTotalSaleValue(new BigDecimal("10.00"));

        Assertions.assertNotNull(sale.getSaleId());
        Assertions.assertNotNull(sale.getSalesmanName());
        Assertions.assertNotNull(sale.getSoldItems());
        Assertions.assertNotNull(sale.getTotalSaleValue());

        log.info("Finalizando teste unitário de criação de Venda.");
    }
}
