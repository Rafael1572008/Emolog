package br.edu.ifsp.spo.java.model;

public enum HumorEnum {
    RADIANTE(5, "felizao.webp"),
    BEM(4, "felizinho.webp"),
    MEDIO(3, "normal.webp"),
    MAL(2, "bravo.webp"),
    HORRIVEL(1, "zangado.webp");

    private final int valor;
    private final String imagem;

    HumorEnum(int valor, String imagem) {
        this.valor = valor;
        this.imagem = imagem;
    }

    public int getValor() {
        return valor;
    }

    public String getImagem() {
        return imagem;
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
