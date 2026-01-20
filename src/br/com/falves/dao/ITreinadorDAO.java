package br.com.falves.dao;

import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Treinador;

import java.util.Collection;

public interface ITreinadorDAO {
    public Boolean cadastrar(Treinador treinador);

    public void excluir(Long id);

    public void alterar(Treinador treinador);

    public void adicionarAoTime(Treinador treinador);

    public void removerDoTime(Treinador treinador);

    public void adicionarNaBox(Treinador treinador);

    public void removerDaBox(Treinador treinador);

    public Treinador consultar(Long id);

    public Collection<Treinador> buscarTodos();
}
