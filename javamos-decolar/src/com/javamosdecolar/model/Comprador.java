package javamos_decolar.com.javamosdecolar.model;

import java.util.ArrayList;
import java.util.List;

public class Comprador extends Usuario{
    private String cpf;
    private List<Venda> historicoCompras = new ArrayList<>();

    public Comprador(String login, String senha, String nome, TipoUsuario tipoUsuario, String cpf) {
        super(login, senha, nome, tipoUsuario);
        this.cpf = cpf;
    }

    public Comprador() {}

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Venda> getHistoricoCompras() {
        return historicoCompras;
    }

    public void setHistoricoCompras(List<Venda> historicoCompras) {
        this.historicoCompras = historicoCompras;
    }

    @Override
    public String toString() {
        return "Comprador{" +
                "cpf='" + cpf + '\'' +
                ", historicoCompras=" + historicoCompras +
                '}';
    }
}
