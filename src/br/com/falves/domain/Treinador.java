/**
 * @author falvesmac
 */

package br.com.falves.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Where;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_treinador")
public class Treinador {

    @Id
    @EqualsAndHashCode.Include
    @NotNull(message = "O id do treinador não pode ser nulo.")
    private Long id;

    @NotNull(message = "O nome do treinador não pode ser nulo.")
    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "A região do treinador não pode estar vazia.")
    private Regioes regiao;

    @Column(nullable = false)
    @NotNull(message = "A idade do treinador não pode estar vazia.")
    private Integer idade;

    @Column(nullable = false)
    @NotNull(message = "As insígnias do treinador não pode estar vazia.")
    private Integer insignias;

    @OneToMany(mappedBy = "treinador", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Where(clause = "status = 'EQUIPE'")
    @Builder.Default
    private Set<Pokemon> equipe = new HashSet<>();

    @OneToMany(mappedBy = "treinador", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Where(clause = "status = 'BOX'")
    @Builder.Default
    private Set<Pokemon> box = new HashSet<>();


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(" | Nome: ").append(nome).append(" | Região: ").append(regiao);

        if (!equipe.isEmpty()) {
            sb.append("\n [EQUIPE]:");
            for (Pokemon p : equipe) {
                sb.append("\n   -> ").append(p.getEspeciePokemon()).append(" (Nvl ").append(p.getNivel()).append(")");
            }
        }

        if (!box.isEmpty()) {
            sb.append("\n [BOX]:");
            for (Pokemon p : box) {
                sb.append("\n   -> ").append(p.getEspeciePokemon()).append(" (Nvl ").append(p.getNivel()).append(")");
            }
        }

        if (equipe.isEmpty() && box.isEmpty()) {
            sb.append("\n (Sem pokémons)");
        }

        return sb.toString();
    }
}