package br.com.dbc.javamosdecolar.service;

import br.com.dbc.javamosdecolar.exception.DatabaseException;
import br.com.dbc.javamosdecolar.exception.RegraDeNegocioException;
import br.com.dbc.javamosdecolar.model.*;
import br.com.dbc.javamosdecolar.model.dto.CompanhiaDTO;
import br.com.dbc.javamosdecolar.model.dto.CompanhiaCreateDTO;
import br.com.dbc.javamosdecolar.model.dto.CompradorDTO;
import br.com.dbc.javamosdecolar.repository.CompanhiaRepository;
import br.com.dbc.javamosdecolar.repository.PassagemRepository;
import br.com.dbc.javamosdecolar.repository.TrechoRepository;
import br.com.dbc.javamosdecolar.repository.VendaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class CompanhiaService {

    private CompanhiaRepository companhiaRepository;
    private VendaRepository vendaRepository;
    private PassagemRepository passagemRepository;
    private TrechoRepository trechoRepository;
    private UsuarioService usuarioService;
    private final ObjectMapper objectMapper;


    public CompanhiaDTO criarCompanhia(CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        try {
            Usuario usuarioNovo = new Usuario(companhiaDTO.getLogin(), companhiaDTO.getSenha(), companhiaDTO.getNomeFantasia(), TipoUsuario.COMPANHIA);
            Usuario usuarioCriado = usuarioService.criarUsuario(usuarioNovo);

            Companhia companhiaNova = objectMapper.convertValue(companhiaDTO, Companhia.class);
            companhiaNova.setIdUsuario(usuarioCriado.getIdUsuario());
            companhiaNova = companhiaRepository.adicionar(companhiaNova);

            return objectMapper.convertValue(companhiaNova, CompanhiaDTO.class);

        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a criação.");
        }
    }
    public void imprimirTrechosDaCompanhia(Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            List<Trecho> trechosPorCompanhia = trechoRepository.getTrechosPorCompanhia(companhia.get()
                    .getIdCompanhia());

            if(trechosPorCompanhia.isEmpty()) {
                System.out.println("Não há trechos cadastrados!");
            } else {
                trechosPorCompanhia.stream().forEach(System.out::println);
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public Companhia getCompanhiaById(Integer id) throws RegraDeNegocioException {
        try {
            return companhiaRepository.buscaCompanhiaPorIdUsuario(id)
                    .orElseThrow(() -> new RegraDeNegocioException("Companhia não encontrada"));
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a recuperação da companhia.");
        }
    }

    public CompanhiaDTO getCompanhiaByIdDTO (Integer id) throws RegraDeNegocioException {
        return objectMapper.convertValue(getCompanhiaById(id), CompanhiaDTO.class);
    }

    public List<CompanhiaDTO> listaCompanhias() throws RegraDeNegocioException {
        try{
            List<CompanhiaDTO> listaCompradores = companhiaRepository.listaCompanhias().stream()
                    .map(companhia -> objectMapper.convertValue(companhia, CompanhiaDTO.class))
                    .collect(Collectors.toList());

            return listaCompradores;
        } catch (DatabaseException e) {
        throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public Companhia getCompanhiaByNome(String nome) throws RegraDeNegocioException {
        try {
            return companhiaRepository.buscaCompanhiaPorNome(nome)
                            .orElseThrow(() -> new RegraDeNegocioException("Companhia não Encontrada"));
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a recuperação da companhia.");
        }
    }

    public void editarCompanhia(Integer idCompanhia, CompanhiaCreateDTO companhiaDTO) throws RegraDeNegocioException {
        try {
            Companhia companhiaPesquisa = getCompanhiaById(idCompanhia);
            companhiaRepository.editarCompanhia(idCompanhia, companhiaPesquisa);
        } catch (DatabaseException e) {
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void imprimirHistoricoDeVendas(Usuario usuario) throws RegraDeNegocioException {
        try {
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            List<Venda> vendasPorCompanhia = vendaRepository.getVendasPorCompanhia(companhia.get().getIdCompanhia());

            if (vendasPorCompanhia.isEmpty()) {
                System.out.println("Não há nada para exibir.");
            } else {
                vendasPorCompanhia.stream().forEach(System.out::println);
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }


    public void listarPassagensCadastradas(Usuario usuario) throws RegraDeNegocioException {
        try{
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            List<Passagem> passagemPorCompanhia = passagemRepository
                    .getPassagemPorCompanhia(companhia.get().getIdCompanhia());

            if(passagemPorCompanhia.isEmpty()) {
                System.out.println("Não há passagens cadastradas!");
            } else {
                passagemPorCompanhia.stream().forEach(System.out::println);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }

    public void deletarPassagem(Integer indexRemocaoPassagem, Usuario usuario) throws RegraDeNegocioException {
        try{
            Optional<Companhia> companhia = companhiaRepository.buscaCompanhiaPorIdUsuario(usuario.getIdUsuario());

            if(companhia.isEmpty()) {
                throw new RegraDeNegocioException("Companhia não pode ser encontrada!");
            }

            Optional<Passagem> passagem = passagemRepository.getPassagemPeloId(indexRemocaoPassagem);

            boolean companhiaEhDonaDaPassagem = passagem.get().getTrecho().getCompanhia().getIdCompanhia()
                    .equals(companhia.get().getIdCompanhia());

            if(!companhiaEhDonaDaPassagem) {
                throw new RegraDeNegocioException("Passagem não pode ser deletada!");
            }

            boolean conseguiuRemover = passagemRepository.remover(indexRemocaoPassagem);
            System.out.println("removido? " + conseguiuRemover + "| com id=" + indexRemocaoPassagem);

        } catch (DatabaseException e) {
            e.printStackTrace();
            throw new RegraDeNegocioException("Aconteceu algum problema durante a listagem.");
        }
    }
}
