package br.com.falves.dao;

import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Treinador;

import java.util.Collection;

public interface ITreinadorDAO {
    public Boolean cadastrar(Treinador treinador);

    public void excluir(Long id);

    public void alterar(Treinador treinador);

    public void adicionarAoTime(Pokemon pokemon, Treinador treinador);

    public void removerDoTime(Pokemon pokemon, Treinador treinador);

    public void adicionarNaBox(Pokemon pokemon, Treinador treinador);

    public void removerDaBox(Pokemon pokemon, Treinador treinador);

    public Treinador consultar(Long id);

    public Collection<Treinador> buscarTodos();
}
