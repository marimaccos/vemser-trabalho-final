package javamos_decolar.com.javamosdecolar.model;

public abstract class Usuario {

    private String login;
    private String senha;
    private String nome;
    private TipoUsuario tipoUsuario;

    public Usuario(String login, String senha, String nome, TipoUsuario tipoUsuario) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.tipoUsuario = tipoUsuario;
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

    public TipoUsuario getTipo() {
        return tipoUsuario;
    }

    public void setTipo(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
