package br.com.falves.service;

import br.com.falves.dao.IPokemonDAO;
import br.com.falves.dao.ITreinadorDAO;
import br.com.falves.dao.PokemonMapDAO;
import br.com.falves.dao.TreinadorMapDAO;
import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Regioes;
import br.com.falves.domain.Treinador;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokemonServiceTest {
    PokemonService pokemonService = new PokemonService();
    IPokemonDAO iPokemonDAO = PokemonMapDAO.getInstance();

    Pokemon pokemon;

    @BeforeEach
    public void init(){
        iPokemonDAO.buscarTodos().clear();

        pokemon = Pokemon.builder()
                .numero(1L)
                .especie(EspeciePokemon.MOLTRES)
                .nivel(20)
                .build();

        iPokemonDAO.cadastrar(pokemon);
    }

    @Test
    void deveCadastrarPokemonComDadosValidos() {
        String dados = "2,Mew,1";
        pokemonService.cadastrarPokemon(dados);
        Pokemon pokemonConsultado = iPokemonDAO.consultar(2L);

        Assertions.assertEquals(2L, pokemonConsultado.getNumero());
    }

    @Test
    void naoDeveCadastrarPokemonComDadosInvalidos() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.cadastrarPokemon("2,1");;
        });
    }

    @Test
    public void naoDeveCadastrarPokemonComMesmoNumero() {
        String dados = "1,Mew,1";


        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.cadastrarPokemon(dados);
        });
    }

    @Test
    void consultarPokemon() {
        boolean pokemonEncontrado = pokemonService.consultarPokemon("1").equals(pokemon);
        Assertions.assertTrue(pokemonEncontrado);
    }

    @Test
    public void naoDeveEncontrarPokemonInexistente() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.consultarPokemon("999");
        });
    }

    @Test
    void naoDeveEncontrarPokemonComDadosInvalidos() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.consultarPokemon("2");
        });
    }

    @Test
    void listaPokemons() {
        boolean listaTodos = !pokemonService.listaPokemons().isEmpty();
        Assertions.assertTrue(listaTodos);
    }

    @Test
    void listaNenhumPokemon(){
        pokemonService.excluirPokemon(pokemon);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.listaPokemons();
        });
    }

    @Test
    void editarPokemon() {
        String dadosNovos = "Mewtwo,80";
        pokemonService.editarPokemon(pokemon, dadosNovos);
        boolean pokemonAlterado = pokemon.getEspeciePokemon().toString().toLowerCase().contains("mewtwo") && pokemon.getNivel() == 80;
        Assertions.assertTrue(pokemonAlterado);
    }

    @Test
    void naoEditaPokemonComDadosInvalidos() {
        String dadosNovos = "80";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.editarPokemon(pokemon, dadosNovos);
        });
    }

    @Test
    void excluirPokemon() {
        pokemonService.excluirPokemon(pokemon);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            pokemonService.consultarPokemon(pokemon.getNumero().toString());
        });
    }

    @Test
    void deveEncontrarPokemonComNivelAcima() {
        boolean pokemonEncontrado = pokemonService.buscarPorNivel("20").contains("MOLTRES");
        Assertions.assertTrue(pokemonEncontrado);
    }

    @Test
    void naoDeveEncontrarPokemonComNivelAbaixo() {
        boolean pokemonEncontrado = pokemonService.buscarPorNivel("30").contains("MOLTRES");
        Assertions.assertFalse(pokemonEncontrado);
    }

    @Test
    void deveEncontrarPokemonComNivelEntreMinimoEMaximo() {
        boolean pokemonEncontrado = pokemonService.buscarPorNivel("10,30").contains("MOLTRES");
        Assertions.assertTrue(pokemonEncontrado);
    }

    @Test
    void naoDeveEncontrarPokemonComNivelAcimaDoMaximo() {
        boolean pokemonEncontrado = pokemonService.buscarPorNivel("10,19").contains("MOLTRES");
        Assertions.assertFalse(pokemonEncontrado);
    }

    @Test
    void naoDeveEncontrarPokemonComNivelAbaixoDoMinimo() {
        boolean pokemonEncontrado = pokemonService.buscarPorNivel("21,80").contains("MOLTRES");
        Assertions.assertFalse(pokemonEncontrado);
    }


    @Test
    void deveEncontrarPokemonPorEspecie() {
        boolean pokemonEncontrado = pokemonService.buscarPorEspecie("MOLTRES").contains("MOLTRES");
        Assertions.assertTrue(pokemonEncontrado);
    }

    @Test
    void naoDeveEncontrarPokemonPorEspecie() {
        boolean pokemonEncontrado = pokemonService.buscarPorEspecie("MOLTRES").contains("MEW");
        Assertions.assertFalse(pokemonEncontrado);
    }

    @Test
    void deveEncontrarPokemonPorTipo() {
        boolean pokemonEncontrado = pokemonService.buscarPorTipo("FOGO").contains("MOLTRES");
        Assertions.assertTrue(pokemonEncontrado);
    }

    @Test
    void naoDeveEncontrarPokemonPorTipo() {
        boolean pokemonEncontrado = pokemonService.buscarPorTipo("AGUA").contains("MOLTRES");
        Assertions.assertFalse(pokemonEncontrado);
    }
}