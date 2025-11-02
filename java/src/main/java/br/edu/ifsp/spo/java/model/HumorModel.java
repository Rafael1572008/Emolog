package br.edu.ifsp.spo.java.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity /// Definir classe como entidade
@Table(name = "tb_humor")
public class HumorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @Column(name = "humor", nullable = false)
    private String humor;

    @Column(name = "texto")
    private String texto;

    @Column(name = "dataHora", nullable = false)
    private LocalDateTime dataHora;

    // Relacionamento com tags (muitos para muitos via tabela intermediária)
    @ManyToMany
    @JoinTable(
            name = "registro_tag",
            joinColumns = @JoinColumn(name = "registro_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<TagModel> tags = new HashSet<>();

    /// Construtores

    public HumorModel(){}

    public HumorModel(Long id, LocalDateTime dataHora, String texto, String humor, UsuarioModel usuario) {
        this.id = id;
        this.dataHora = dataHora;
        this.texto = texto;
        this.humor = humor;
        this.usuario = usuario;
    }

    /// Getter e Setters
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getHumor() {
        return humor;
    }

    public void setHumor(String humor) {
        this.humor = humor;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<TagModel> getTags() {
        return tags;
    }

    public void setTags(Set<TagModel> tags) {
        this.tags = tags;
    }

    /// Override


}
