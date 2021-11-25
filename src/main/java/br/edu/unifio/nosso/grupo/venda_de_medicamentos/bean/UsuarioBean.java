package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Usuario;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.UsuarioRepository;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.net.URI;
import java.util.List;

@Component
@ViewScoped
@Data
public class UsuarioBean {
    private Usuario usuario;
    private List<Usuario> usuarios;
    private boolean logged = false;
    @Autowired
    private UsuarioRepository usuarioRepository;

    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    public void novoUsuario() {
        usuario = new Usuario();
    }

    public void salvarUsuario() {
        try {
            usuarioRepository.save(usuario);
            Messages.addGlobalInfo("Usuário salvo com sucesso!");
            try {
                Faces.redirect("usuario-listar.xhtml?faces-redirect=true");
            } catch (Exception e) {

            }
        } catch (DataIntegrityViolationException e) {
            Messages.addGlobalWarn("Erro: Já existe um usuário cadastrado com esse nome.");
        }
    }

    public void car() {
        usuario = Faces.getFlashAttribute("usuario");

        if (usuario == null) {
            Faces.navigate("usuario-listar.xhtml?faces-redirect=true");
        }
    }

    public void selecionarExclusao(Usuario usuario) {
        Faces.setFlashAttribute("usuario", usuario);
        Faces.navigate("usuario-exclusao.xhtml?faces-redirect=true");
    }

    public void listarUsuario() {
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public void selecionarEdicao(Usuario usuario) {
        try {
            Faces.setFlashAttribute("usuario", usuario);
            Faces.redirect("usuario-editar.xhtml");
        } catch (Exception e) {

        }
    }

    public void retornarEdicao() {
        if (Faces.getFlashAttribute("usuario") != null) {
            usuario = Faces.getFlashAttribute("usuario");
        } else {
            Faces.navigate("usuario-listar.xhtml");
            Messages.addGlobalInfo("Nenhum usuário selecionado!");
        }
    }

    public void excluirUsuario() {
        try {
            usuarioRepository.deleteById(usuario.getCodigo());
            Messages.addFlashGlobalInfo("Usuário removido com sucesso.");
            Faces.navigate("usuario-listar.xhtml?faces-redirect=true");
        } catch (DataIntegrityViolationException e) {
            Messages.addGlobalWarn("Erro: o registro selecionado está vinculado com outros registros.");
        }
    }

    public void autenticar() {
        if (usuario != null) {
            boolean encontrado = false;
            Usuario usuarioEncontrado = new Usuario();
            Usuario entradaUsuario = usuario;
            HttpSession session = request.getSession();
            Example<Usuario> informacaoLogin = Example.of(entradaUsuario);
            Iterable<Usuario> resultadoQuery = usuarioRepository.findAll(informacaoLogin);
            for (Usuario e : resultadoQuery) {
                encontrado = true;
                usuarioEncontrado = e;
            }
            if (encontrado == true) {
                if (session.getAttribute("sessao") == null) {
                    session.setAttribute("sessao", usuarioEncontrado);
                    Messages.addFlashGlobalInfo("Logado com sucesso!");
                    Faces.navigate("usuario-listar.xhtml?faces-redirect=true");
                } else {
                    Messages.addFlashGlobalInfo("Sessão já estava iniciada!");
                    Faces.navigate("usuario-listar.xhtml?faces-redirect=true");
                }
            } else {
                Messages.addFlashGlobalError("Usuário não encontrado!");
            }
        }
    }


    public void verificarSessao() {
        HttpSession session = request.getSession(false);
        String caminho = request.getRequestURI();
        if (session != null) {
            Usuario sessionUsuario = (Usuario) session.getAttribute("sessao");
            if (session.getAttribute("sessao") != null) {
                logged = true;
            } else {
                logged = false;
                System.out.println(caminho);



            }
        }
    }

    public void logar() {
        try {
            Faces.navigate("login.xhtml");
        } catch (Exception e) {

        }

    }

    public void deslogar() {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
            try {
                Faces.redirect("login.xhtml");
            } catch (Exception e) {

            }
        }
    }

    public boolean getlogged() {
        return logged;
    }
}
