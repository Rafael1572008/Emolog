package br.edu.ifsp.spo.java.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoUtil {

    /// Gera hash SHA-256
    public static String gerarHash(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro no Hash: " + e.getMessage());
        }
    }

    public static boolean compararHash(String texto, String hashBase64) {
        String hashGerado = gerarHash(texto);
        return hashGerado.equals(hashBase64);
    }
}
