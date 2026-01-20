/**
 * @author falvesmac
 */

package br.com.falves.service;

import br.com.falves.dao.IPokemonDAO;
import br.com.falves.dao.PokemonMapDAO;
import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.TipoPokemon;
import br.com.falves.repository.PokemonRepository;

import javax.swing.*;

public class PokemonService {
    private final PokemonRepository pokemonRepository = new PokemonRepository();

    // CREATE - CADASTRA POKÉMON
    public void cadastrarPokemon(String dados) {

        if (dados == null) {
            throw new IllegalArgumentException("Dados inválidos.");
        }

        try {
            String[] dadosSeparados = dados.split(",");

            if (dadosSeparados.length != 3) {
                throw new IllegalArgumentException("Dados Incorretos! Padrão: NÚMERO, ESPÉCIE, NÍVEL");
            }

            Long num = Long.parseLong(dadosSeparados[0]);
            String especieStr = dadosSeparados[1].trim().toUpperCase();
            EspeciePokemon especie = EspeciePokemon.valueOf(especieStr);
            Integer nivel = Integer.parseInt(dadosSeparados[2]);

            Pokemon pokemon = Pokemon.builder().numero(num).especiePokemon(especie).nivel(nivel).build();

            Boolean isCadastrado = pokemonRepository.cadastrar(pokemon);

            if (!isCadastrado) {
                throw new IllegalArgumentException("Pokémon com NUM " + num + " já existe!");
            }

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Erro no campo numérico NUM.");

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);

        } catch (Exception e) {
            throw new RuntimeException("Erro inesperado: " + e.getMessage());
        }
    }

    // READ - RETORNA POKÉMON BUSCADO
    public Pokemon consultarPokemon(String idStr){

        // SE O NUM FOR NULO OU VAZIO RETORNA ERRO
        if (idStr == null || idStr.trim().isEmpty()){
            throw new IllegalArgumentException("Digite um NUM válido.");
        }

        // TENTA
        try {
            // TRANSFORMA O NUM RECEBIDO COMO STRING EM LONG
            Long num = Long.parseLong(idStr.trim());

            // CONSULTA NO MAP COM O NUM
            Pokemon pokemon = pokemonRepository.consultar(num);

            // SE O POKÉMON RETORNADO FOR NULO RETORNA ERRO
            if (pokemon == null){
                throw new IllegalArgumentException("Pokémon com NUM " + num + " não encontrado!");
            }

            // RETORNA O TREINADOR
            return pokemon;

            // CAPTURA ERRO DE NÚMERO INVÁLIDO CASO NÃO CONSIGA TRANSFORMAR O STRING EM LONG
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("NUM inválido! Digite apenas números inteiros.");
        }
    }

    // READ DE POKÉMONS - LISTA TODOS OS POKÉMONS
    public String listaPokemons(){
        if(pokemonRepository.buscarTodos().isEmpty()){
            throw new IllegalArgumentException("Nenhum pokémon cadastrado.\nCadastre um novo pokémon.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Listando os Pokémons Cadastrados: \n");
        pokemonRepository.buscarTodos().forEach(t -> sb.append(t.toString()).append("\n"));

        return sb.toString();
    }

    // UPDATE - EDITA POKÉMON
    public void editarPokemon(Pokemon pokemon, String dadosNovos) {
        if(dadosNovos == null){
            throw new IllegalArgumentException("Dados inválidos.");
        }
        try {
            String[] dadosSeparados = dadosNovos.split(",");

            if(dadosSeparados.length != 2){
                throw new IllegalArgumentException("Dados inválidos. Informe: ESPÉCIE, NÍVEL");
            }

            String especieStr = dadosSeparados[0].trim().toUpperCase();
            EspeciePokemon especie = EspeciePokemon.valueOf(especieStr);
            int nivel = Integer.parseInt(dadosSeparados[1].trim());

            if(nivel <= 0 || nivel > 100){
                throw new IllegalArgumentException("Nível deve ser positivo entre 1 e 100.");
            }
            Pokemon pokemonAntigo = pokemonRepository.consultar(pokemon.getNumero());

            if (pokemonAntigo != null && pokemonAntigo.getTreinador() != null) {
               Pokemon.builder().treinador(pokemonAntigo.getTreinador());
            }

            Pokemon pokemonAlterado = Pokemon.builder().nivel(nivel).numero(pokemon.getNumero()).especiePokemon(especie).build();

            pokemonRepository.alterar(pokemonAlterado);

        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Erro: Nível deve ser um número inteiro.");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Erro inesperado: " + e.getMessage());
        }
    }

    // DELETE - EXCLUI POKÉMON
    public void excluirPokemon(Pokemon pokemon) {
        pokemonRepository.excluir(pokemon.getNumero());
    }

    // READ - RETORNA POKÉMONS A PARTIR DE UM NÍVEL MÍNIMO
    public String buscarPorNivel(String niveis){

        if (niveis == null) {
            throw new IllegalArgumentException("Erro: Insira os valores corretamente.");
        };

        String[] niveisSeparados = niveis.split(",");

        if(niveisSeparados.length < 1 || niveisSeparados.length > 2){
            throw new IllegalArgumentException("Valores inválidos. Informe: NÍVEL MÍNIMO, NÍVEL MÁXIMO");
        }

        String nivelMinimoStr = niveisSeparados[0];
        int nivelMinimo = Integer.parseInt(nivelMinimoStr.trim());

        StringBuilder sb = new StringBuilder();

        if (nivelMinimo < 0 || nivelMinimo > 100) {
            throw new IllegalArgumentException("Erro: O nível deve ser de 1 a 100.");
        }

        if (niveisSeparados.length == 2) {
            String nivelMaximoStr = niveisSeparados[1];
            int nivelMaximo = Integer.parseInt(nivelMaximoStr.trim());

            if (nivelMaximo < nivelMinimo && nivelMaximo < 0 || nivelMaximo > 100) {
                throw new IllegalArgumentException("Erro: O Nível Máximo deve ser de 1 a 100 e maior que o Nível Mínimo.");
            }

            sb.append("Listando todos os Pokémons do nível ").append(nivelMinimo).append(" ao nível: ").append(nivelMaximo).append("\n");
            pokemonRepository.buscarPorNivel(nivelMinimo, nivelMaximo).forEach(p -> sb.append(p.toString()).append("\n"));
        } else {
            sb.append("Listando todos os Pokémons de nível ").append(nivelMinimo).append(" ou maior. \n");
            pokemonRepository.buscarPorNivel(nivelMinimo).forEach(p -> sb.append(p.toString()).append("\n"));
        }

        return sb.toString();
    }

    public String buscarPorEspecie(String especieStr){

        if(especieStr == null){
            throw new IllegalArgumentException("Espécie inválida.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Listando todos os Pokémons da espécie: ").append(especieStr).append("\n");

        pokemonRepository.buscarPorEspecie(EspeciePokemon.valueOf(especieStr)).forEach(p -> sb.append(p.toString()).append("\n"));

        return sb.toString();
    }

    public String buscarPorTipo(String tipoStr){

        if(tipoStr == null){
            throw new IllegalArgumentException("Espécie inválida.");
        }

        String tipoFormatado = tipoStr.trim().substring(0, 1).toUpperCase() +
                tipoStr.trim().substring(1).toLowerCase();

        StringBuilder sb = new StringBuilder();
        sb.append("Listando todos os Pokémons do tipo: ").append(tipoFormatado).append("\n");

        pokemonRepository.buscarPorTipo(TipoPokemon.valueOf(tipoFormatado)).forEach(p -> sb.append(p.toString()).append("\n"));

        return sb.toString();
    }

    public String listarEspecies(){
        StringBuilder listarEspecies = new StringBuilder();

        int contador = 0;

        for (EspeciePokemon especiePokemon : EspeciePokemon.values()){
            listarEspecies.append(especiePokemon).append(" | ");
            contador++;

            if (contador % 3 == 0) listarEspecies.append("\n");
        }
        return listarEspecies.toString();
    }

    public String listarTipos(){
        StringBuilder listarTipos = new StringBuilder();

        int contador = 0;

        for (TipoPokemon tipoPokemon : TipoPokemon.values()){
            listarTipos.append(tipoPokemon).append(" | ");
            contador++;

            if (contador % 3 == 0) listarTipos.append("\n");
        }
        return listarTipos.toString();
    }
}