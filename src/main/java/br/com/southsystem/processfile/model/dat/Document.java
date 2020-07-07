package br.com.southsystem.processfile.model.dat;


import lombok.Data;

@Data
public class Document {

    // Quantidade de clientes no arquivo de entrada
    private Integer totalCustomer;

    // Quantidade de vendedor no arquivo de entrada
    private Integer totalSalesman;

    // ID da venda mais cara
    private String mostExpensiveSaleId;

    // Pior vendedor
    private String nameWorstSeller;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Qtd. Clientes:           ").append(getTotalCustomer()).append("\n");
        sb.append("Qtd. de Vendedores:      ").append(getTotalSalesman()).append("\n");
        sb.append("ID Venda mais cara:      ").append(getMostExpensiveSaleId()).append("\n");
        sb.append("Nome pior vendedor:      ").append(getNameWorstSeller());
        return sb.toString();
    }
}