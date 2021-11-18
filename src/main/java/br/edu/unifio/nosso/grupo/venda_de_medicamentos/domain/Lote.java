package br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "O campo número é necessário")
    @Size(min = 3, max = 50, message = "O tamanho do campo número deve ser entre 3 e 50 caracteres")
    private String numero ;

    @NotNull(message = "O campo data de fabricação é necessário")
    @PastOrPresent(message = "A data de fabricação informada deve ser uma data presente ou passada")
    private LocalDate dataDeFabricacao ;

    @NotNull(message = "O campo data de validade é necessário")
    @FutureOrPresent(message = "A data de validade informada deve ser uma data presente ou futura")
    private LocalDate dataDeValidade;
}
