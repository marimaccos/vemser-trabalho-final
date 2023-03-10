package br.com.dbc.javamosdecolar.model;

import java.util.Arrays;

public enum Status {
    CONCLUIDO(1),
    CANCELADO(2);

    private Integer status;

    Status(Integer tipo) {
        this.status = tipo;
    }

    public Integer getTipo() {
        return status;
    }

    public static Status ofTipo(Integer numero) {
        return Arrays.stream(Status.values())
                .filter(tipo -> tipo.getTipo().equals(numero))
                .findFirst()
                .get();
    }
}
