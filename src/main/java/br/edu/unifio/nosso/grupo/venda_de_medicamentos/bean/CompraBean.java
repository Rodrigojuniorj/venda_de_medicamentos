package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Compra;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Lote;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Pagamento;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Usuario;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.CompraRepository;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.LoteRepository;
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
public class CompraBean {
    private Compra compra;
    private List<Compra> compras;
    private List<Usuario> usuarios;
    private List<Lote> lotes;

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LoteRepository loteRepository;

    public void novaCompra(){
        compra = new Compra();
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        lotes = loteRepository.findAll(Sort.by(Sort.Direction.ASC, "codigo"));
    }
    public void salvarCompra(){
        try{
            compraRepository.save(compra);
            Messages.addGlobalInfo("Compra salva com sucesso!");
            Faces.navigate("compra-listar.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: Já existe uma compra cadastrada com esse código.");
        }
    }
    public void listarCompra(){
        compras = compraRepository.findAll(Sort.by(Sort.Direction.ASC, "horario"));
    }
    public void selecionarEdicao(Compra compra) {
        try {
            Faces.setFlashAttribute("compra", compra);
            Faces.redirect("compra-editar.xhtml");
        } catch (Exception e) {

        }

    }
    public void retornarEdicao() {
        if (Faces.getFlashAttribute("compra") != null) {
            compra = Faces.getFlashAttribute("compra");
            usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
            lotes = loteRepository.findAll(Sort.by(Sort.Direction.ASC, "codigo"));
        } else {
            try{
                Faces.redirect("compra-lista.xhtml");
                Messages.addGlobalInfo("Nenhuma compra selecionada!");
            }catch (Exception e){

            }
        }
    }
    public void excluirCompra(){
        try{
            compraRepository.deleteById(compra.getCodigo());
            Messages.addFlashGlobalInfo("Compra removida com sucesso.");
            Faces.navigate("compra-listar.xhtml?faces-redirect=true");
        }catch(DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: o registro selecionado está vinculado com outros registros.");
        }
    }
    public void selecionarExclusao(Compra compra){
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        lotes = loteRepository.findAll(Sort.by(Sort.Direction.ASC, "codigo"));
        Faces.setFlashAttribute("compra", compra);
        Faces.navigate("compra-exclusao.xhtml?faces-redirect=true");
    }
    public void retornarExclusao(){
        compra = Faces.getFlashAttribute("compra");
        usuarios = usuarioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        lotes = loteRepository.findAll(Sort.by(Sort.Direction.ASC, "codigo"));

        if(compra == null){
            Faces.navigate("compra-listar.xhtml?faces-redirect=true");
        }
    }
}
