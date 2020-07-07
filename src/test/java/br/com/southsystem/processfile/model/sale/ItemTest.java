package br.com.southsystem.processfile.model.sale;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
@Log
public class ItemTest {

    @Test
    public void testItem() {
        log.info("Iniciando teste unitário de criação de Item.");
        Item item = new Item();
        item.setItemId(1);
        item.setItemPrice(new BigDecimal("10.00"));
        item.setQuantityItem(11);

        Assertions.assertNotNull(item.getItemId());
        Assertions.assertNotNull(item.getItemPrice());
        Assertions.assertNotNull(item.getQuantityItem());

        log.info("Finalizando teste unitário de criação de Item.");
    }
}
