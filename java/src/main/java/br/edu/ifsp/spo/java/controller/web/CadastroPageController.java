package br.edu.ifsp.spo.java.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadastroPageController {

    @GetMapping("/cadastro")
    public String showCadastroPage() {
        return "cadastro"; // Retorna o arquivo cadastro.html em src/main/resources/templates/
    }
}