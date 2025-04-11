package web.controlevacinacao.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.controlevacinacao.filter.VacinaFilter;
import web.controlevacinacao.model.Vacina;
import web.controlevacinacao.repository.VacinaRepository;
import web.controlevacinacao.service.VacinaService;

import java.util.List;

@Controller
public class VacinaController {

    private static final Logger logger = LoggerFactory.getLogger(VacinaController.class);

    private VacinaRepository vacinaRepository;
    private VacinaService vacinaService;

    public VacinaController(VacinaRepository vacinaRepository, VacinaService vacinaService) {
        this.vacinaRepository = vacinaRepository;
        this.vacinaService = vacinaService;
    }

    @GetMapping("/vacinas")
    public String mostraVacinas(Model model) {
        List<Vacina> vacinas = vacinaRepository.findAll();

        model.addAttribute("vacinas", vacinas);

        return "vacinas/listar";
    }

    @GetMapping("/vacinas/pesquisar")
    public String abrirPesquisar() {
        return "vacinas/pesquisar";
    }

    @PostMapping("/vacinas/pesquisar")
    public String pesquisar(VacinaFilter vacina, Model model) {

       List<Vacina> vacinas = vacinaRepository.pesquisar(vacina);
       logger.trace("Vacinas encontradas: {}", vacinas);

        model.addAttribute("vacinas", vacinas);

        return "vacinas/listar";
    }

    @GetMapping("/vacinas/cadastrar")
    public String abrirCadastro(Vacina vacina) {
        return "vacinas/cadastrar";
    }

    @PostMapping("/vacinas/cadastrar")
    public String cadastrar(Vacina vacina, RedirectAttributes atributos) {
        vacinaService.salvar(vacina);
        atributos.addAttribute("mensagem", "Vacina cadastrada com sucesso!");
        return "redirect:/mensagem";
    }

    @GetMapping("/mensagem")
    public String mostrarMensagem(String mensagem, Model model){
        model.addAttribute("mensagem", mensagem);
        return "mensagem";
    }

    @GetMapping("/vacinas/alterar/{codigo}")
    public String abrirAlterar(@PathVariable("codigo") Long codigo, Model model) {

        Vacina vacina = vacinaRepository.findById(codigo).orElse(null);

        if(vacina != null) {
            model.addAttribute("vacina", vacina);
            return "vacinas/alterar";
        } else {
            model.addAttribute("mensagem", "Vacina não encontrada!");
            return "mensagem";
        }
    }

    @PostMapping("/vacinas/alterar")
    public String alterar(Vacina vacina, RedirectAttributes atributos) {
        vacinaService.alterar(vacina);
        atributos.addAttribute("mensagem", "Vacina alterada com sucesso!");
        return "redirect:/mensagem";
    }
}
