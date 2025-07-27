package br.edu.ifsp.spo.java.controller;

import br.edu.ifsp.spo.java.model.UsuarioModel;
import br.edu.ifsp.spo.java.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Obter todos os usuarios
    @GetMapping
    public List<UsuarioModel> getall() { return usuarioService.getall(); }

    @PostMapping
    public UsuarioModel create(@RequestBody UsuarioModel usuarioModel) {return usuarioService.save(usuarioModel);}
}
