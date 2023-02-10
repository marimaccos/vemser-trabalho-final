package javamos_decolar;

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

    public void entrarNoSistema(){
        // TODO implementação
    }

    public boolean cadastrarUsuario(String login, String senha, String nome, Tipo tipo){
        // TODO implementação
        return false;
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
