package br.com.southsystem.processfile.model.people;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class Salesman extends People {

    private String cpf;

    // TODO: Mudar para double e corrigir cargas
    private String salary;

    // Implementação para expor o name super classe
    @ToString.Include(name = "name")
    public String getName() {
        return super.getName();
    }
}
