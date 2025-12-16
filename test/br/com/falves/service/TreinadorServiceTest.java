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

class TreinadorServiceTest {
    TreinadorService treinadorService = new TreinadorService();
    IPokemonDAO iPokemonDAO = PokemonMapDAO.getInstance();
    ITreinadorDAO iTreinadorDAO = TreinadorMapDAO.getInstance();

    Pokemon pokemon;
    Treinador treinador;

    @BeforeEach
    public void init(){
        iPokemonDAO.buscarTodos().clear();
        iTreinadorDAO.buscarTodos().clear();

        pokemon = Pokemon.builder()
                .numero(1L)
                .especie(EspeciePokemon.MOLTRES)
                .nivel(20)
                .build();

        iPokemonDAO.cadastrar(pokemon);

        treinador = Treinador.builder()
                .id(1L)
                .nome("Ash")
                .regiao(Regioes.KANTO)
                .idade(15)
                .insignias(2)
                .build();

        iTreinadorDAO.cadastrar(treinador);
    }

    @Test
    void deveCadastrarTreinadorComDadosValidos() {
        String dados = "2,Misty,Kanto,12,2";
        treinadorService.cadastrarTreinador(dados);
        Treinador treinadorConsultado = iTreinadorDAO.consultar(2L);

        Assertions.assertEquals(2L, treinadorConsultado.getId());
    }

    @Test
    void naoDeveCadastrarComDadosInvalidos() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            treinadorService.cadastrarTreinador("2,Misty");
        });
    }

    @Test
    void consultarTreinador() {
        boolean treinadorEncontrado = treinadorService.consultarTreinador("1").equals(treinador);
        Assertions.assertTrue(treinadorEncontrado);
    }

    @Test
    void naoEncontraTreinador(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            treinadorService.consultarTreinador("2");
        });
    }

    @Test
    void listaTreinadores(){
        boolean listaTodos = !treinadorService.listaTreinadores().isEmpty();
        Assertions.assertTrue(listaTodos);
    }

    @Test
    void listaNenhumTreinador(){
        treinadorService.excluirTreinador(treinador);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            treinadorService.listaTreinadores();
        });
    }

    @Test
    void editarTreinador() {
        String dadosNovos = "Misty,Paldea,12,2";
        treinadorService.editarTreinador(treinador, dadosNovos);
        boolean treinadorAlterado = treinador.getNome().contains("Misty") && treinador.getRegiao() == Regioes.PALDEA && treinador.getIdade() == 12 && treinador.getInsignias() == 2;
        Assertions.assertTrue(treinadorAlterado);
    }

    @Test
    void naoEditaTreinadorComDadosInvalidos() {
        String dadosNovos = "Misty, Paldea, 2";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            treinadorService.editarTreinador(treinador, dadosNovos);
        });
    }

    @Test
    void excluirTreinador() {
        treinadorService.excluirTreinador(treinador);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            treinadorService.consultarTreinador(String.valueOf(treinador.getId()));
        });
    }

    @Test
    void capturarPokemon() {
        treinadorService.capturarPokemon(treinador, pokemon);
        boolean pokemonNoTime = treinador.getEquipe().contains(pokemon);
        Assertions.assertTrue(pokemonNoTime);
    }

    @Test
    void removerPokemonDoTime() {
        treinadorService.capturarPokemon(treinador, pokemon);
        treinadorService.removerPokemonDoTime(treinador, pokemon);

        boolean pokemonNaBox = !treinador.getEquipe().contains(pokemon) && treinador.getBox().contains(pokemon);

        Assertions.assertTrue(pokemonNaBox);
    }

    @Test
    public void adicionaSetimoPokemonNaBox(){
        Pokemon pk1,pk2,pk3,pk4,pk5,pk6;

        pk1 = Pokemon.builder()
                .numero(2L)
                .especie(EspeciePokemon.MOLTRES)
                .nivel(20)
                .build();

        pk2 = Pokemon.builder()
                .numero(3L)
                .especie(EspeciePokemon.ZAPDOS)
                .nivel(20)
                .build();

        pk3 = Pokemon.builder()
                .numero(4L)
                .especie(EspeciePokemon.CHARIZARD)
                .nivel(20)
                .build();

        pk4 = Pokemon.builder()
                .numero(5L)
                .especie(EspeciePokemon.ARTICUNO)
                .nivel(20)
                .build();

        pk5 = Pokemon.builder()
                .numero(6L)
                .especie(EspeciePokemon.GEODUDE)
                .nivel(20)
                .build();

        pk6 = Pokemon.builder()
                .numero(7L)
                .especie(EspeciePokemon.PARAS)
                .nivel(20)
                .build();

        iPokemonDAO.cadastrar(pk1);
        iPokemonDAO.cadastrar(pk2);
        iPokemonDAO.cadastrar(pk3);
        iPokemonDAO.cadastrar(pk4);
        iPokemonDAO.cadastrar(pk5);
        iPokemonDAO.cadastrar(pk6);

        treinadorService.capturarPokemon(treinador, pk1);
        treinadorService.capturarPokemon(treinador, pk2);
        treinadorService.capturarPokemon(treinador, pk3);
        treinadorService.capturarPokemon(treinador, pk4);
        treinadorService.capturarPokemon(treinador, pk5);
        treinadorService.capturarPokemon(treinador, pk6);
        treinadorService.capturarPokemon(treinador, pokemon);

        boolean pokemonNaBox = treinador.getBox().contains(pokemon);

        Assertions.assertTrue(pokemonNaBox);
    }

    @Test
    public void removePokemonDaBox(){
        treinadorService.capturarPokemon(treinador, pokemon);
        treinadorService.removerPokemonDoTime(treinador, pokemon);
        treinadorService.removePokemonDaBox(treinador, pokemon);

        boolean pokemonLivre = !treinador.getEquipe().contains(pokemon) && !treinador.getBox().contains(pokemon) && pokemon.getTreinador() == null;

        Assertions.assertTrue(pokemonLivre);
    }

    @Test
    public void listaPokemonsDaEquipe(){
        treinadorService.capturarPokemon(treinador, pokemon);
        boolean estaListado = treinadorService.listaEquipe(treinador).contains(pokemon.getEspeciePokemon().toString());

        Assertions.assertTrue(estaListado);
    }

    @Test
    public void listaPokemonsDaBox(){
        treinadorService.capturarPokemon(treinador, pokemon);
        treinadorService.removerPokemonDoTime(treinador, pokemon);
        boolean estaListado = treinadorService.listaBox(treinador).contains(pokemon.getEspeciePokemon().toString());

        Assertions.assertTrue(estaListado);
    }
}