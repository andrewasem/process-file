package br.com.southsystem.processfile.model.people;

import lombok.Data;
import lombok.ToString;

@Data
public class Customer extends People {

    private String cnpj;

    private String businessArea;

    // Implementação para expor o name super classe
    @ToString.Include(name = "name")
    public String getName() {
        return super.getName();
    }
}
