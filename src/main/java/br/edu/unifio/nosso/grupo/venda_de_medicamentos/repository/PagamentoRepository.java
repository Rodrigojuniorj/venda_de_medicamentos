package br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
