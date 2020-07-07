package br.com.southsystem.processfile;

import br.com.southsystem.processfile.model.dat.Document;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Log
class FilesApplicationTests {
    @Test
    public void init() {
        log.info("Teste");
        Assertions.assertNotNull(new Document());
    }
}