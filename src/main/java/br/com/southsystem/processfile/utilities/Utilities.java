package br.com.southsystem.processfile.utilities;

import lombok.extern.java.Log;
import org.springframework.util.StringUtils;

import java.io.*;

@Log
public class Utilities {

    public static final String DATA_OUT = "/data/out/";

    public static final String UTF_8 = "utf-8";

    /**
     * Verifica o caminho da pasta e cria se não existir.
     * @param folder
     * @throws Exception
     */
    public static void checkFolder(String folder) throws Exception {
        folder = replaceWhitespace(folder);
        File folderFile = new File(folder);
        if (!folderFile.exists() || !folderFile.isDirectory()) {
            if (folderFile.mkdirs()) {
                log.info("Diretório criado: " + folder);
            } else {
                log.warning("Falha para criar o diretório: " + folder);
                throw new IOException("Falha para criar o diret´roio: '" + folder + "'. Verifique se o caminho especificado existe e é de leitura / gravação");
            }
        } else if (!folderFile.canRead() || !folderFile.canWrite()) {
            log.info("Verifique a permissão do diretório: " + folder);
            throw new IOException(
                    "Não foi possível acessar o diretório: '" + folder + "'. Verifique se o caminho especificado é de leitura e gravação.");
        }
    }

    /**
     * Realiza leitura de arquivo e retorna o texto do arquivo em String caso encontre o arquivo
     * @param origFilePath - Diretório do arquivo a ser lido
     * @return
     */
    public static String readTextFile(String origFilePath) {

        origFilePath = replaceWhitespace(origFilePath);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(origFilePath), UTF_8));) {
            String readLine;
            StringBuilder sb = new StringBuilder();

            while ((readLine = bufferedReader.readLine()) != null) {
                sb.append(readLine).append("\n");
            }

            return sb.toString().trim();
        } catch (IOException e) {
            log.warning("Arquivo não pode ser encontrado ou está inválido. Favor verificar se a estrutura do diretório origem e arquivo estão corretos.");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Realiza a escrita de um texto para um arquivo de destino
     * @param text - Texto a ser escrito
     * @param destFilePath - Caminho do arquivo a ser escrito
     */
    public static void writerFile(String text, String destFilePath) {
        destFilePath = replaceWhitespace(destFilePath);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFilePath), UTF_8))) {
            writer.write(text);
        } catch (IOException e) {
            log.warning("Arquivo não pode ser escrito ou encontrado. Favor verificar se a estrutura do diretório destino e arquivo estão corretos.");
            e.printStackTrace();
        }
    }

    /*
     * Método que substitui espaços em branco com enconding por whitespace
     */
    private static String replaceWhitespace(String path) {
        if (!StringUtils.isEmpty(path)) {
            return path.replaceAll("%20", " ");
        } else {
            return "";
        }
    }
    public static String getDestFolderFile() {
        String destFolderFile = System.getProperty("user.home") + DATA_OUT;

        log.info("Diretório de gravação de arquivo está em: " + destFolderFile);

        return replaceWhitespace(destFolderFile);
    }

    /**
     * Verifica se existe a posição do campo solicitada
     * @param arrRow - array com os valores dos objetos
     * @param pos - posição a ser obtida
     * @param strField
     * @return true / false
     */
    public static boolean existPositionField(String[] arrRow, int pos, String strField) {
        try {
            String valuePos = arrRow[pos];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            log.warning("Não foi encontrada nenhuma informação para o field "+ strField  + "");
        }
        return false;
    }
}
