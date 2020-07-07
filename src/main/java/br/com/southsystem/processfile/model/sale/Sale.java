package br.com.southsystem.processfile.model.sale;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
// Equals e HashCode somente no identificador
@EqualsAndHashCode(of = "saleId")
public class Sale {

    private String saleId;

    private List<Item> soldItems;

    private String SalesmanName;

    private BigDecimal totalSaleValue;
}