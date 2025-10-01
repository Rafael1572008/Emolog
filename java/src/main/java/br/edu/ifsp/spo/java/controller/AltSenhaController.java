package br.edu.ifsp.spo.java.controller;

import br.edu.ifsp.spo.java.dto.response.LoginResponseDTO;
import br.edu.ifsp.spo.java.model.UsuarioModel;
import br.edu.ifsp.spo.java.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/novasenha")
public class AltSenhaController {
    private final UsuarioService usuarioService;

    // Construtor
    public AltSenhaController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public String changePassword(@RequestBody String senha,HttpSession session){
        String email = (String) session.getAttribute("usuario");
        Optional<UsuarioModel> usuarioOpt = usuarioService.findByEmail(email);
        usuarioOpt.get().setSenhaHash(senha);
        return "redirect:/";
    }
}

