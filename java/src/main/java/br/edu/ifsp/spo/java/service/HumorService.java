package br.edu.ifsp.spo.java.service;

import br.edu.ifsp.spo.java.dto.response.HumorDiarioDTO;
import br.edu.ifsp.spo.java.model.HumorEnum;
import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.model.TagModel;
import br.edu.ifsp.spo.java.repository.HumorRepository;
import br.edu.ifsp.spo.java.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


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
    public HumorModel updateTextoHumor(Long id, String novoTexto) {
        HumorModel humor = humorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Humor não encontrado"));

        humor.setTexto(novoTexto);
        return humorRepository.save(humor);
    }

    // Calcular a média de emoções diaria do User (Aredondar para cima)
    public List<HumorDiarioDTO> calcularHumorDiario(Long idUser) {
        Map<String, List<HumorModel>> porDia = humorRepository
                .findByUsuarioIdOrderByDataHoraDesc(idUser)
                .stream()
                .collect(Collectors.groupingBy(h -> h.getDataHora().toLocalDate().toString()));

        return porDia.entrySet().stream()
                .map(entry -> {
                    String data = entry.getKey();
                    List<HumorModel> doDia = entry.getValue();

                    double media = doDia.stream()
                            .mapToInt(h -> h.getHumor().getValor())
                            .average()
                            .orElse(3.0);

                    int valorArredondado = (int) Math.round(media);

                    HumorEnum humorFinal = HumorEnum.fromValor(valorArredondado);

                    return new HumorDiarioDTO(
                            data,
                            humorFinal.name(), // envia para o front em string amigável
                            valorArredondado,
                            doDia.size()
                    );
                })
                .sorted(Comparator.comparing(HumorDiarioDTO::getData))
                .toList();
    }

    // Importar de lote
    public List<HumorModel> saveAll(List<HumorModel> humores) {
        return humorRepository.saveAll(humores);
    }

    // Obter humor por user
    public List<HumorModel> findByIdUser(Long usuarioId) {
        return humorRepository.findByUsuarioIdOrderByDataHoraDesc(usuarioId);
    }

    /// Obter Humores por Tag
    public List<HumorModel> findHumorByTag(Long usuarioId, TagModel tagModel){
        return humorRepository.findByUsuarioIdAndTagsContainingOrderByIdDesc(usuarioId, tagModel);
    }
}