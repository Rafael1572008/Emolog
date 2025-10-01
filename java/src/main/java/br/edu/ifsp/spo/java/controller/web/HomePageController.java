package br.edu.ifsp.spo.java.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
public class HomePageController {
    @GetMapping
    public String homePage(HttpSession session, Model model) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        String usuario = (String) session.getAttribute("usuario");
        model.addAttribute("nome", usuario);

        return "index";
    }
}