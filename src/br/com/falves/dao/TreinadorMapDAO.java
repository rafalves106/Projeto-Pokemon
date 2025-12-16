/**
 * @author falvesmac
 */

package br.com.falves.dao;

import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Treinador;

import javax.swing.*;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class TreinadorMapDAO implements ITreinadorDAO{

    private static TreinadorMapDAO instance;

    private Map<Long, Treinador> map;

    private TreinadorMapDAO(){
        this.map = new TreeMap<>();
    }

    public static synchronized TreinadorMapDAO getInstance(){
        if(instance == null){
            instance = new TreinadorMapDAO();
        }
        return instance;
    }

    @Override
    public Boolean cadastrar(Treinador treinador) {
        if (map.containsKey(treinador.getId())) {
            return false;
        }

        map.put(treinador.getId(), treinador);
        return true;
    }

    @Override
    public void excluir(Long id) {
        map.remove(id);
    }

    @Override
    public void alterar(Treinador treinador) {
        Treinador treinadorCadastrado = map.get(treinador.getId());

        treinadorCadastrado.setNome(treinador.getNome());
        treinadorCadastrado.setIdade(treinador.getIdade());
        treinadorCadastrado.setRegiao(treinador.getRegiao());
        treinadorCadastrado.setInsignias(treinador.getInsignias());
    }

    @Override
    public void adicionarAoTime(Pokemon pokemon, Treinador treinador) {
        treinador.getEquipe().add(pokemon);
    }

    @Override
    public void removerDoTime(Pokemon pokemon, Treinador treinador) {
        treinador.getEquipe().remove(pokemon);
    }

    @Override
    public void adicionarNaBox(Pokemon pokemon, Treinador treinador) {
        treinador.getBox().add(pokemon);
    }

    @Override
    public void removerDaBox(Pokemon pokemon, Treinador treinador) {
        treinador.getBox().remove(pokemon);
    }


    @Override
    public Treinador consultar(Long id) {
        return this.map.get(id);
    }

    @Override
    public Collection<Treinador> buscarTodos() {
        return this.map.values();
    }
}