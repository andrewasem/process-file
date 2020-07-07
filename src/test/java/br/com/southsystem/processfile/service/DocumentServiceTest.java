package br.com.southsystem.processfile.service;

import br.com.southsystem.processfile.model.dat.Document;
import br.com.southsystem.processfile.utilities.Utilities;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;

@SpringBootTest
@Log
class DocumentServiceTest {

    private static final String DATA_IN = "data/in/data.dat";

    private static final String DATA_OUT = "data/out/";

    private static final String CORRECT_RESULT = "data/result/data.done.dat";

    @Mock
    private DocumentService documentService;

    @Test
    public void testReadFileDataIn() {
        log.info("Realizando teste de leitura de arquivo em resources/data/in/data.dat");
        String text = getFileText(null, DATA_IN);

        Assertions.assertNotNull(text, "Não foram encontrados dados dentro do arquivo.");
        Assertions.assertNotEquals("texto incorreto", text);
    }

    @Test
    public void testResultDocument() {
        log.info("Realizando construção do objeto Document via arquivo que está em resources/data/in/data.dat");
        try {
            URL url = getFileURL();
            // obtendo o caminho dentro da pasta data/in
            String filePathIn = getFilePath(url, DATA_IN);

            // obtendo o texto do arquivo
            String text = getFileText(url, DATA_IN);

            // realizando leitura do texto do arquivo na pasta data/in
            Document document = new DocumentService(filePathIn).doDocument(text);

            // obtendo o caminho dentro da pasta correct-result
            String filePathCorretResult = getFilePath(url, CORRECT_RESULT);

            // obtendo o texto do arquivo que tem o resultado esperado
            String textCorrectResult = getFileText(url, CORRECT_RESULT);

            Assertions.assertNotNull(document, "Não foi possível construir o documento, verificar se o documento existe em: ");

            log.info("Comparando o resultado do arquivo resources/data/in/data.dat com o resultado do arquivo que se encontra em resouces/result/data.done.dat");
            if (!textCorrectResult.equals(document.toString())) {
                Assertions.assertNotNull(null, "Resultado obtido de: " + filePathIn + " está diferente do resultado esperado em: " + filePathCorretResult);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.assertNotNull(null, "Erro na construção do objeto Document ou erro na leitura dos Arquivos.");
        }
    }

    /*
     * Obtém URL do arquivo
     */
    private URL getFileURL() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource("application.properties");

        Assertions.assertNotNull(url, "Verificar se o arquivo application.properties se encontra na pasta resources do projeto.");
        Assertions.assertNotNull(url.getFile(), "Caminho do arquivo não detectado.");
        return url;
    }

    /*
     * Obtém arquivo conforme localização
     */
    private String getFileText(URL url, String filePath) {
        if (url == null) {
            url = getFileURL();
        }

        String targetFilePath = getFilePath(url, filePath);

        log.info("Caminho do arquivo: " + targetFilePath);

        return Utilities.readTextFile(targetFilePath);
    }

    /*
     * Obtém caminho do arquivo
     */
    private String getFilePath(URL url, String filePath) {
        return url.getFile().replaceAll("application.properties", filePath);
    }
}