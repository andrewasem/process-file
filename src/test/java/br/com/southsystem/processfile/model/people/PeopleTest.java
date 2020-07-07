package br.com.southsystem.processfile.model.people;

import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log
public class PeopleTest {

    @Test
    public void testPeople() {
        log.info("Iniciando teste unitário de criação de Pessoa.");
        People people = new People();
        people.setName("João");

        Assertions.assertNotNull(people.getName());

        log.info("Finalizando teste unitário de criação de Pessoa.");
    }
}
