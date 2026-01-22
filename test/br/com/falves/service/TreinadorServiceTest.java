package br.com.falves.service;
import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Regioes;
import br.com.falves.domain.Treinador;
import br.com.falves.dao.PokemonRepository;
import br.com.falves.dao.TreinadorRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TreinadorServiceTest {
    TreinadorService treinadorService;
    static final TreinadorRepository treinadorRepository = new TreinadorRepository("projeto-pokemon-test");
    static final PokemonRepository pokemonRepository = new PokemonRepository("projeto-pokemon-test");

    Pokemon pokemon;
    Treinador treinador;

    @BeforeEach
    public void init(){
        treinadorService = new TreinadorService(treinadorRepository, pokemonRepository);
        treinadorRepository.limparTabela();

        pokemon = Pokemon.builder()
                .numero(1L)
                .especiePokemon(EspeciePokemon.MOLTRES)
                .nivel(20)
                .build();

        pokemonRepository.cadastrar(pokemon);

        treinador = Treinador.builder()
                .id(1L)
                .nome("Ash")
                .regiao(Regioes.KANTO)
                .idade(15)
                .insignias(2)
                .build();

        treinadorRepository.cadastrar(treinador);
    }

    @AfterAll
    public static void after(){
        TreinadorRepository treinadorRepository = new TreinadorRepository("projeto-pokemon-test");
        treinadorRepository.limparTabela();
    }

    @Test
    void deveCadastrarTreinadorComDadosValidos() {
        Treinador treinador1 = Treinador.builder()
                        .id(2L)
                        .nome("Misty")
                        .regiao(Regioes.KANTO)
                        .idade(12)
                        .insignias(2)
                        .build();

        treinadorService.cadastrarTreinador(treinador1);
        Treinador treinadorConsultado = treinadorService.consultarTreinador("2");

        Assertions.assertEquals(2L, treinadorConsultado.getId());
    }

    @Test
    void naoDeveCadastrarComDadosInvalidos() {
        Treinador treinador1 = Treinador.builder()
                .id(2L)
                .regiao(Regioes.KANTO)
                .idade(12)
                .insignias(2)
                .build();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            treinadorService.cadastrarTreinador(treinador1);
        });
    }

    @Test
    void consultarTreinador() {
        Treinador treinadorEncontrado = treinadorService.consultarTreinador("1");
        boolean treinadorFoiEncontrado = treinadorEncontrado.toString().equals(treinador.toString());
        Assertions.assertTrue(treinadorFoiEncontrado);
    }

    @Test
    void naoDeveEncontrarTreinadorComDadosInvalidos(){
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
        Treinador treinadorAlterado = Treinador.builder()
                        .id(1L)
                        .nome("Misty")
                        .regiao(Regioes.PALDEA)
                        .idade(12)
                        .insignias(2)
                        .build();
        treinadorService.editarTreinador(treinador, dadosNovos);
        treinador = treinadorService.consultarTreinador("1");

        boolean treinadorFoiAlterado = treinadorAlterado.toString().equals(treinador.toString());
        Assertions.assertTrue(treinadorFoiAlterado);
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
        Treinador treinadorComPokemonNoTime = treinadorService.consultarTreinador("1");
        Pokemon pokemonNoTime = pokemonRepository.consultar(1L);
        boolean pokemonEstaNoTime = treinadorComPokemonNoTime.getEquipe().contains(pokemonNoTime);
        Assertions.assertTrue(pokemonEstaNoTime);
    }

    @Test
    void removerPokemonDoTime() {
        treinadorService.capturarPokemon(treinador, pokemon);
        treinadorService.removerPokemonDoTime(treinador, pokemon);
        Treinador treinadorSemPokemonNoTime = treinadorService.consultarTreinador("1");
        boolean pokemonNaBox = !treinadorSemPokemonNoTime.getEquipe().contains(pokemon) && treinadorSemPokemonNoTime.getBox().contains(pokemon);
        Assertions.assertTrue(pokemonNaBox);
    }

    @Test
    public void adicionaSetimoPokemonNaBox(){
        Pokemon pk1,pk2,pk3,pk4,pk5,pk6;

        pk1 = Pokemon.builder()
                .numero(2L)
                .especiePokemon(EspeciePokemon.RAICHU)
                .nivel(20)
                .build();

        pk2 = Pokemon.builder()
                .numero(3L)
                .especiePokemon(EspeciePokemon.ZAPDOS)
                .nivel(20)
                .build();

        pk3 = Pokemon.builder()
                .numero(4L)
                .especiePokemon(EspeciePokemon.CHARIZARD)
                .nivel(20)
                .build();

        pk4 = Pokemon.builder()
                .numero(5L)
                .especiePokemon(EspeciePokemon.ARTICUNO)
                .nivel(20)
                .build();

        pk5 = Pokemon.builder()
                .numero(6L)
                .especiePokemon(EspeciePokemon.GEODUDE)
                .nivel(20)
                .build();

        pk6 = Pokemon.builder()
                .numero(7L)
                .especiePokemon(EspeciePokemon.PARAS)
                .nivel(20)
                .build();

        pokemonRepository.cadastrar(pk1);
        pokemonRepository.cadastrar(pk2);
        pokemonRepository.cadastrar(pk3);
        pokemonRepository.cadastrar(pk4);
        pokemonRepository.cadastrar(pk5);
        pokemonRepository.cadastrar(pk6);

        treinadorService.capturarPokemon(treinador, pokemon);
        treinadorService.capturarPokemon(treinador, pk1);
        treinadorService.capturarPokemon(treinador, pk2);
        treinadorService.capturarPokemon(treinador, pk3);
        treinadorService.capturarPokemon(treinador, pk4);
        treinadorService.capturarPokemon(treinador, pk5);
        treinadorService.capturarPokemon(treinador, pk6);

        boolean pokemonNaBox = treinador.getBox().contains(pk6);

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