package br.edu.ifsp.spo.java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore  /// Evitar o 'loop infinito de serialização Json'
    @OneToMany(mappedBy = "usuario")
    private Set<HumorModel> registrosHumor;

    private String nome;
    private String email;
    private String senhaHash;

    // Construtores
    public UsuarioModel() {}

    public UsuarioModel(String nome, String email, String senhaHash) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<HumorModel> getRegistrosHumor(){
        return registrosHumor;
    }

    public void setRegistrosHumor(Set<HumorModel> registrosHumor) {
        this.registrosHumor = registrosHumor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }
}
