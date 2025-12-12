/**
 * @author falvesmac
 */

package br.com.falves.domain;

import br.com.falves.builder.TreinadorBuilder;

import java.util.List;
import java.util.Objects;

public class Treinador {
    private Long id;
    private String nome;
    private Regioes regiao;
    private Integer idade, insignias;
    private List<Pokemon> equipe;
    private List<Pokemon> box;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Regioes getRegiao() {
        return regiao;
    }

    public void setRegiao(Regioes regiao) {
        this.regiao = regiao;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Integer getInsignias() {
        return insignias;
    }

    public void setInsignias(Integer insignias) {
        this.insignias = insignias;
    }

    public List<Pokemon> getEquipe() {
        return equipe;
    }

    public void setEquipe(List<Pokemon> equipe) {
        this.equipe = equipe;
    }

    public List<Pokemon> getBox() {
        return box;
    }

    public void setBox(List<Pokemon> box) {
        this.box = box;
    }

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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Treinador treinador = (Treinador) o;
        return Objects.equals(id, treinador.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
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