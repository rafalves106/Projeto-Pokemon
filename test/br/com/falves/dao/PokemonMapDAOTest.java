package br.com.falves.dao;

import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PokemonMapDAOTest {
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
    public void pesquisarPokemon(){
        Pokemon pokemonConsultado = iPokemonDAO.consultar(pokemon.getNumero());
        Assertions.assertNotNull(pokemonConsultado);
    }

    @Test
    public void excluirPokemon(){
        iPokemonDAO.excluir(pokemon.getNumero());
        Pokemon pokemonConsultado = iPokemonDAO.consultar(pokemon.getNumero());
        Assertions.assertNull(pokemonConsultado);
    }

    @Test
    public void alterarPokemon(){
        EspeciePokemon especie = EspeciePokemon.CHARIZARD;
        Integer nivel = 30;

        Pokemon pokemonAlterado = Pokemon.builder()
                .numero(pokemon.getNumero())
                .especie(especie)
                .nivel(nivel)
                .build();

        iPokemonDAO.alterar(pokemonAlterado);

        Pokemon pokemonConsultado = iPokemonDAO.consultar(pokemon.getNumero());

        Assertions.assertEquals(pokemonAlterado, pokemonConsultado);
    }

    @Test
    public void naoDeveCadastrarPokemonComMesmoNumero() {
        Pokemon pokemonNumeroIgual = Pokemon.builder()
                .numero(pokemon.getNumero())
                .especie(EspeciePokemon.ALAKAZAM)
                .nivel(10)
                .build();

        Boolean resultado = iPokemonDAO.cadastrar(pokemonNumeroIgual);

        Assertions.assertFalse(resultado, "Não deve permitir o cadastro de 2 IDS iguais");
    }

    @Test
    public void naoDeveEncontrarPokemonInexistente() {
        Pokemon pokemonNaoEncontrado = iPokemonDAO.consultar(999L);

        Assertions.assertNull(pokemonNaoEncontrado);
    }
}