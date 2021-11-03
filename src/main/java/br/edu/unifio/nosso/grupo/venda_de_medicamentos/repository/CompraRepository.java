package br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
}
