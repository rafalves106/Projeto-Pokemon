/**
 * @author falvesmac
 */

package br.com.falves.domain;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "O número do pokémon não pode ser nulo.")
    private Long numero;

    @NotNull(message = "O nível do pokémon não pode estar vazio")
    @Min(value = 1, message = "Nível mínimo é 1")
    @Max(value = 100, message = "Nível máximo é 100")
    @Column(nullable = false)
    private Integer nivel;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "A espécie do pokémon não pode estar vazia.")
    @Column(nullable = false)
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