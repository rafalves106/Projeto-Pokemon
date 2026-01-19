/**
 * @author falvesmac
 */

package br.com.falves.domain;

import br.com.falves.builder.TreinadorBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
public class Treinador {
    private Long id;
    private String nome;
    private Regioes regiao;
    private Integer idade, insignias;
    private List<Pokemon> equipe;
    private List<Pokemon> box;

    public Treinador(TreinadorBuilder treinadorBuilder) {
        this.id = treinadorBuilder.getId();
        this.nome = treinadorBuilder.getNome();
        this.regiao = treinadorBuilder.getRegiao();
        this.idade = treinadorBuilder.getIdade();
        this.insignias = treinadorBuilder.getInsignias();
        this.equipe = treinadorBuilder.getEquipe();
        this.box = treinadorBuilder.getBox();
    }

    public static TreinadorBuilder builder(){
        return new TreinadorBuilder();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (equipe.isEmpty() && box.isEmpty()) {
            sb.append(" (Sem pokémons)");
        } else if(!equipe.isEmpty()){
            for(Pokemon pokemon : equipe){
                sb.append("\n   -> ");
                sb.append(pokemon.toString());
            }
        } else if(!box.isEmpty()){
            for(Pokemon pokemon : box){
                sb.append("\n   -> ");
                sb.append(pokemon.toString());
            }
        }
        return "ID: " + id + " | Nome: " + nome + " | Região: " + regiao.toString() + "\nPokémon:" + sb;
    }
}