package br.edu.ifsp.spo.java.controllers.web;

import br.edu.ifsp.spo.java.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class HomePageController {
    private final UsuarioService usuarioService;

    // Construtor
    public HomePageController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        String email = (String) session.getAttribute("usuario");
        String nomeUsuario = usuarioService.findByEmail(email).get().getNome();
        model.addAttribute("nome", nomeUsuario);

        return "index";
    }
}