package br.edu.ifsp.spo.java.repository;

import br.edu.ifsp.spo.java.model.HumorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HumorRepository extends JpaRepository<HumorModel, Long> {
}
