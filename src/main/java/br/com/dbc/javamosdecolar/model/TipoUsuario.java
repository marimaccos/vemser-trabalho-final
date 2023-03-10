package br.com.dbc.javamosdecolar.model;

import java.util.Arrays;

public enum TipoUsuario {
    COMPANHIA(1, "1"),
    COMPRADOR(2, "2"),;

    private Integer tipo;
    private String tipoString;


    TipoUsuario(Integer tipo, String tipoString) {
        this.tipo = tipo;
    }

    public Integer getTipo() {
        return tipo;
    }

    public String getTipoString() {
        return tipoString;
    }

    public static TipoUsuario ofTipo(Integer numero) {
        return Arrays.stream(TipoUsuario.values())
                .filter(tipo -> tipo.getTipo().equals(numero))
                .findFirst()
                .get();
    }

}
