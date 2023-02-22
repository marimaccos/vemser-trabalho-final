package javamos_decolar.com.javamosdecolar.utils;

public final class Codigo {
    public static String gerarCodigo() {
        // sugestão: procurar uma forma de fazer o próprio oracle fazer isso
        return String.valueOf(1 + (int) (Math.random() * 2000));
    }
}
