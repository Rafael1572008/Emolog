package br.edu.ifsp.spo.java.controllers.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AltSenhaPageController {
    @GetMapping("/alterarsenha")
    public String alterarSenha(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "altSenha";
    }
}