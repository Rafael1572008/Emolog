package br.edu.ifsp.spo.java.controllers;

import br.edu.ifsp.spo.java.dto.response.CriarHumorDTO;
import br.edu.ifsp.spo.java.dto.response.HumorDiarioDTO;
import br.edu.ifsp.spo.java.model.HumorEnum;
import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.model.UsuarioModel;
import br.edu.ifsp.spo.java.repository.UsuarioRepository;
import br.edu.ifsp.spo.java.service.HumorService;
import br.edu.ifsp.spo.java.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/humor")
public class HumorController {
    private final HumorService humorService;
    private final UsuarioService usuarioService;

    /// Contrutor
    public HumorController(HumorService humorService, UsuarioService usuarioService) {
        this.humorService = humorService;
        this.usuarioService = usuarioService;
    }

    /// Rota pata retornar Todos os user
    @GetMapping
    public List<HumorModel> getAll(){
        return humorService.getAll();
    }

    /// Rota para criar humor
    @PostMapping
    public ResponseEntity<HumorModel> create(@RequestBody CriarHumorDTO dto, HttpSession session) {
        String email = session.getAttribute("usuario").toString();
        Optional<UsuarioModel> usuarioOpt = usuarioService.findByEmail(email);

        UsuarioModel usuario = usuarioOpt.orElseThrow(() ->
            new RuntimeException("Usuário não encontrado"));

        HumorModel humor = new HumorModel();

        HumorEnum humorEnum = HumorEnum.fromValor(dto.getHumor());
        humor.setTexto(dto.getTexto());
        humor.setHumor(humorEnum);
        humor.setUsuario(usuario);
        humor.setDataHora(LocalDateTime.now());

        HumorModel criado = humorService.save(humor);
        return ResponseEntity.status(201).body(criado);
    }


    // Deletar Humor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        // Chamar o servico
        humorService.delete(id);

        // Retorna 200 caso de bom
        return ResponseEntity.noContent().build();
    }

    /// Procurar humor por Id
    @GetMapping("/{id}")
    public ResponseEntity<HumorModel> getById(@PathVariable Long id) {
        Optional<HumorModel> humorOptional = humorService.findById(id);
        return humorOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /// === Tags ===
    /// Patch, Adicioanr tags
    @PatchMapping("/{id}/tags")
    public ResponseEntity<Void> adicionarTags(
            @PathVariable Long id,
            @RequestBody Set<Long> tagsIds){

        humorService.adicionarTags(id, tagsIds);
        return ResponseEntity.noContent().build();
    }

    ///  Deletar tag
    @DeleteMapping("/{id}/tags")
    public ResponseEntity<Void> removerTags(
            @PathVariable Long id,
            @RequestBody Set<Long> tagIds){

        humorService.removeTags(id, tagIds);
        return ResponseEntity.noContent().build();
    }


    /// === Humor Titulo ===
    // alterar humor
    @PatchMapping("/{id}/texto")
    public ResponseEntity<HumorModel> alterTitleHumor(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {

        String novoTexto = body.get("texto");
        if (novoTexto == null || novoTexto.trim().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        HumorModel atualizado = humorService.updateTextoHumor(id, novoTexto.trim());
        return ResponseEntity.ok(atualizado);
    }

    /// Gráfico (Não está diferenciando os usuários, junta)
    @GetMapping("/diario")
    public ResponseEntity<List<HumorDiarioDTO>> getHumorDiario(HttpSession session) {

        // Obter Id pela sessão
        Long idUser = (Long) session.getAttribute("idUser");

        List<HumorDiarioDTO> resultado = humorService.calcularHumorDiario(idUser);
        return ResponseEntity.ok(resultado);
    }

    /// Importação de lote
    @PostMapping("/batch")
    public ResponseEntity<List<HumorModel>> createBatch(@RequestBody List<HumorModel> humores) {
        List<HumorModel> salvos = humorService.saveAll(humores);
        return ResponseEntity.ok(salvos);
    }

    /// Humor do usuário logado
    @GetMapping("/humor")
    public ResponseEntity<List<HumorModel>> getHumorByIdUser(HttpSession session) {

        // Obter Id pela sessão
        Long idUser = (Long) session.getAttribute("idUser");

        List<HumorModel> humores = humorService.findByIdUser(idUser);
        return ResponseEntity.ok(humores);
    }

    /// Obter valores possiveis para humores
    @GetMapping("/humores")
    public HumorEnum[] listaHumores(){
        return HumorEnum.values();
    }
}