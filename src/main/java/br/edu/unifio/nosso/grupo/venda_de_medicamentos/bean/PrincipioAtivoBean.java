package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.PrincipioAtivo;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.PrincipioAtivoRepository;
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
public class PrincipioAtivoBean {
    private PrincipioAtivo principioAtivo;
    private List<PrincipioAtivo> principiosAtivos;
    @Autowired
    private PrincipioAtivoRepository principioAtivoRepository;

    public void novoPrincipioAtivo(){
        principioAtivo = new PrincipioAtivo();
    }

    public void salvarPrincipioAtivo(){
        try{
            principioAtivoRepository.save(principioAtivo);
            Messages.addGlobalInfo("Principio Ativo salvo com sucesso!");
        }catch (DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: Já existe um Principio Ativo cadastrado com esse nome.");
        }
    }

    public void listarPrincipioAtivo(){
        principiosAtivos = principioAtivoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public void selecionarEdicao(PrincipioAtivo principioAtivo) {
        try {
            Faces.setFlashAttribute("principioativo", principioAtivo);
            Faces.redirect("principioativo-edicao.xhtml");
        } catch (Exception e) {

        }
    }
    public void retornarEdicao() {
        if (Faces.getFlashAttribute("principioativo") != null) {
            principioAtivo = Faces.getFlashAttribute("principioativo");
        } else {
            try{
                Faces.redirect("principioativo-lista.xhtml");
                Messages.addGlobalInfo("Nenhum principio ativo selecionado!");
            }catch (Exception e){

            }
        }
    }
    public void excluirPrincipioAtivo(){
        try{
            principioAtivoRepository.deleteById(principioAtivo.getCodigo());
            Messages.addFlashGlobalInfo("Principio ativo removido com sucesso.");
        }catch(DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: o registro selecionado está vinculado com outros registros.");
        }
    }
}
