package br.edu.ifsp.spo.java.controller;

import br.edu.ifsp.spo.java.model.HumorModel;
import br.edu.ifsp.spo.java.service.HumorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/humor")
public class HumorController {
    private final HumorService humorService;

    /// Contrutor
    public HumorController(HumorService humorService) {
        this.humorService = humorService;
    }

    /// Rota pata retornar Todos os user
    @GetMapping
    public List<HumorModel> getAll(){
        return humorService.getAll();
    }

    /// Rota para criar humor
    @PostMapping
    public HumorModel create(@RequestBody HumorModel humorModel){
        return humorService.save(humorModel);
    }

    /// Criar delete nesse e nos outros



    /// Procurar humor por Id
    public ResponseEntity<HumorModel> getById(@PathVariable Long id){ /// PathVariables para pegar id da URL
        Optional<HumorModel> humorOptional = humorService.findById(id);
        return humorOptional
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
