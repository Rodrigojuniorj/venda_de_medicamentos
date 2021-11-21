package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Compra;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Pagamento;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.CompraRepository;
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

    @Autowired
    private CompraRepository compraRepository;

    public void novaCompra(){
        compra = new Compra();
    }
    public void salvarCompra(){
        try{
            compraRepository.save(compra);
            Messages.addGlobalInfo("Compra salva com sucesso!");
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
        }catch(DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: o registro selecionado está vinculado com outros registros.");
        }
    }
    public void selecionarExclusao(Compra compra){
        Faces.setFlashAttribute("compra", compra);
        Faces.navigate("compra-exclusao.xhtml?faces-redirect=true");
    }

}
