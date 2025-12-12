/**
 * @author falvesmac
 */

package br.com.falves.dao;

import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.TipoPokemon;

import java.util.*;
import java.util.stream.Collectors;

public class PokemonMapDAO implements IPokemonDAO{
    private static PokemonMapDAO instance;
    private Map<Long, Pokemon> map;
    private PokemonMapDAO(){
        this.map = new TreeMap<>();
    }

    public static synchronized PokemonMapDAO getInstance(){
        if(instance==null){
            instance = new PokemonMapDAO();
        }
        return instance;
    }

    @Override
    public Boolean cadastrar(Pokemon pokemon) {
        if (map.containsKey(pokemon.getNumero())) {
            return false;
        }

        map.put(pokemon.getNumero(), pokemon);
        return true;
    }

    @Override
    public void excluir(Long id) {
        map.remove(id);
    }

    @Override
    public void alterar(Pokemon pokemon) {
        Pokemon pokemonCadastrado = map.get(pokemon.getNumero());
        pokemonCadastrado.setNumero(pokemon.getNumero());
        pokemonCadastrado.setEspeciePokemon(pokemon.getEspeciePokemon());
        pokemonCadastrado.setNivel(pokemon.getNivel());
    }

    @Override
    public Pokemon consultar(Long numero) {
        return this.map.get(numero);
    }

    @Override
    public Collection<Pokemon> buscarTodos() {
        return this.map.values();
    }

    @Override
    public Collection<Pokemon> buscarPorTipo(TipoPokemon tipo) {
        return this.map.values().stream()
                .filter(p -> p.getEspeciePokemon().getTipo().equals(tipo))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Pokemon> buscarPorNivel(int nivelMinimo) {
        System.out.println(nivelMinimo);
        return this.map.values().stream()
                .filter(p -> p.getNivel() >= nivelMinimo)
                .sorted(Comparator.comparing(Pokemon::getNivel))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Pokemon> buscarPorEspecie(EspeciePokemon especie) {
        return this.map.values().stream()
                .filter(p -> p.getEspeciePokemon().equals(especie))
                .sorted(Comparator.comparing(Pokemon::getNivel))
                .collect(Collectors.toList());
    }
}