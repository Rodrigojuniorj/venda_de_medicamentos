package br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {
}
