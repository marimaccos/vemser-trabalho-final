package javamos_decolar;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Venda {
    private String codigo;
    private BigDecimal valor;
    private List<Passagem> passagens;
//    private Comprador comprador;
//    private Companhia compranhia;
    private LocalDate data;

    // constructor


    // getters and setters


    public BigDecimal calcularValorTotal() {
        return BigDecimal.ONE;
    }

    public void imprimirPassagem() {

    }

}
