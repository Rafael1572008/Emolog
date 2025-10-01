package br.edu.ifsp.spo.java.controllers;

import br.edu.ifsp.spo.java.dto.response.AlterarSenhaDTO;
import br.edu.ifsp.spo.java.model.UsuarioModel;
import br.edu.ifsp.spo.java.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String changePassword(@RequestBody AlterarSenhaDTO alterarSenha, HttpSession session, Model model){
        String email = (String) session.getAttribute("usuario");
        Optional<UsuarioModel> usuarioOpt = usuarioService.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            UsuarioModel usuario = usuarioOpt.get();
            usuario.setSenhaHash(alterarSenha.getSenhaNova());

            usuarioService.save(usuario);
        }

        return "redirect:/";
    }
}

