package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Laboratorio;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Lote;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.LaboratorioRepository;
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
public class LaboratorioBean {

    Laboratorio laboratorio;
    List<Laboratorio> laboratorios;

    @Autowired
    LaboratorioRepository laboratorioRepository;

    public void novoLaboratorio(){
        laboratorio = new Laboratorio();
    }

    public void salvarLaboratorio(){
        try{
            laboratorioRepository.save(laboratorio);
            Messages.addGlobalInfo("Laboratorio salvo com sucesso!");
            Faces.navigate("laboratorio-listar.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: Já existe um laboratório cadastrado com esse nome.");
        }
    }

    public void listarLaboratorios(){
        laboratorios = laboratorioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }
    public void selecionarEdicao(Laboratorio laboratorio){
        try {
            Faces.setFlashAttribute("laboratorio", laboratorio);
            Faces.redirect("laboratorio-editar.xhtml");
        } catch (Exception e) {

        }
    }
    public void selecionarExclusao(Laboratorio laboratorio){
        Faces.setFlashAttribute("laboratorio", laboratorio);
        Faces.navigate("laboratorio-exclusao.xhtml?faces-redirect=true");
    }

    public void carreg(){
        laboratorio = Faces.getFlashAttribute("laboratorio");

        if(laboratorio == null){
            Faces.navigate("laboratorio-lista.xhtml?faces-redirect=true");
        }
    }

    public void retornarEdicao(){
        if (Faces.getFlashAttribute("laboratorio") != null) {
            laboratorio = Faces.getFlashAttribute("laboratorio");
        } else {
            try{
                Faces.redirect("laboratorio-lista.xhtml");
                Messages.addGlobalInfo("Nenhum principio ativo selecionado!");
            }catch (Exception e){

            }
        }
    }
    public void excluirLaboratorio(){
        try{
            laboratorioRepository.deleteById(laboratorio.getCodigo());
            Messages.addFlashGlobalInfo("Laboratorio removido com sucesso.");

            Faces.navigate("laboratorio-listar.xhtml?faces-redirect=true");
        }catch(DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: o registro selecionado está vinculado com outros registros.");
        }
    }
}
