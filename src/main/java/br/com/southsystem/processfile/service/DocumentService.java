package br.com.southsystem.processfile.service;

import br.com.southsystem.processfile.model.dat.Document;
import br.com.southsystem.processfile.model.people.Customer;
import br.com.southsystem.processfile.model.people.Salesman;
import br.com.southsystem.processfile.model.sale.Item;
import br.com.southsystem.processfile.model.sale.Sale;
import br.com.southsystem.processfile.utilities.Utilities;
import lombok.extern.java.Log;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@Log
public class DocumentService {

    private static final String COLCHETES = "[\\[|\\]]";

    // não terá getter e setter, pois essa propriedade não deve ser alterado do lado de fora do objeto
    private String filePath;

    // não terá getter e setter, pois essa propriedade não deve ser alterado do lado de fora do objeto
    private final Document document = new Document();

    public DocumentService(String filePath) {
        this.filePath = filePath;
    }

    public Document doDocument(String fileText) throws IOException {

        if (StringUtils.isEmpty(fileText)) {
            throw new IOException(
                    "Não foi possível realizar leitura do arquivo. Favor verificar os dados do arquivo em: " + this.filePath);
        }

        List<String> rows =  Arrays.asList(fileText.trim().split("\n"));

        // carrega a lista de clientes
        loadCustomerList(rows);

        // carrega a lista de vendedores
        loadSalesman(rows);

        // carrega a lista de vendas
        loadSalesList(rows);
        return document;
    }

    void loadSalesList(List<String> rows) {

        List<String> lsRowsSale = new ArrayList<>();

        // separando os arquivos de dados das vendas
        rows.stream().filter(row -> row.startsWith("003")).forEach(lsRowsSale::add);

        if (lsRowsSale.isEmpty()) {
            log.warning("Não foram encontrados dados de vendas. Verifique o arquivo: " + this.filePath);
            return;
        }

        List<Sale> lsSale = new ArrayList<>(lsRowsSale.size());

        lsRowsSale.forEach(row -> {
            // realizando o split de dados da venda
            String[] arrSaleData = row.split("ç");

            Sale sale = new Sale();

            if (Utilities.existPositionField(arrSaleData, 1, "saleId da Venda")) {
                sale.setSaleId(arrSaleData[1]);
            }
            if (Utilities.existPositionField(arrSaleData, 3, "salesmanName da Venda")) {
                sale.setSalesmanName(arrSaleData[3]);
            }

            // Carregando a lista de itens
            if (Utilities.existPositionField(arrSaleData, 2, "soldItem da Venda")) {
                // Criando array com dados do item
                String[] arrDataSale = arrSaleData[2].replaceAll(COLCHETES, "").trim().split(",");

                List<Item> lsSoldItem = loadItem(arrDataSale);

                sale.setSoldItems(lsSoldItem);

                // Realiza o cálculo para identificar o valor total da venda
                doCalcTotalSaleValue(sale);
            }
            lsSale.add(sale);
        });

        // ordenando lista de vendas pelo valor da venda
        lsSale.sort(Comparator.comparing(Sale::getTotalSaleValue));

        // identificando a venda realizada com maior valor
        Sale mostExpensiveSale = lsSale.get(lsSale.size()-1);

        // setando o ID da venda com a maior venda
        document.setMostExpensiveSaleId(mostExpensiveSale.getSaleId());

        // setando o nome do pior vendedor
        document.setNameWorstSeller(lsSale.get(0).getSalesmanName());
    }

    /*
     * Realiza calculo do valor total da venda
     * @param sale
     */
    void doCalcTotalSaleValue(Sale sale) {
        sale.getSoldItems().forEach(soldItem -> {
            BigDecimal totalValue = sale.getTotalSaleValue();
            if (totalValue == null) {
                totalValue = new BigDecimal(soldItem.getItemPrice().longValue());
            } else {
                totalValue = totalValue.add(soldItem.getItemPrice());
            }
            sale.setTotalSaleValue(totalValue);
        });
    }

