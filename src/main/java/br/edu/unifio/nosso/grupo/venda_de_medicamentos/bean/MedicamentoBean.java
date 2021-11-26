package br.edu.unifio.nosso.grupo.venda_de_medicamentos.bean;

import br.edu.unifio.nosso.grupo.venda_de_medicamentos.domain.*;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.LaboratorioRepository;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.LoteRepository;
import br.edu.unifio.nosso.grupo.venda_de_medicamentos.repository.MedicamentoRepository;
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
public class MedicamentoBean {
    private Medicamento medicamento;
    private List<Lote> lotes;
    private List<PrincipioAtivo> principioAtivos;
    private List<Medicamento> medicamentos;
    private List<Laboratorio> laboratorios;

    @Autowired
    private MedicamentoRepository medicamentoRepository;
    @Autowired
    private PrincipioAtivoRepository principioAtivoRepository;
    @Autowired
    private LaboratorioRepository laboratorioRepository;

    public void novoMedicamento(){
        medicamento = new Medicamento();
        principioAtivos = principioAtivoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        laboratorios = laboratorioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }
    public void salvarMedicamento(){
        try{
            medicamentoRepository.save(medicamento);
            Messages.addGlobalInfo("Medicamento salvo com sucesso!");
            Faces.navigate("medicamento-listar.xhtml?faces-redirect=true");
        }catch (DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: Já existe um Medicamento cadastrado com esse nome.");
        }
    }
    public void listarMedicamento(){
        medicamentos = medicamentoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }
    public void selecionarEdicao(Medicamento medicamento) {
            Faces.setFlashAttribute("medicamento", medicamento);
            Faces.navigate("medicamento-editar.xhtml?faces-redirect=true");
    }
    public void retornarEdicao() {
        if (Faces.getFlashAttribute("medicamento") != null) {
            medicamento = Faces.getFlashAttribute("medicamento");
            principioAtivos = principioAtivoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
            laboratorios = laboratorioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        } else {
            try{
                Faces.redirect("medicamento-listar.xhtml");
                Messages.addGlobalInfo("Nenhum medicamento selecionado!");
            }catch (Exception e){

            }
        }
    }

    public void excluirMedicamento(){
        try{
            medicamentoRepository.deleteById(medicamento.getCodigo());
            Messages.addFlashGlobalInfo("Medicamento removido com sucesso.");
            Faces.navigate("medicamento-listar.xhtml?faces-redirect=true");
        }catch(DataIntegrityViolationException e){
            Messages.addGlobalWarn("Erro: o registro selecionado está vinculado com outros registros.");
        }
    }

    public void selecionarExclusao(Medicamento medicamento){
        Faces.setFlashAttribute("medicamento", medicamento);
        Faces.navigate("medicamento-exclusao.xhtml?faces-redirect=true");
    }
    public void retornarExclusao(){
        medicamento = Faces.getFlashAttribute("medicamento");
        principioAtivos = principioAtivoRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        laboratorios = laboratorioRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
        if(medicamento == null){
            Faces.navigate("medicamento-listar.xhtml?faces-redirect=true");
        }
    }

}
