package br.com.southsystem.processfile.consumer;

import lombok.extern.java.Log;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.RouteDefinition;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Log
public class ConsumerRouterBuilder extends RouteBuilder {

    public static final int INITIAL_DELAY = 5000;
    public static final int READ_LOCK_CHECK_INTERVAL = 5000;
    public static final int DELAY = 5000;

    public static final String READ_LOCK = "changed";
    public static final String INCLUDE_FILE_TYPE = ".*.dat";

    public void configure() {

        String homePath = System.getProperty("user.home");

        if (!StringUtils.isEmpty(homePath)) {
            log.info("Configurando o endpoint do Apache Camel com o diretório de origem em: " +  homePath + "/data/in");

            RouteDefinition def = from("file:" + homePath +"/data/in?"+
                    "initialDelay=" + INITIAL_DELAY +
                    "&noop=true" +
                    "&include=" + INCLUDE_FILE_TYPE +
                    "&delay=" + DELAY +
                    "&readLock="+ READ_LOCK +
                    "&readLockCheckInterval=" + READ_LOCK_CHECK_INTERVAL);
            def.process(new ConsumerProcessor());
            log.info("Processo de consumo de arquivos incializado: " + homePath + "/data/in");
        } else {
            log.warning("Inicialização do processo de consumo de arquivos interrompida. Verificar pasta de destino.");
        }
    }
}