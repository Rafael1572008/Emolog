package br.edu.ifsp.spo.java.service;

import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.repository.HumorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HumorService {
    private HumorRepository humorRepository;

    public HumorService(HumorRepository humorRepository) {
        this.humorRepository = humorRepository;
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
}
