package javamos_decolar.com.javamosdecolar.model;

public final class Comprador extends Usuario{
    private Integer idComprador;
    private String cpf;

    public Comprador(Integer idUsuario, String login, String senha, String nome,
                     TipoUsuario tipoUsuario, String cpf, Integer idComprador) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.cpf = cpf;
        this.idComprador = idComprador;
    }

    public Comprador(Integer idUsuario, String login, String senha, String nome, TipoUsuario tipoUsuario,
                     String cpf) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.cpf = cpf;
    }

    public Comprador() {}

    public Integer getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(Integer idComprador) {
        this.idComprador = idComprador;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Comprador{" +
                ", idComprador=" + idComprador +
                ", idUsuario=" + this.getIdUsuario() +
                ", login=" + this.getLogin() +
                ", senha=" + this.getSenha() +
                ", nome=" + this.getNome() +
                ", cpf='" + cpf + '\'' +
                '}';
    }
}
