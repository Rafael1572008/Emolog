package br.edu.ifsp.spo.java.service;

import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.model.TagModel;
import br.edu.ifsp.spo.java.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private TagRepository tagRepository;

    /// Contrutor
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    // Listar todas as tags
    public List<TagModel> getAll(){
        return tagRepository.findAll();
    }

    // Criar Tag
    public TagModel save(TagModel tagModel){ return tagRepository.save(tagModel); }

    // Deletar Tag
    public void delete(Long id){ tagRepository.deleteById(id); }

    // Procurar Tag pelo Id
    public Optional<TagModel> findById(Long id) { return tagRepository.findById(id); }

    // Obter tag por Usuário
    public List<TagModel> findByIdUser(Long usuarioId) {
        return tagRepository.findByUsuarioIdOrderByNomeAsc(usuarioId);
    }
}
