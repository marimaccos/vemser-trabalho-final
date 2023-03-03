package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.dto.CreatePassagemDTO;
import br.com.dbc.javamosdecolar.model.dto.PassagemDTO;
import br.com.dbc.javamosdecolar.model.dto.UpdatePassagemDTO;
import br.com.dbc.javamosdecolar.repository.PassagemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PassagemService {
    private final PassagemRepository passagemRepository;
    private final TrechoService trechoService;
    private final CompanhiaService companhiaService;
    private final ObjectMapper mapper;

    public PassagemDTO cadastrarPassagem(CreatePassagemDTO passagemDTO) throws RegraDeNegocioException {
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

            Passagem passagemCriada = passagemRepository.adicionar(passagem);

            return mapper.convertValue(passagemCriada, PassagemDTO.class);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
        }
    }

    public List<PassagemDTO> listarPassagens(String companhia,
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
                return this.passagemRepository.listar().stream()
                        .map(passagem -> mapper.convertValue(passagem, PassagemDTO.class))
                        .collect(Collectors.toList());
            } catch (DatabaseException e) {
                throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
            }
        }
    }

    public List<PassagemDTO> listarUltimasPassagens() throws RegraDeNegocioException {
        try {
            return passagemRepository.getUltimasPassagens()
                    .stream()
                    .map(passagem -> mapper.convertValue(passagem, PassagemDTO.class))
                    .toList();
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void editarPassagem(Integer passagemId, CreatePassagemDTO passagemDTO) throws RegraDeNegocioException {
        try {
            passagemRepository.getPassagemPeloId(passagemId)
                    .orElseThrow(() -> new RegraDeNegocioException("Passagem inválida!"));

            Passagem passagem = mapper.convertValue(passagemDTO, Passagem.class);

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

    private LocalDateTime transformaStringEmLocalDateTime(String data) throws RegraDeNegocioException {
        try {
            return LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (DateTimeParseException e) {
            throw new RegraDeNegocioException("Data inserida no formato incorreto!");
        }
    }

    private List<PassagemDTO> listarPassagemPorData(LocalDateTime data, int tipoDeData) throws RegraDeNegocioException {
        final Integer DATA_PARTIDA = 1;
        final Integer DATA_CHEGADA = 2;

        try {
            if(tipoDeData == DATA_PARTIDA) {
                return passagemRepository.getPassagemPorDataPartida(data).stream()
                        .map(passagem -> mapper.convertValue(passagem, PassagemDTO.class)).toList();
            } else if(tipoDeData == DATA_CHEGADA){
                return passagemRepository.getPassagemPorDataChegada(data).stream()
                        .map(passagem -> mapper.convertValue(passagem, PassagemDTO.class)).toList();
            } else {
                return null;
            }
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    private List<PassagemDTO> listarPassagemPorValorMaximo(BigDecimal valorMaximo) throws RegraDeNegocioException {
        try {
            return passagemRepository.getPassagemPorValor(valorMaximo).stream()
                    .map(passagem -> mapper.convertValue(passagem, PassagemDTO.class)).toList();
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    private List<PassagemDTO> listarPassagemPorCompanhia(String nomeCompanhia) throws RegraDeNegocioException {
        try {
            Companhia companhia = companhiaService.getCompanhiaByNome(nomeCompanhia);
            return passagemRepository
                    .getPassagemPorCompanhia(companhia.getIdCompanhia()).stream()
                    .map(passagem -> mapper.convertValue(passagem, PassagemDTO.class)).toList();

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public PassagemDTO getPassagemById(Integer id) throws RegraDeNegocioException {
        try {
            Passagem passagem = passagemRepository.getPassagemPeloId(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Passagem não encontrada!"));
            return mapper.convertValue(passagem, PassagemDTO.class);
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
