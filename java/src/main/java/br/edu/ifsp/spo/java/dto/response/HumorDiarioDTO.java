package br.edu.ifsp.spo.java.dto.response;



public class HumorDiarioDTO{
    private String data;
    private String humor;
    private int valor;
    private int registrosNoDia;

    // Construtor
    public HumorDiarioDTO(String data, String humor, int valor, int registrosNoDia) {
        this.data = data;
        this.humor = humor;
        this.valor = valor;
        this.registrosNoDia = registrosNoDia;
    }

    // Getters
    public String getData() {
        return data;
    }

    public String getHumor() {
        return humor;
    }

    public int getValor() {
        return valor;
    }

    public int getRegistrosNoDia() {
        return registrosNoDia;
    }
}
