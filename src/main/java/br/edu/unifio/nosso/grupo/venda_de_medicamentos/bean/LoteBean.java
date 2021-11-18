package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Lote;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.LoteRepository;
import lombok.Data;
import org.omnifaces.util.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;

@Component
@ViewScoped
@Data
public class LoteBean {
    private Lote lote;

    @Autowired
    LoteRepository loteRepository;

    public void loteNovo(){
        lote = new Lote();
    }

    public void  salvarLote(){
        try {
            loteRepository.save(lote);
            Messages.addGlobalInfo("Lote salvo com sucesso!");
        }catch (DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: Já existe um Lote cadastrado com esse número.");
        }
    }
}
