package br.edu.ifsp.spo.java.controllers.web;

import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.model.TagModel;
import br.edu.ifsp.spo.java.service.HumorService;
import br.edu.ifsp.spo.java.service.TagService;
import br.edu.ifsp.spo.java.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomePageController {
    private final UsuarioService usuarioService;
    private final HumorService humorService;
    private final TagService tagService;


    // Construtor
    public HomePageController(UsuarioService usuarioService, HumorService humorService, TagService tagService) {
        this.usuarioService = usuarioService;
        this.humorService = humorService;
        this.tagService = tagService;
    }

    @GetMapping
    public String homePage(HttpSession session, Model model) {
        // Se não tem usuário logado → login
        String email = (String) session.getAttribute("usuario");
        Long idUser = (Long) session.getAttribute("idUser");

        if (email == null || idUser == null) {
            return "redirect:/login";
        }

        // Busca o usuário de forma segura
        var usuarioOpt = usuarioService.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            session.invalidate();
            return "redirect:/login";
        }

        String nomeUsuario = usuarioOpt.get().getNome();

        // Busca registros SEM lançar erro se a lista estiver vazia
        List<HumorModel> registros = humorService.findByIdUser(idUser);
        if (registros == null) {
            registros = List.of(); // garante lista vazia
        }

        List<TagModel> tags = tagService.findByIdUser(idUser);
        if (tags == null) {
            tags = List.of(); // garante lista vazia
        }

        model.addAttribute("todasTags", tags);
        model.addAttribute("nome", nomeUsuario);
        model.addAttribute("registros", registros);

        return "index";
    }
}