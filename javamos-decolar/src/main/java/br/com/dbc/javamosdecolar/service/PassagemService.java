package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.dto.CreatePassagemDTO;
import br.com.dbc.javamosdecolar.model.dto.UpdatePassagemDTO;
import br.com.dbc.javamosdecolar.repository.PassagemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PassagemService {
    private final PassagemRepository passagemRepository;
    private final TrechoService trechoService;
    private final CompanhiaService companhiaService;

    public Passagem cadastrarPassagem(CreatePassagemDTO passagemDTO) throws RegraDeNegocioException {
        try {

            LocalDateTime dataPartida = transformaStringEmLocalDateTime(passagemDTO.getDataPartida());
            LocalDateTime dataChegada = transformaStringEmLocalDateTime(passagemDTO.getDataChegada());

            final boolean DIA_ANTERIOR = dataChegada.isBefore(dataPartida);

            if(DIA_ANTERIOR) {
                throw new RegraDeNegocioException("Data inválida!");
            }

            UUID codigo = UUID.randomUUID();

            Trecho trecho = trechoService.getTrechoById(passagemDTO.getIdTrecho());

            Passagem passagem = new Passagem(codigo.toString(), dataPartida, dataChegada,
                    trecho, true, passagemDTO.getValor());

            return passagemRepository.adicionar(passagem);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
        }
    }

    public List<Passagem> listarPassagens(String companhia,
                                          String dataChegada, String dataPartida, BigDecimal valor)
            throws RegraDeNegocioException {

        final Integer DATA_PARTIDA = 1;
        final Integer DATA_CHEGADA = 2;

        if(!companhia.isBlank()) {
            return this.listarPassagemPorCompanhia(companhia);
        } else if (!dataPartida.equals(null)) {
            return this.listarPassagemPorData(transformaStringEmLocalDateTime(dataPartida
                    .replace("-", " ")), DATA_PARTIDA);
        } else if (!dataChegada.equals(null)) {
            return this.listarPassagemPorData(transformaStringEmLocalDateTime(dataChegada
                    .replace("-", " ")), DATA_CHEGADA);
        } else if (!valor.equals(null)) {
            return this.listarPassagemPorValorMaximo(valor);
        } else {
            try {
                return this.passagemRepository.listar();
            } catch (DatabaseException e) {
                throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
            }
        }
    }

    public List<Passagem> listarUltimasPassagens() throws RegraDeNegocioException {
        try {
            return passagemRepository.getUltimasPassagens();
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void editarPassagem(Integer passagemId, UpdatePassagemDTO passagemDTO) throws RegraDeNegocioException {
        try {
            Passagem passagemEncontrada = passagemRepository.getPassagemPeloId(passagemId)
                    .orElseThrow(() -> new RegraDeNegocioException("Passagem inválida!"));

            Passagem passagem = mapUpdatePassagemDTOtoPassagem(passagemDTO, passagemEncontrada);

            final boolean DIA_ANTERIOR = passagem.getDataChegada().isBefore(passagem.getDataPartida());

            if(DIA_ANTERIOR) {
                throw new RegraDeNegocioException("Data inválida!");
            }

            passagemRepository.editar(passagemId, passagem);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void deletarPassagem(Integer passagemId) throws DatabaseException {
        passagemRepository.remover(passagemId);
    }

    public boolean editarPassagemVendida(Passagem passagem) throws DatabaseException {
        passagem.setDisponivel(false);
        return passagemRepository.editar(passagem.getIdPassagem(), passagem);
    }

    private Passagem mapUpdatePassagemDTOtoPassagem(UpdatePassagemDTO passagemDTO,
                                                    Passagem passagem) throws RegraDeNegocioException {
        if(!passagemDTO.getDataChegada().equals(null)) {
            passagem.setDataChegada(transformaStringEmLocalDateTime(passagemDTO.getDataChegada()));
        }

        if(!passagemDTO.getDataPartida().equals(null)) {
            passagem.setDataPartida(transformaStringEmLocalDateTime(passagemDTO.getDataPartida()));
        }

        if(!passagemDTO.getValor().equals(null)) {
            passagem.setValor(passagemDTO.getValor());
        }

        if(passagemDTO.getIdTrecho() != null) {
            Trecho trecho = trechoService.getTrechoById(passagemDTO.getIdTrecho());
            passagem.setTrecho(trecho);
        }

        return passagem;
    }

    private LocalDateTime transformaStringEmLocalDateTime(String data) throws RegraDeNegocioException {
        try {
            return LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (DateTimeParseException e) {
            throw new RegraDeNegocioException("Data inserida no formato incorreto!");
        }
    }

    private List<Passagem> listarPassagemPorData(LocalDateTime data, int tipoDeData) throws RegraDeNegocioException {
        final Integer DATA_PARTIDA = 1;
        final Integer DATA_CHEGADA = 2;

        try {
            if(tipoDeData == DATA_PARTIDA) {
                return passagemRepository.getPassagemPorDataPartida(data);
            } else if(tipoDeData == DATA_CHEGADA){
                return passagemRepository.getPassagemPorDataChegada(data);
            } else {
                return null;
            }
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    private List<Passagem> listarPassagemPorValorMaximo(BigDecimal valorMaximo) throws RegraDeNegocioException {
        try {
            return passagemRepository.getPassagemPorValor(valorMaximo);
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    private List<Passagem> listarPassagemPorCompanhia(String nomeCompanhia) throws RegraDeNegocioException {
        try {
            Companhia companhia = companhiaService.buscaCompanhiaPorNome(nomeCompanhia);
            return passagemRepository
                    .getPassagemPorCompanhia(companhia.getIdCompanhia());

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public Passagem getPassagemById(Integer id) throws RegraDeNegocioException {
        try {
            return passagemRepository.getPassagemPeloId(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Passagem não encontrada!"));
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
