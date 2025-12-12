/**
 * @author falvesmac
 */

package br.com.falves.builder;

import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Regioes;
import br.com.falves.domain.Treinador;

import java.util.ArrayList;
import java.util.List;

public class TreinadorBuilder{
    private Long id;
    private String nome;
    private Regioes regiao;
    private Integer idade, insignias;

    private List<Pokemon> equipe = new ArrayList<>();
    private List<Pokemon> box = new ArrayList<>();

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

    public TreinadorBuilder id(Long id){
        this.id = id;
        return this;
    }

    public TreinadorBuilder nome(String nome){
        this.nome = nome;
        return this;
    }

    public TreinadorBuilder regiao(Regioes regiao){
        this.regiao = regiao;
        return this;
    }

    public TreinadorBuilder idade(Integer idade){
        this.idade = idade;
        return this;
    }

    public TreinadorBuilder insignias(Integer insignias){
        this.insignias = insignias;
        return this;
    }

    public TreinadorBuilder equipe(List<Pokemon> equipe){
        this.equipe = equipe;
        return this;
    }

    public TreinadorBuilder equipeVazia(){
        this.equipe = new ArrayList<>();
        return this;
    }

    public TreinadorBuilder box(List<Pokemon> box){
        this.box = box;
        return this;
    }

    public TreinadorBuilder boxVazia(){
        this.equipe = new ArrayList<>();
        return this;
    }

    public Treinador build(){
        return new Treinador(this);
    }
}