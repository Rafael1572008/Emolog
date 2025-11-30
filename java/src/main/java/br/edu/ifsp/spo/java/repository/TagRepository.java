package br.edu.ifsp.spo.java.repository;

import br.edu.ifsp.spo.java.model.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<TagModel, Long> {
    List<TagModel> findByUsuarioIdOrderByNomeAsc(Long usuarioId);
}
