package br.edu.ifsp.spo.java.service;

import br.edu.ifsp.spo.java.model.UsuarioModel;
import br.edu.ifsp.spo.java.repository.UsuarioRepository;
import br.edu.ifsp.spo.java.security.CryptoUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private UsuarioRepository usuarioRepository;


    // Construtor
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    // Listar todos os usuarios
    public List<UsuarioModel> getAll() { return usuarioRepository.findAll(); }

    // Criar Usuario
    public UsuarioModel save(UsuarioModel usuarioModel) {

        String hash = CryptoUtil.gerarHash(usuarioModel.getSenhaHash());
        usuarioModel.setSenhaHash(hash);

        return usuarioRepository.save(usuarioModel);
    }

    // Deletar
    public void delete(Long id) {usuarioRepository.deleteById(id);}

    // Procurar por Id (Me obrigou a botar Optional, JPA)
    public Optional<UsuarioModel> findById(Long id){ return usuarioRepository.findById(id); }

    // Prucurar user pela senha e email
    public Optional<UsuarioModel> findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    /// Login
    public Optional<UsuarioModel> login(String email, String senhaDigitada) {

        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        }

        UsuarioModel usuario = usuarioOpt.get();

        // compara a senha digitada com o hash salvo
        boolean ok = CryptoUtil.compararHash(senhaDigitada, usuario.getSenhaHash());

        if (ok) {
            return Optional.of(usuario);
        }

        return Optional.empty();
    }

}
