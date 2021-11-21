package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;



import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.Pagamento;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.PagamentoRepository;
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

public class PagamentoBean {
    private Pagamento pagamento;
    private List<Pagamento> pagamentos;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public void novoPagamento(){
        pagamento = new Pagamento();
    }

    public void salvarPagamento(){
        try{
            pagamentoRepository.save(pagamento);
            Messages.addGlobalInfo("Pagamento salvo com sucesso!");
        }catch (DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: Já existe um Pagamento cadastrado com esse código.");
        }
    }
    public void listarPagamento(){
        pagamentos = pagamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "horario"));
    }

    public void selecionarEdicao(Pagamento pagamento) {
        try {
            Faces.setFlashAttribute("pagamento", pagamento);
            Faces.redirect("pagamento-editar.xhtml");
        } catch (Exception e) {

        }

    }
    public void retornarEdicao() {
        if (Faces.getFlashAttribute("pagamento") != null) {
            pagamento = Faces.getFlashAttribute("pagamento");
        } else {
            try{
                Faces.redirect("pagamento-lista.xhtml");
                Messages.addGlobalInfo("Nenhum pagamento selecionado!");
            }catch (Exception e){

            }
        }
    }
    public void excluirPagamento(){
        try{
            pagamentoRepository.deleteById(pagamento.getCodigo());
            Messages.addFlashGlobalInfo("Pagamento removido com sucesso.");
        }catch(DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: o registro selecionado está vinculado com outros registros.");
        }
    }

    public void selecionarExclusao(Pagamento pagamento){
        Faces.setFlashAttribute("pagamento", pagamento);
        Faces.navigate("pagamento-exclusao.xhtml?faces-redirect=true");
    }




}
