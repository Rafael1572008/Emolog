package br.edu.ifsp.spo.java.service;

import br.edu.ifsp.spo.java.model.UsuarioModel;
import br.edu.ifsp.spo.java.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Listar todos os usuarios
    public List<UsuarioModel> getall() { return usuarioRepository.findAll(); }

    // Criar Usuario
    public UsuarioModel save(UsuarioModel usuarioModel) {return usuarioRepository.save(usuarioModel);}

    // Deletar
    public void delete(Long id) {usuarioRepository.deleteById(id);}

}
