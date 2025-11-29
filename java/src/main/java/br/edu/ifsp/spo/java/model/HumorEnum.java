package br.edu.ifsp.spo.java.model;

public enum HumorEnum {
    RADIANTE(5),
    BEM(4),
    MEDIO(3),
    MAL(2),
    HORRIVEL(1);

    private final int valor;

    HumorEnum(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public static HumorEnum fromValor(int valor) {
        return switch (valor) {
            case 5 -> RADIANTE;
            case 4 -> BEM;
            case 3 -> MEDIO;
            case 2 -> MAL;
            case 1 -> HORRIVEL;
            default -> MEDIO;
        };
    }

}
