package javamos_decolar.com.javamosdecolar.model;

import java.util.Arrays;

public enum Status {
    CONCLUIDO(1),
    CANCELADO(3);

    private Integer status;

    Status(Integer tipo) {
        this.status = tipo;
    }

    public Integer getTipo() {
        return status;
    }

    public static TipoUsuario ofTipo(Integer numero) {
        return Arrays.stream(TipoUsuario.values())
                .filter(tipo -> tipo.getTipo().equals(numero))
                .findFirst()
                .get();
    }
}
