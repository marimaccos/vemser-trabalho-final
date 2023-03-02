package br.com.dbc.javamosdecolar.model.dto;

public class CreateVendaDTO {
    private Integer idVenda;
    private Integer idComprador;
    private Integer idCompanhia;
    private Integer idPassagem;

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Integer getIdComprador() {
        return idComprador;
    }

    public void setIdComprador(Integer idComprador) {
        this.idComprador = idComprador;
    }

    public Integer getIdCompanhia() {
        return idCompanhia;
    }

    public void setIdCompanhia(Integer idCompanhia) {
        this.idCompanhia = idCompanhia;
    }

    public Integer getIdPassagem() {
        return idPassagem;
    }

    public void setIdPassagem(Integer idPassagem) {
        this.idPassagem = idPassagem;
    }
}
