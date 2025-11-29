package br.edu.ifsp.spo.java.controllers.web;

import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.service.HumorService;
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

    // Construtor
    public HomePageController(UsuarioService usuarioService, HumorService humorService) {
        this.usuarioService = usuarioService;
        this.humorService = humorService;
    }

    @GetMapping
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        String email = (String) session.getAttribute("usuario");
        Long idUser = usuarioService.findByEmail(email).get().getId();

        String nomeUsuario = usuarioService.findByEmail(email).get().getNome();
//        List<HumorModel> registros = humorService.findByIdUser(idUser);

        model.addAttribute("nome", nomeUsuario);

        return "index";
    }
}