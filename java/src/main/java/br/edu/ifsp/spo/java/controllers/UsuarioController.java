package br.edu.ifsp.spo.java.controllers;

import br.edu.ifsp.spo.java.dto.response.LoginResponseDTO;
import br.edu.ifsp.spo.java.model.UsuarioModel;
import br.edu.ifsp.spo.java.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // Construtor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    // Obter todos os usuarios
    @GetMapping
    public List<UsuarioModel> getAll() { return usuarioService.getAll(); }


    // Criar Usuario
    @PostMapping
    public ResponseEntity<UsuarioModel> create(@RequestBody UsuarioModel usuarioModel) {
        String email = usuarioModel.getEmail();
        if (usuarioService.findByEmail(email).isPresent()){
            return ResponseEntity.status(401).build();
        }

        UsuarioModel criado = usuarioService.save(usuarioModel);
        return ResponseEntity.status(201).body(criado);
    }

    /// Sub-Rotas

    // Rota Para pesquisar por Id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioModel> getById(@PathVariable Long id) {
        Optional<UsuarioModel> usuarioOpt = usuarioService.findById(id);
        return usuarioOpt
                .map(ResponseEntity::ok) // retorna 200 (ok) com o objeto
                .orElse(ResponseEntity.notFound().build()); // retorna 404 se não encontrado
    }

    // Buscar por email e senha
    @PostMapping("/login")
    public ResponseEntity<UsuarioModel> getbyEmailandSenha(@RequestBody LoginResponseDTO loginResponse, HttpSession session){
        Optional<UsuarioModel> usuarioOpt = usuarioService.findByEmail(loginResponse.getEmail());

        if (usuarioOpt.isPresent() && usuarioOpt.get().getSenhaHash().equals(loginResponse.getSenha())) {
            session.setAttribute("usuario", loginResponse.getEmail());
            session.setAttribute("idUser", usuarioOpt.get().getId());  // adiciona o ID (Thiago)
            return ResponseEntity.ok(usuarioOpt.get()); // 200 OK
        }

        return ResponseEntity.status(401).build(); // 401 Unauthorized
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity
                .status(302)
                .header("Location", "/login")
                .build();
    }
}