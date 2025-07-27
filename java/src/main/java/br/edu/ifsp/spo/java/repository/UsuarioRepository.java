package br.edu.ifsp.spo.java.repository;

import br.edu.ifsp.spo.java.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

}
