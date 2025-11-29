package br.edu.ifsp.spo.java.repository;

import br.edu.ifsp.spo.java.model.HumorEnum;
import br.edu.ifsp.spo.java.model.HumorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HumorRepository extends JpaRepository<HumorModel, Long> {
    List<HumorModel> findByUsuarioIdOrderByDataHoraDesc(Long usuarioId);
    List<HumorModel> findByUsuarioIdAndHumorOrderByIdDesc(Long usuarioId, HumorEnum humorTipo);
}
