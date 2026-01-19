/**
 * @author falvesmac
 */

package br.com.falves.domain;
import br.com.falves.builder.PokemonBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Pokemon {
    private Long numero;
    private Integer nivel;
    private EspeciePokemon especiePokemon;
    private Treinador treinador;

    public Pokemon(PokemonBuilder pokemonBuilder) {
        this.numero = pokemonBuilder.getNumero();
        this.especiePokemon = pokemonBuilder.getEspecie();
        this.nivel = pokemonBuilder.getNivel();
        this.treinador = pokemonBuilder.getTreinador();
    }

    public static PokemonBuilder builder(){
        return new PokemonBuilder();
    }

    @Override
    public String toString() {
        String nomeTreinador = (treinador == null) ? "Selvagem" : treinador.getNome();

        return "NUM: " + numero + " | Espécie: " + especiePokemon.toString() + " | Nível: " + nivel + " | Treinador: " + nomeTreinador;
    }
}