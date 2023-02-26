package javamos_decolar.com.javamosdecolar.model;

public final class Companhia extends Usuario{

    private Integer idCompanhia;
    private String cnpj;
    private String nomeFantasia;

    public Companhia() {

    }
    public Companhia(Integer idUsuario, String login, String senha, String nome,
                     TipoUsuario tipoUsuario, String cnpj, Integer idComprador, String nomeFantasia) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.idCompanhia = idComprador;
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    public Companhia(Integer idUsuario, String login, String senha, String nome, TipoUsuario tipoUsuario, String cnpj,
                     String nomeFantasia) {
        super(idUsuario, login, senha, nome, tipoUsuario);
        this.cnpj = cnpj;
        this.nomeFantasia = nomeFantasia;
    }

    public Integer getIdCompanhia() {
        return idCompanhia;
    }

    public void setIdCompanhia(Integer idCompanhia) {
        this.idCompanhia = idCompanhia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    @Override
    public String toString() {
        return "Companhia{" +
                ", idCompanhia=" + idCompanhia +
                ", idUsuario='" + this.getIdUsuario() + '\'' +
                ", login='" + this.getLogin() + '\'' +
                ", nome='" + this.getNome() + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", nomeFantasia='" + nomeFantasia + '\'' +
                '}';
    }
}
