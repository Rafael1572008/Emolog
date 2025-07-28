package br.edu.ifsp.spo.java.controller;

import br.edu.ifsp.spo.java.model.TagModel;
import br.edu.ifsp.spo.java.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tag")  // Define o caminho base /tag
public class TagController {

    private final TagService tagService;

    // Construtor
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // Obter todas as tags
    @GetMapping
    public List<TagModel> getAll() {
        return tagService.getAll();
    }

    // Criar tag
    @PostMapping
    public ResponseEntity<TagModel> create(@RequestBody TagModel tagModel) {
        TagModel criado = tagService.save(tagModel);
        return ResponseEntity.status(201).body(criado);
    }

    // Obter tag por Id
    @GetMapping("/{id}")
    public ResponseEntity<TagModel> getById(@PathVariable Long id) {
        Optional<TagModel> tagOpt = tagService.findById(id);
        return tagOpt
                .map(ResponseEntity::ok)  // 200 OK + tag
                .orElse(ResponseEntity.notFound().build()); // 404 se não encontrar
    }
}
