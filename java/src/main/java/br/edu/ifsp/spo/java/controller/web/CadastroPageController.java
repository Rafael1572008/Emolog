package br.edu.ifsp.spo.java.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CadastroPageController {

    @GetMapping("/cadastro")
    public String cadastroPage() {
        return "cadastro";
    }
}