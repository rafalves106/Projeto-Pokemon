package br.com.falves.dao;

import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.TipoPokemon;

import java.util.Collection;

public interface IPokemonDAO {
    public Boolean cadastrar(Pokemon pokemon);

    public void excluir(Long numero);

    public void alterar(Pokemon pokemon);

    public Pokemon consultar(Long numero);

    public Collection<Pokemon> buscarTodos();

    public Collection<Pokemon> buscarPorTipo(TipoPokemon tipo);

    public Collection<Pokemon> buscarPorNivel(int nivelMinimo);

    public Collection<Pokemon> buscarPorNivel(int nivelMinimo, int nivelMaximo);

    public Collection<Pokemon> buscarPorEspecie(EspeciePokemon especie);
}