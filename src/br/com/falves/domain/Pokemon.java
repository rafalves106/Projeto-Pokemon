/**
 * @author falvesmac
 */

package br.com.falves.domain;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_pokemon")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pokemon {
    @Id
    @EqualsAndHashCode.Include
    private Long numero;
    private Integer nivel;

    @Enumerated(EnumType.STRING)
    private EspeciePokemon especiePokemon;

    @Enumerated(EnumType.STRING)
    private StatusPokemon status;

    @ManyToOne
    @JoinColumn(name = "treinador_id")
    private Treinador treinador;

    @Override
    public String toString() {
        String nomeTreinador = (treinador == null) ? "Selvagem" : treinador.getNome();
        return "Espécie: " + especiePokemon + " | Nível: " + nivel + " | Treinador: " + nomeTreinador;
    }
}