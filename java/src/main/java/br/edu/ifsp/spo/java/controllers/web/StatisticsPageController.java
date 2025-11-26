package br.edu.ifsp.spo.java.controllers.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsPageController {

    @GetMapping("/estatisticas")
    public String Statistics(HttpSession session) {

        // se NÃO existe usuário, manda para login
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        // se existe usuário, mostra a página
        return "statistics";
    }
}
