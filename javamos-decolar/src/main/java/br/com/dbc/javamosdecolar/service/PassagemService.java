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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PassagemService {
    private final PassagemRepository passagemRepository;
    private final TrechoService trechoService;
    private final CompanhiaService companhiaService;

    public Passagem cadastrarPassagem(CreatePassagemDTO passagemDTO) throws RegraDeNegocioException {
        try {
            final boolean DIA_ANTERIOR = passagemDTO.getDataChegada().isBefore(passagemDTO.getDataPartida());

            if(DIA_ANTERIOR) {
                throw new RegraDeNegocioException("Data inválida!");
            }

            UUID codigo = UUID.randomUUID();

            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia inválida!");
            }

            Optional<Trecho> trechoOptional = trechoService.getTrechoById(passagemDTO.getIdTrecho());

            if(trechoOptional.isEmpty()) {
                throw new RegraDeNegocioException("Trecho inválido!");
            }

            Passagem passagem = new Passagem(codigo.toString(), passagemDTO.getDataPartida(), passagemDTO.getDataChegada(),
                    trechoOptional.get(), true, passagemDTO.getValor());

            return passagemRepository.adicionar(passagem);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante o cadastro.");
        }
    }

    public List<Passagem> listarPassagens(String companhia,
                                          LocalDateTime dataChegada, LocalDateTime dataPartida, BigDecimal valor)
            throws RegraDeNegocioException {
        if(!companhia.isBlank()) {
            return this.listarPassagemPorCompanhia(companhia);
        } else if (!dataChegada.equals(null)) {
            return this.listarPassagemPorData(dataPartida, 1);
        } else if (!dataChegada.equals(null)) {
            return this.listarPassagemPorData(dataChegada, 2);
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
            Optional<Passagem> passagemOptional = passagemRepository.getPassagemPeloId(passagemId);

            if(passagemOptional.isEmpty()) {
                throw new RegraDeNegocioException("Passagem inválida!");
            }

            final Passagem PASSAGEM_EXISTENTE = passagemOptional.get();

            Passagem passagem = mapUpdatePassagemDTOtoPassagem(passagemDTO, PASSAGEM_EXISTENTE);

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

    private Passagem mapUpdatePassagemDTOtoPassagem(UpdatePassagemDTO passagemDTO,
                                                    Passagem passagem) throws RegraDeNegocioException {
        if(!passagemDTO.getDataChegada().equals(null)) {
            passagem.setDataChegada(passagemDTO.getDataChegada());
        }

        if(!passagemDTO.getDataPartida().equals(null)) {
            passagem.setDataPartida(passagemDTO.getDataPartida());
        }

        if(!passagemDTO.getValor().equals(null)) {
            passagem.setValor(passagemDTO.getValor());

        }

        if(passagemDTO.getTrechoId() != null) {
            Optional<Trecho> trechoOptional = trechoService.getTrechoById(passagemDTO.getIdTrecho());
            if(trechoOptional.isEmpty()) {
                throw new RegraDeNegocioException("Trecho inválido!");
            }
            passagem.setTrecho(trechoOptional.get());
        }

        return passagem;
    }

    private List<Passagem> listarPassagemPorData(LocalDateTime data, int tipoDeData) throws RegraDeNegocioException {
        try {
            if(tipoDeData == 1) {
                return passagemRepository.getPassagemPorDataPartida(data);

            } else {
                return passagemRepository.getPassagemPorDataChegada(data);
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
            Optional<Companhia> companhiaEncontrada = companhiaService.buscaCompanhiaPorNome(nomeCompanhia);

            if(companhiaEncontrada.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não encontrada!");
            }

            return passagemRepository
                    .getPassagemPorCompanhia(companhiaEncontrada.get().getIdCompanhia());

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

}