    List<Item> loadItem(String[] arrItemData) {
        List<String> lsDataItem = Arrays.asList(arrItemData);

        if (lsDataItem.isEmpty()) {
            log.warning("Não foram encontrados dados de itens na venda. Verifique o arquivo: " + this.filePath);
            return null;
        }
        List<Item> lsItem = new ArrayList<>(lsDataItem.size());

        // percorrendo array de dados dos itens
        lsDataItem.forEach(rowItem -> {
            // Realizando os split de dados de um item
            String[] arrLineItemData = rowItem.split("-");

            Item item = new Item();
            // Item ID
            if (Utilities.existPositionField(arrLineItemData, 0, "itemId do item")) {
                try {
                    item.setItemId(Integer.parseInt(arrLineItemData[0]));
                } catch (NumberFormatException n) {
                    log.warning("O identificador do item está em formato inválido");
                }

            }
            // Item Quantity
            if (Utilities.existPositionField(arrLineItemData, 1, "quantifyItem do item")) {
                try {
                    item.setQuantityItem(Integer.parseInt(arrLineItemData[1]));
                } catch (NumberFormatException n) {
                    log.warning("A quantidade do item está em formato inválido");
                }
            }
            // Item Price
            if (Utilities.existPositionField(arrLineItemData, 2, "itemPrice do item")) {
                try {
                    item.setItemPrice(new BigDecimal(arrLineItemData[2]));
                } catch (NumberFormatException n) {
                    item.setItemPrice(new BigDecimal("00.00"));
                    log.warning("O preço do item está em formato inválido");
                }
            }

            lsItem.add(item);
        });

        return lsItem;
    }

    void loadSalesman(List<String> rows) {

        List<String> lsRowsSalesman = new ArrayList<>();

        // separando os arquivos de dados do vendedor
        rows.stream().filter(row -> row.startsWith("001")).forEach(lsRowsSalesman::add);

        if (lsRowsSalesman.isEmpty()) {
            log.warning("Não foram encontrados dados de vendedor. Verificar o arquivo: " + this.filePath);
            return;
        }

        List<Salesman> lsSalesman = new ArrayList<>(lsRowsSalesman.size());

        lsRowsSalesman.forEach(row -> {
            // realizando o split de dados do vendedor pelo ç, pois é o divisor de campos do objeto
            String[] arrDataSalesman = row.split("ç");

            Salesman salesman = new Salesman();

            if (Utilities.existPositionField(arrDataSalesman, 1, "cpf vendedor")) {
                salesman.setCpf(arrDataSalesman[1]);
            }
            if (Utilities.existPositionField(arrDataSalesman, 2, "name vendedor")) {
                salesman.setName(arrDataSalesman[2]);
            }
            if (Utilities.existPositionField(arrDataSalesman, 3, "salary vendedor")) {
                salesman.setSalary(arrDataSalesman[3]);
            }

            lsSalesman.add(salesman);
        });

        document.setTotalSalesman(lsSalesman.size());
    }

    void loadCustomerList(List<String> rows) {

        List<String> lsRowsCustomer = new ArrayList<>();

        // separando os arquivos de dados do cliente
        rows.stream().filter(row -> row.startsWith("002")).forEach(lsRowsCustomer::add);

        if (lsRowsCustomer.isEmpty()) {
            log.warning("Não foram encontrados dados de cliente. Favor verifique o arquivo: " + this.filePath);
            return;
        }

        List<Customer> lsCustomer = new ArrayList<>(lsRowsCustomer.size());

        lsRowsCustomer.forEach(row -> {
            // realizando o split de dados do cliente pelo ç, pois é o divisor de campos do objeto
            String[] arrDataCustomer = row.split("ç");

            Customer customer = new Customer();

            if (Utilities.existPositionField(arrDataCustomer, 1, "CNPJ do cliente")) {
                customer.setCnpj(arrDataCustomer[1]);
            }
            if (Utilities.existPositionField(arrDataCustomer, 2, "Name do cliente")) {
                customer.setName(arrDataCustomer[2]);
            }
            if (Utilities.existPositionField(arrDataCustomer, 3, "businessArea do cliente")) {
                customer.setBusinessArea(arrDataCustomer[3]);
            }

            lsCustomer.add(customer);
        });

        document.setTotalCustomer(lsCustomer.size());
    }
}
