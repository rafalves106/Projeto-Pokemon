/**
 * @author falvesmac
 */

package br.com.falves.builder;

import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Treinador;

public class PokemonBuilder {
    private Long numero;
    private EspeciePokemon especie;
    private Integer nivel;
    private Treinador treinador;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public EspeciePokemon getEspecie() {
        return especie;
    }

    public void setEspecie(EspeciePokemon especie) {
        this.especie = especie;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    public PokemonBuilder numero(Long numero){
        this.numero = numero;
        return this;
    }

    public PokemonBuilder especie(EspeciePokemon especie){
        this.especie = especie;
        return this;
    }

    public PokemonBuilder nivel(Integer nivel){
        this.nivel = nivel;
        return this;
    }

    public PokemonBuilder treinador(Treinador treinador){
        this.treinador = treinador;
        return this;
    }

    public Pokemon build(){
        return new Pokemon(this);
    }

}