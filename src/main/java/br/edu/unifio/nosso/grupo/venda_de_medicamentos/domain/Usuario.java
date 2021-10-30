package br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nome"})
})
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @NotBlank(message = "O campo nome é necessário")
    @Size(min = 3, max = 50, message = "O tamanho do campo nome deve ser entre 3 e 50 caracteres")
    private String nome;

    @NotBlank(message = "O campo CPF é necessário")
    private String cpf;

    @NotBlank(message = "O campo E-mail é necessário")
    @Size(min = 3, max = 50, message = "O tamanho do campo E-mail deve ser entre 3 e 50 caracteres")
    private String email;

    @NotBlank(message = "O campo Senha é necessário")
    @Size(min = 3, max = 50, message = "O tamanho do campo Senha deve ser entre 3 e 50 caracteres")
    private String senha;

}
