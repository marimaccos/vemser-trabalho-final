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

    public PassagemDTO create(PassagemCreateDTO passagemDTO) throws RegraDeNegocioException {
        try {

            LocalDateTime dataPartida = passagemDTO.getDataPartida();
            LocalDateTime dataChegada = passagemDTO.getDataChegada();

            final boolean DIA_ANTERIOR = dataChegada.isBefore(dataPartida);

            if (DIA_ANTERIOR) {
                throw new RegraDeNegocioException("Data inválida!");
            }

            UUID codigo = UUID.randomUUID();

            Trecho trecho = mapper.convertValue(trechoService.getById(passagemDTO.getIdTrecho()),
                    Trecho.class);

            Passagem passagem = new Passagem(codigo.toString(), dataPartida, dataPartida,
                    trecho, true, passagemDTO.getValor());

            Passagem passagemCriada = passagemRepository.create(passagem);

            PassagemDTO passagemCriadaDTO = mapper.convertValue(passagemCriada, PassagemDTO.class);
            passagemCriadaDTO.setIdTrecho(trecho.getIdTrecho());

            return passagemCriadaDTO;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
        }
    }

    public PassagemDTO update(Integer passagemId, PassagemCreateDTO passagemDTO) throws RegraDeNegocioException {
        try {
            Passagem passagemEncontrada = passagemRepository.getById(passagemId)
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

            if(passagemRepository.update(passagemId, passagem, 0)) {
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

    public void delete(Integer passagemId) throws DatabaseException {
        passagemRepository.delete(passagemId);
    }

    public PassagemDTO getById(Integer id) throws RegraDeNegocioException {
        try {
            Passagem passagem = passagemRepository.getById(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Passagem não encontrada!"));
            PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
            passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());

            return passagemDTO;

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<PassagemDTO> getByData(String dataChegada, String dataPartida) throws RegraDeNegocioException {
        try {
            if(dataPartida != null) {
                return passagemRepository.getByDataPartida(parseStringEmLocalDateTime(dataPartida))
                        .stream()
                        .map(passagem -> {
                            PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
                            passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());
                            return passagemDTO;
                        }).toList();

            } else if(dataChegada != null){
                dataChegada = dataChegada.replace("-", "/");

                return passagemRepository.getByDataChegada(parseStringEmLocalDateTime(dataChegada))
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

    public List<PassagemDTO> getByValorMaximo(BigDecimal valorMaximo) throws RegraDeNegocioException {
        try {
            return passagemRepository.getByValor(valorMaximo).stream()
                    .map(passagem -> {
                        PassagemDTO passagemDTO = mapper.convertValue(passagem, PassagemDTO.class);
                        passagemDTO.setIdTrecho(passagem.getTrecho().getIdTrecho());
                        return passagemDTO;
                    }).toList();

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public List<PassagemDTO> getByCompanhia(String nomeCompanhia) throws RegraDeNegocioException {
        try {
            Companhia companhia = companhiaService.getByNome(nomeCompanhia);
            return passagemRepository
                    .getByCompanhia(companhia.getIdCompanhia()).stream()
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

    public List<PassagemDTO> getUltimasPassagens() throws RegraDeNegocioException {
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

    public boolean updatePassagemVendida(Passagem passagem, Integer idVenda) throws DatabaseException {
        passagem.setDisponivel(false);

        return passagemRepository.update(passagem.getIdPassagem(), passagem, idVenda);
    }

    private LocalDateTime parseStringEmLocalDateTime(String data) throws RegraDeNegocioException {
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
