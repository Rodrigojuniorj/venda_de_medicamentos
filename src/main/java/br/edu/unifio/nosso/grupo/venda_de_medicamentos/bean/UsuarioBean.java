package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.PrincipioAtivo;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Usuario;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.UsuarioRepository;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;
import java.util.List;

@Component
@ViewScoped
@Data
public class UsuarioBean {
    private Usuario usuario;
    private List<Usuario> usuarios;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void novoUsuario(){
        usuario = new Usuario();
    }

    public void salvarUsuario(){
        try{
            usuarioRepository.save(usuario);
            Messages.addGlobalInfo("Usuário salvo com sucesso!");
        }catch (DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: Já existe um usuário cadastrado com esse nome.");
        }
    }

    public void listarUsuario(){
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public void selecionarEdicao(Usuario usuario) {
        try {
            Faces.setFlashAttribute("usuario", usuario);
            Faces.redirect("usuario-edicao.xhtml");
        } catch (Exception e) {

        }
    }
    public void retornarEdicao() {
        if (Faces.getFlashAttribute("usuario") != null) {
            usuario = Faces.getFlashAttribute("usuario");
        } else {
            try{
                Faces.redirect("usuario-lista.xhtml");
                Messages.addGlobalInfo("Nenhum usuário selecionado!");
            }catch (Exception e){

            }
        }
    }
    public void excluirUsuario(){
        try{
            usuarioRepository.deleteById(usuario.getCodigo());
            Messages.addFlashGlobalInfo("Usuário removido com sucesso.");
        }catch(DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: o registro selecionado está vinculado com outros registros.");
        }
    }
}
