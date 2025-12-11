/**
 * @author falvesmac
 */

package br.com.falves.domain;
import java.util.Objects;

public class Pokemon {
    private Long numero;
    private Integer nivel;
    private EspeciePokemon especiePokemon;
    private Treinador treinador;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public EspeciePokemon getEspeciePokemon() {
        return especiePokemon;
    }

    public void setEspeciePokemon(EspeciePokemon especiePokemon) {
        this.especiePokemon = especiePokemon;
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public void setTreinador(Treinador treinador) {
        this.treinador = treinador;
    }

    public Pokemon(Long numero, EspeciePokemon especiePokemon, Integer nivel) {
        this.numero = numero;
        this.especiePokemon = especiePokemon;
        this.nivel = nivel;
        this.treinador = null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return Objects.equals(numero, pokemon.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(numero);
    }

    @Override
    public String toString() {
        String nomeTreinador = (treinador == null) ? "Selvagem" : treinador.getNome();

        return "NUM: " + numero + " | Espécie: " + especiePokemon.toString() + " | Nível: " + nivel + " | Treinador: " + nomeTreinador;
    }
}