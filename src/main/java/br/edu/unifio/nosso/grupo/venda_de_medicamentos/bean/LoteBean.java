package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Lote;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Medicamento;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.LoteRepository;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.MedicamentoRepository;
import lombok.Data;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.faces.view.ViewScoped;
import java.util.List;

@Component
@ViewScoped
@Data
public class LoteBean {
    private Lote lote;
    private List<Lote> lotes;
    private List<Medicamento> medicamentos;

    @Autowired
    LoteRepository loteRepository;

    @Autowired
    MedicamentoRepository  medicamentoRepository;
    public void loteNovo(){
        lote = new Lote();
        medicamentos = medicamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    public void listar(){
        lotes = loteRepository.findAll(Sort.by(Sort.Direction.ASC, "dataDeValidade"));

    }

    public void selecionarEdicao(Lote lote) {
        Faces.setFlashAttribute("lote", lote);
        Faces.navigate("lote-editar.xhtml?faces-redirect=true");
    }

    public void selecionarExclusao(Lote lote){
        Faces.setFlashAttribute("lote", lote);
        Faces.navigate("lote-exclusao.xhtml?faces-redirect=true");
    }

    public void carregar(){
        lote = Faces.getFlashAttribute("lote");
        medicamentos = medicamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        if(lote == null){
            Faces.navigate("lote-listar.xhtml?faces-redirect=true");
        }
    }

    public void excluir(){
        try{
            loteRepository.deleteById(lote.getCodigo());
            Messages.addFlashGlobalInfo("Lote removido com sucesso");

            Faces.navigate("lote-listar.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException excecao){
            Messages.addGlobalError("Lote não foi removido!");
        }
    }

    public void  salvarLote(){
        try {
            loteRepository.save(lote);
            Messages.addGlobalInfo("Lote salvo com sucesso!");

            Faces.navigate("lote-listar.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: Já existe um Lote cadastrado com esse número.");
        }
    }
}
