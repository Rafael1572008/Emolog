package br.edu.ifsp.spo.java.controller.web;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/login")
public class LoginPageController {
    @GetMapping
    public String loginPage(HttpSession session){
        if (session.getAttribute("usuario") != null) {
            return "redirect:/";
        }
        return "login";
    }
}
