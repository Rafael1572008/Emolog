package br.edu.ifsp.spo.java.model;

import jakarta.persistence.*;

@Entity //Indicar que a classe java é uma entidade persistente
@Table(name = "tb_tag")
public class TagModel {

    @Id // Identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração de id sequenciais
    private Long id;

    @Column(name = "nome", nullable = false, length = 100) // Configurações da colunas
    private String nome;


    // Construtores
    public TagModel() {
    }

    public TagModel(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }


    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override // Representação
    public String toString() {
        return "Tag{id=" + id + ", nome='" + nome + "'}";
    }
}
