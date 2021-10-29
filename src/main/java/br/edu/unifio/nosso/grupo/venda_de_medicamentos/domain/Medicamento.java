package br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome"})
})

@Data
public class Medicamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "O campo nome é necessário")
    @Size(min = 3, max = 50, message = "O tamanho do campo nome deve ser entre 3 e 50 caracteres")
    private String nome;

    @NotNull(message = "O campo Preço Unitário é obrigatório")
    @DecimalMin(value = "0.01", message = "o valor minimo é 0")
    @DecimalMax(value = "1000.00", message = "o valor máximo para esse campo é 1000.00")
    private Double precoUnitario;

    @ManyToOne
    @NotNull(message = "O campo laboratório é obrigatória")
    private Laboratorio laboratorio;

    @ManyToOne
    @NotNull(message = "O campo Príncipio Ativo é obrigatória")
    private PrincipioAtivo principioAtivo;
}
