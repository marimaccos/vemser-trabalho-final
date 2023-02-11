package javamos_decolar;

import java.util.Optional;

public abstract class Usuario {

    private String login;
    private String senha;
    private String nome;
    private Tipo tipo;

    public Usuario(String login, String senha, String nome, Tipo tipo) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.tipo = tipo;
    }

    public boolean entrarNoSistema(String login, String senha,
                                   CompradorDados compradorDados, CompanhiaDados companhiaDados) {

        Optional<Comprador> compradorOpt = compradorDados.getListaDeComprador().stream()
                .filter(comprador -> comprador.getLogin() == login)
                .findAny();

        if (compradorOpt.isPresent()) {
            if (compradorOpt.get().getSenha() == senha) {
                return true;
            }
        }

        return false;
    }

    public boolean cadastrarUsuario(String login, String senha, String nome, Tipo tipo,
                                    CompradorDados compradorDados, CompanhiaDados companhiaDados) {

        if (login.isBlank() || senha.isBlank() || nome.isBlank()) {
            return  false;
        }

        if (tipo == Tipo.COMPRADOR) {
            Comprador comprador = new Comprador(login, senha, nome, tipo);
            compradorDados.adicionar(comprador);

            return  true;
        } else {
            Companhia companhia = new Companhia(login, senha, nome, tipo);
            companhiaDados.adicionar(companhia);

            return true;
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
