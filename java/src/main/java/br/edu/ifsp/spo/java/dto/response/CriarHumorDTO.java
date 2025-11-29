package br.edu.ifsp.spo.java.dto.response;

public class CriarHumorDTO {

    private String texto;
    private int humor;

    public CriarHumorDTO() {}

    public CriarHumorDTO(String texto, int humor) {
        this.texto = texto;
        this.humor = humor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getHumor() {
        return humor;
    }

    public void setHumor(int humor) {
        this.humor = humor;
    }
}
