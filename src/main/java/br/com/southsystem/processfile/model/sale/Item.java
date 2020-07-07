package br.com.southsystem.processfile.model.sale;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
// Equals e HashCode somente no identificador
@EqualsAndHashCode(of = "itemId")
public class Item {

    // Item ID
    private Integer itemId;

    // Quantity-Item
    private Integer quantityItem;

    // Item Price
    private BigDecimal itemPrice;
}
