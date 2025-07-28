package br.edu.ifsp.spo.java.repository;

import br.edu.ifsp.spo.java.model.TagModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagModel, Long> {
}
