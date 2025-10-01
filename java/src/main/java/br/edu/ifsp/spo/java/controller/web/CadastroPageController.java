package br.edu.ifsp.spo.java.controller.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadastroPageController {

    @GetMapping("/cadastro")
    public String cadastroPage(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/";
        }
        return "cadastro";
    }
}