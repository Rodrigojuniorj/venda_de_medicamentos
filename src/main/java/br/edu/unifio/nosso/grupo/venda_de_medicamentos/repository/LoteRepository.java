package br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoteRepository extends JpaRepository<Lote, Integer> {
}
