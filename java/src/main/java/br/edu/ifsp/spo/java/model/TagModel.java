package br.edu.ifsp.spo.java.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity //Indicar que a classe java é uma entidade persistente
@Table(name = "tb_tag")
public class TagModel {

    @Id // Identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Geração de id sequenciais
    private Long id;

    @Column(name = "nome", nullable = false, length = 100) // Configurações da colunas
    private String nome;

    // Relacionamento reverso (opcional, mas útil para acessar os registros de humor ligados a esta tag)
    @JsonIgnore /// Evitar o 'loop infinito de serialização Json'
    @ManyToMany(mappedBy = "tags")
    private Set<HumorModel> registroHumor;


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

    public Set<HumorModel> getRegistroHumor(){
        return registroHumor;
    }

    public void setRegistroHumor(Set<HumorModel> registroHumor){
        this.registroHumor = registroHumor;
    }




    @Override // Representação
    public String toString() {
        return "Tag{id=" + id + ", nome='" + nome + "'}";
    }
}
