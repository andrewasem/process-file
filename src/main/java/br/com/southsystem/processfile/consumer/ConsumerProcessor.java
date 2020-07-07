package br.com.southsystem.processfile.consumer;

import br.com.southsystem.processfile.model.dat.Document;
import br.com.southsystem.processfile.service.DocumentService;
import br.com.southsystem.processfile.utilities.Utilities;
import lombok.extern.java.Log;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Log
public class ConsumerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {
        try {
            String destFolderFile = Utilities.getDestFolderFile();

            Message message = exchange.getIn();
            File fileToProcess = message.getBody(File.class);

            String currentFilePath = fileToProcess.getAbsolutePath();

            log.info("Arquivo detectado em: '" + currentFilePath);

            // verifia se o diretório de destino existe, caso não exista vai criar, caso tenha permissão
            Utilities.checkFolder(destFolderFile);

            String destFilePath = destFolderFile + File.separator + fileToProcess.getName().replace(".dat", ".done.dat");

            String textFile = Utilities.readTextFile(currentFilePath);

            // Chamada a classe DocumentoUtil que vai realizar a leitura dos dados
            // do arquivo e retornar um modelo do tipo Document para que possa ser escrito
            Document document = new DocumentService(fileToProcess.getAbsolutePath()).doDocument(textFile);

            if (document != null) {
                Utilities.writerFile(document.toString(), destFilePath);
                log.info("Criado arquivo: " + destFilePath);
            } else {
                log.warning("Arquivo não foi criado, favor verificar as informaões do arquivo: " + currentFilePath);
            }
        } catch (Exception e) {
            log.warning("Erro ao tentar realizar leitura do arquivo, favor verificar os dados.");
        }
    }
}
