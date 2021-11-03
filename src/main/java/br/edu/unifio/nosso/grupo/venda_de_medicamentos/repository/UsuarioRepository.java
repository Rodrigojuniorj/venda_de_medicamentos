package br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
