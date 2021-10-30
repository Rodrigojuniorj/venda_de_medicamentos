package br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotNull(message = "O campo data de horário é necessário")
    private LocalDateTime horario ;

    @NotBlank(message = "O campo Número do Cartão é necessário")
    @Size(min = 3, max = 50, message = "O tamanho do campo Número do Cartão deve ser entre 3 e 50 caracteres")
    private String numeroDoCartao;

    @ManyToOne
    @NotNull(message = "O campo compra é obrigatório")
    private Compra compra;
}
