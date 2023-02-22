package javamos_decolar.com.javamosdecolar.model;

import java.util.Arrays;

public enum TipoUsuario {
    COMPANHIA(1),
    COMPRADOR(2);

    private Integer tipo;

    TipoUsuario(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public static TipoUsuario ofTipo(Integer numero) {
        return Arrays.stream(TipoUsuario.values())
                .filter(tipo -> tipo.getTipo().equals(numero))
                .findFirst()
                .get();
    }

}
