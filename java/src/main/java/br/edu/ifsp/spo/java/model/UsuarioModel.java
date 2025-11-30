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

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Set<HumorModel> registrosHumor;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private Set<TagModel> tags;

    private String nome;
    private String email;
    private String senhaHash;

    public UsuarioModel() {}

    public UsuarioModel(String nome, String email, String senhaHash, Set<TagModel> tags) {
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.tags = tags;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<HumorModel> getRegistrosHumor() {
        return registrosHumor;
    }

    public void setRegistrosHumor(Set<HumorModel> registrosHumor) {
        this.registrosHumor = registrosHumor;
    }

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
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
