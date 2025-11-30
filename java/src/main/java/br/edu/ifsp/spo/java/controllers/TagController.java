package br.edu.ifsp.spo.java.controllers;

import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.model.TagModel;
import br.edu.ifsp.spo.java.model.UsuarioModel;
import br.edu.ifsp.spo.java.repository.UsuarioRepository;
import br.edu.ifsp.spo.java.service.TagService;
import br.edu.ifsp.spo.java.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tag")  // Define o caminho base /tag
public class TagController {
    private final TagService tagService;
    private final UsuarioService usuarioService;


    // Construtor
    public TagController(TagService tagService, UsuarioService usuarioService) {
        this.tagService = tagService;
        this.usuarioService = usuarioService;
    }

    // Obter todas as tags
    @GetMapping
    public List<TagModel> getAll() {
        return tagService.getAll();
    }

    // Criar tag
    @PostMapping
    public ResponseEntity<TagModel> create(@RequestBody TagModel tag, HttpSession session) {

        /// Obter id pela sessão
        Long IdUser = (Long) session.getAttribute("idUser");

        if (IdUser == null) {
            return ResponseEntity.status(401).build(); // não logado
        }

        /// Encontrar usuáio pelo id
        UsuarioModel usuario = usuarioService.findById(IdUser)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        /// Atribuir user a tag
        tag.setUsuario(usuario);

        /// Salvar
        TagModel criada = tagService.save(tag);

        return ResponseEntity.status(201).body(criada);
    }

    // Obter tag por Id
    @GetMapping("/{id}")
    public ResponseEntity<TagModel> getById(@PathVariable Long id) { /// PathVariable pega o id pela URL
        Optional<TagModel> tagOptional = tagService.findById(id);
        return tagOptional
                .map(ResponseEntity::ok)  // 200 OK + tag
                .orElse(ResponseEntity.notFound().build()); // 404 se não encontrar
    }

    // Obter tags por usuários
    @GetMapping("/user")
    public ResponseEntity<List<TagModel>> getTagsByIdUser(HttpSession session){

        // Obter Id do usuário pela sessão
        Long IdUSer = (Long) session.getAttribute("idUser");

        List<TagModel> tags = tagService.findByIdUser(IdUSer);
        return ResponseEntity.ok(tags);
    }

    /// Deletar Tag
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteById(@PathVariable Long id){
        tagService.delete(id);

        return ResponseEntity.noContent().build();
    }

    /// Alterar texto tag
    @PatchMapping("/{id}/texto")
    public ResponseEntity<TagModel> alterTitleHumor(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String novoTexto = body.get("texto");
        if (novoTexto == null || novoTexto.trim().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        TagModel atualizado = tagService.updateTextoHumor(id, novoTexto.trim());
        return ResponseEntity.ok(atualizado);
    }
}
