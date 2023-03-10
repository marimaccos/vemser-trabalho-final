package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.Companhia;
import br.com.dbc.javamosdecolar.model.Passagem;
import br.com.dbc.javamosdecolar.model.Trecho;
import br.com.dbc.javamosdecolar.dto.PassagemCreateDTO;
import br.com.dbc.javamosdecolar.dto.PassagemDTO;
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

@Service
@RequiredArgsConstructor
public class PassagemService {
    private final PassagemRepository passagemRepository;
    private final TrechoService trechoService;
    private final CompanhiaService companhiaService;
    private final ObjectMapper mapper;

    public PassagemDTO criar(PassagemCreateDTO passagemDTO) throws RegraDeNegocioException {
        try {

            LocalDateTime dataPartida = passagemDTO.getDataPartida();
            LocalDateTime dataChegada = passagemDTO.getDataChegada();

            final boolean DIA_ANTERIOR = dataChegada.isBefore(dataPartida);

            if (DIA_ANTERIOR) {
                throw new RegraDeNegocioException("Data inválida!");
            }

            UUID codigo = UUID.randomUUID();

            Trecho trecho = mapper.convertValue(trechoService.getTrechoById(passagemDTO.getIdTrecho()),
                    Trecho.class);

            Passagem passagem = new Passagem(codigo.toString(), dataPartida, dataPartida,
                    trecho, true, passagemDTO.getValor());

            Passagem passagemCriada = passagemRepository.adicionar(passagem);

            PassagemDTO passagemCriadaDTO = mapper.convertValue(passagemCriada, PassagemDTO.class);
            passagemCriadaDTO.setIdTrecho(trecho.getIdTrecho());

            return passagemCriadaDTO;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
        }
    }

    public PassagemDTO getById(Integer id) throws RegraDeNegocioException {
        try {
            Passagem passagem = passagemRepository.getPassagemPeloId(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Passagem não encontrada!"));
            PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
            passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());

            return passagemDTO;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public PassagemDTO editar(Integer passagemId, PassagemCreateDTO passagemDTO) throws RegraDeNegocioException {
        try {
            Passagem passagemEncontrada = passagemRepository.getPassagemPeloId(passagemId)
                    .orElseThrow(() -> new RegraDeNegocioException("Passagem inválida!"));

            if(!passagemEncontrada.isDisponivel()) {
                throw new RegraDeNegocioException("Edição indisponivel para uma passagem já comprada.");
            }

            Passagem passagem = mapper.convertValue(passagemDTO, Passagem.class);

            final boolean DIA_ANTERIOR = passagem.getDataChegada().isBefore(passagem.getDataPartida());

            if(DIA_ANTERIOR) {
                throw new RegraDeNegocioException("Data inválida!");
            }
            passagem.setDisponivel(true);

            if(passagemRepository.editar(passagemId, passagem, 0)) {
                PassagemDTO passagemEditada = mapper.convertValue(passagem, PassagemDTO.class);
                passagemEditada.setIdPassagem(passagemId);
                passagemEditada.setIdTrecho(passagemEncontrada.getTrecho().getIdTrecho());
                passagemEditada.setCodigo(passagemEncontrada.getCodigo());

                return passagemEditada;

            } else {
                throw new RegraDeNegocioException("Aconteceu algum problema durante a edição da passagem.");
            }
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a edição da passagem");
        }
    }

    public boolean editarPassagemVendida(Passagem passagem, Integer idVenda) throws DatabaseException {
        passagem.setDisponivel(false);

        return passagemRepository.editar(passagem.getIdPassagem(), passagem, idVenda);
    }

    public void deletar(Integer passagemId) throws DatabaseException {
        passagemRepository.remover(passagemId);
    }

    public List<PassagemDTO> listarPorData(String dataChegada, String dataPartida) throws RegraDeNegocioException {
        try {
            if(dataPartida != null) {
                return passagemRepository.getPassagemPorDataPartida(transformaStringEmLocalDateTime(dataPartida))
                        .stream()
                        .map(passagem -> {
                            PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
                            passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());
                            return passagemDTO;
                        }).toList();

            } else if(dataChegada != null){
                dataChegada = dataChegada.replace("-", "/");

                return passagemRepository.getPassagemPorDataChegada(transformaStringEmLocalDateTime(dataChegada))
                        .stream()
                        .map(passagem -> {
                            PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
                            passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());
                            return passagemDTO;
                        }).toList();
            } else {
                throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
            }
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<PassagemDTO> listarPorValorMaximo(BigDecimal valorMaximo) throws RegraDeNegocioException {
        try {
            return passagemRepository.getPassagemPorValor(valorMaximo).stream()
                    .map(passagem -> {
                        PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
                        passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());
                        return passagemDTO;
                    }).toList();

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<PassagemDTO> listarPorCompanhia(String nomeCompanhia) throws RegraDeNegocioException {
        try {
            Companhia companhia = companhiaService.getByNome(nomeCompanhia);
            return passagemRepository
                    .getPassagemPorCompanhia(companhia.getIdCompanhia()).stream()
                    .map(passagem -> {
                        PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
                        passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());
                        return passagemDTO;
                    }).toList();

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<PassagemDTO> listarUltimas() throws RegraDeNegocioException {
        try {
            return passagemRepository.getUltimasPassagens()
                    .stream()
                    .map(passagem -> {
                        PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
                        passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());
                        return passagemDTO;
                    })
                    .toList();

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    private LocalDateTime transformaStringEmLocalDateTime(String data) throws RegraDeNegocioException {
        try {
            char[] dataChars = data.replace("-", "/").toCharArray();
            dataChars[10] = ' ';
            data = String.valueOf(dataChars);

            return LocalDateTime.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

        } catch (DateTimeParseException e) {
            throw new RegraDeNegocioException("Data inserida no formato incorreto!");
        }
    }
}
