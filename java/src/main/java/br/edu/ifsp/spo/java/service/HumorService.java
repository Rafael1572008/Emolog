package br.edu.ifsp.spo.java.service;

import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.model.TagModel;
import br.edu.ifsp.spo.java.repository.HumorRepository;
import br.edu.ifsp.spo.java.repository.TagRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class HumorService {
    private HumorRepository humorRepository;
    private TagRepository tagRepository;


    public HumorService(HumorRepository humorRepository, TagRepository tagRepository) {
        this.humorRepository = humorRepository;
        this.tagRepository = tagRepository;
    }

    /// Listar Todos os humores
    public List<HumorModel> getAll(){
        return humorRepository.findAll();
    }

    /// Criar humor
    public HumorModel save(HumorModel humorModel){
        return humorRepository.save(humorModel);
    }

    /// Deletar humor
    public void delete(Long id){
        humorRepository.deleteById(id);
    }

    /// Procurar humor pelo id
    public Optional<HumorModel> findById(Long id){ // Me obriga a usar Optinal :( [Verei o pq]
        return humorRepository.findById(id);
    }

    /// Adicionar Tag
    public void adicionarTags(Long id, Set<Long> tagsIds) {
        HumorModel humor = humorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Humor não encontrado"));

        Set<TagModel> tagsNovas = new HashSet<>(tagRepository.findAllById(tagsIds));
        if (tagsNovas.size() != tagsIds.size()) {
            throw new RuntimeException("Tag não encontrada");
        }

        Set<TagModel> tagsAtuais = new HashSet<>(humor.getTags());

        tagsAtuais.addAll(tagsNovas);

        humor.setTags(tagsAtuais);

        humorRepository.save(humor);
    }

    // Remover Tag
    public void removeTags(Long id, Set<Long> tagsIds) {
        // Buscar humor pelo ID
        HumorModel humor = humorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Humor não encontrado"));

        // Buscar tags a serem retiradas
        Set<TagModel> tagsRetiradas = new HashSet<>(tagRepository.findAllById(tagsIds));

        // Obter tags atuais do humor
        Set<TagModel> tagsAtuais = new HashSet<>(humor.getTags());

        // Remover as tags do humor
        tagsAtuais.removeAll(tagsRetiradas);

        // Atualizar o humor com as novas tags
        humor.setTags(tagsAtuais);

        // Salvar o humor atualizado
        humorRepository.save(humor);
    }

    /// Altera Humor, texto
    public Optional<HumorModel> updateHumor(Long id, String newText){
        // Buscar humor pelo ID
        HumorModel humor = humorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Humor não encontrado"));

        // Humor
        Optional<HumorModel> existHumor = humorRepository.findById(id);
        if (existHumor.isPresent()) {
            HumorModel updateHumor = existHumor.get();

            updateHumor.setTexto(newText);

            humorRepository.save(humor);
        }
        return existHumor;
    }


}
