package br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotNull(message = "O campo data de horário é necessário")
    private LocalDateTime horario ;

    @NotNull(message = "O campo Preço Total é obrigatório")
    private Double precoTotal;

    @ManyToOne
    @NotNull(message = "O campo usuário é obrigatório")
    private Usuario usuario;

    @ManyToOne
    @NotNull(message = "O campo lote é obrigatório")
    private Lote lote;
}
