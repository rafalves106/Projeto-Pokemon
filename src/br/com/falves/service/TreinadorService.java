/**
 * @author falvesmac
 */

package br.com.falves.service;

import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Regioes;
import br.com.falves.domain.StatusPokemon;
import br.com.falves.domain.Treinador;
import br.com.falves.dao.PokemonRepository;
import br.com.falves.dao.TreinadorRepository;

public class TreinadorService {
    private TreinadorRepository treinadorRepository;
    private PokemonRepository pokemonRepository;

    public TreinadorService() {
        this.treinadorRepository = new TreinadorRepository();
        this.pokemonRepository = new PokemonRepository();
    }

    public TreinadorService(TreinadorRepository treinadorRepository, PokemonRepository pokemonRepository) {
        this.treinadorRepository = treinadorRepository;
        this.pokemonRepository = pokemonRepository;
    }

    // CREATE - CADASTRA TREINADOR
    public void cadastrarTreinador(Treinador treinador){
        // TENTA
        try {
            // VARIÁVEL COM A VERIFICAÇÃO SE FOI CADASTRADO OU NÃO
            Boolean isCadastrado = treinadorRepository.cadastrar(treinador);

            // SE FOR FALSE, RETORNA UM ERRO
            if(!isCadastrado) {
                throw new IllegalArgumentException("Treinador com ID " + treinador.getId() + " já existe!");
            }

        // CAPTURA ERRO DE ARGUMENTOS NUMERAIS INVÁLIDOS
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Erro: ID, Idade e Insígnias devem ser números.");

        // CAPTURA ERRO DE ARGUMENTOS INVÁLIDOS
        } catch (IllegalArgumentException e) {
            throw e;

        // CAPTURA ERROS DIVERSOS
        } catch (Exception e){
            throw new RuntimeException("Erro inesperado: " + e.getMessage());
        }
    }

    // READ - RETORNA TREINADOR BUSCADO
    public Treinador consultarTreinador(String idStr){

        // SE O ID FOR NULO OU VAZIO RETORNA ERRO
        if (idStr == null || idStr.trim().isEmpty()){
            throw new IllegalArgumentException("Digite um id válido.");
        }

        // TENTA
        try {
            // TRANSFORMA O ID RECEBIDO COMO STRING EM LONG
            Long id = Long.parseLong(idStr.trim());

            // CONSULTA NO MAP COM O ID
            Treinador treinador = treinadorRepository.consultar(id);

            // SE O TREINADOR RETORNADO FOR NULO RETORNA ERRO
            if (treinador == null){
                throw new IllegalArgumentException("Treinador com ID " + id + " não encontrado!");
            }

            // RETORNA O TREINADOR
            return treinador;

        // CAPTURA ERRO DE NÚMERO INVÁLIDO CASO NÃO CONSIGA TRANSFORMAR O STRING EM LONG
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("ID inválido! Digite apenas números inteiros.");
        }
    }

    // READ DE TREINADORES - LISTA TODOS OS TREINADORES
    public String listaTreinadores(){
        if(treinadorRepository.buscarTodos().isEmpty()){
            throw new IllegalArgumentException("Nenhum treinador cadastrado.\nCadastre um novo treinador.");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("Listando os Treinadores Cadastrados: \n");
        treinadorRepository.buscarTodos().forEach(t -> sb.append(t.toString()).append("\n"));

        return sb.toString();
    }

    // UPDATE
    public void editarTreinador(Treinador treinador, String dadosNovos) {
        if(dadosNovos == null){
            throw new IllegalArgumentException("Dados inválidos.");
        }
        try {
            String[] dadosSeparados = dadosNovos.split(",");

            if(dadosSeparados.length != 4){
                throw new IllegalArgumentException("Dados inválidos. Informe: NOME,REGIÃO,IDADE,INSÍGNIAS");
            }

            String nome = dadosSeparados[0].trim();
            String regiao = dadosSeparados[1].trim();
            Integer idade = Integer.parseInt(dadosSeparados[2].trim());
            Integer insignias = Integer.parseInt(dadosSeparados[3].trim());

            Regioes regiaoFormatada = Regioes.valueOf(regiao.toUpperCase());

            Treinador treinadorAlterado = Treinador.builder()
                    .id(treinador.getId())
                    .nome(nome)
                    .regiao(regiaoFormatada)
                    .idade(idade)
                    .insignias(insignias)
                    .build();

            if(treinador.getEquipe() != null){
                treinadorAlterado.setEquipe(treinador.getEquipe());
            }

            if(treinador.getBox() != null){
                treinadorAlterado.setBox(treinador.getBox());
            }

            treinadorRepository.alterar(treinadorAlterado);

        } catch (NumberFormatException e){
            throw new IllegalArgumentException("Erro: ID, Idade e Insígnias devem ser números.");
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e){
            throw new RuntimeException("Erro inesperado: " + e.getMessage());
        }
    }

    // DELETE
    public void excluirTreinador(Treinador treinador) {
        treinadorRepository.excluir(treinador.getId());
    }

    // UPDATE DE POKÉMONS ASSOCIADOS - ATRIBUI O POKÉMON AO TREINADOR
    public String capturarPokemon(Treinador treinador, Pokemon pokemon) {

        // Verifica se o pokemon possui um treinador atual
        if(pokemon.getTreinador() != null){
            throw new IllegalArgumentException("Este Pokémon já pertence a um treinador.");
        }

        boolean foiParaBox = false;

        if (treinador.getEquipe().size() < 6) {
            pokemon.setStatus(StatusPokemon.EQUIPE);
            pokemon.setTreinador(treinador);
            treinador.getEquipe().add(pokemon);
        } else {
            pokemon.setStatus(StatusPokemon.BOX);
            pokemon.setTreinador(treinador);

            treinador.getBox().add(pokemon);
        }

        treinadorRepository.alterar(treinador);

        if (foiParaBox){
            return "Equipe cheia! " + pokemon.getEspeciePokemon() + " foi enviado para a Box.";
        } else {
            return pokemon.getEspeciePokemon() + " foi adicionado à equipe de " + treinador.getNome() + "!";
        }
    }

    // UPDATE DE POKÉMONS ASSOCIADOS - REMOVE POKÉMON ATRIBUIDO AO TREINADOR DO TIME PRINCIPAL
    public void removerPokemonDoTime(Treinador treinador, Pokemon pokemon){
        // Verifica se o pokemon possui um treinador diferente
        if(pokemon.getTreinador() != treinador){
            throw new IllegalArgumentException("Este Pokémon pertence a outro treinador.");
        }

        // Verifica se o Pokémon já está na box
        if(treinador.getBox().contains(pokemon)){
            throw new IllegalArgumentException("Este Pokémon já está na box do treinador.");
        }

        if (treinador.getEquipe().contains(pokemon)) {
            treinador.getEquipe().remove(pokemon);
            treinador.getBox().add(pokemon);
            pokemon.setStatus(StatusPokemon.BOX);
            treinadorRepository.alterar(treinador);
        }
    }

    // UPDATE DE POKÉMONS ASSOCIADOS - REMOVE O POKÉMON DA ATRIBUIÇÃO DO TREINADOR
    public void removePokemonDaBox(Treinador treinador, Pokemon pokemon) {

        // Verifica se o pokemon possui um treinador diferente
        if(pokemon.getTreinador() != treinador){
            throw new IllegalArgumentException("Este Pokémon pertence a outro treinador.");
        }

        if(treinador.getEquipe().contains(pokemon)){
            throw new IllegalArgumentException("Este Pokémon está na equipe principal do treinador. Envie para a box primeiro.");
        }

        treinador.getBox().remove(pokemon);
        pokemon.setTreinador(null);
        pokemon.setStatus(null);

        pokemonRepository.alterar(pokemon);
        treinadorRepository.removerDaBox(treinador);
    }

    // READ DE POKÉMONS ASSOCIADOS - LISTA OS POKÉMONS DA EQUIPE PRINCIPAL DO TREINADOR
    public String listaEquipe(Treinador treinador){
        StringBuilder sb = new StringBuilder();

        sb.append("Listando os Pokémons da Equipe do treinador: \n");
        treinador.getEquipe().forEach(pokemon -> sb.append(pokemon.toString()).append("\n"));

        return sb.toString();
    }

    // READ DE POKÉMONS ASSOCIADOS - LISTA OS POKÉMONS DA BOX DO TREINADOR
    public String listaBox(Treinador treinador){
        StringBuilder sb = new StringBuilder();

        sb.append("Listando os Pokémons da Box do treinador: \n");
        treinador.getBox().forEach(pokemon -> sb.append(pokemon.toString()).append("\n"));

        return sb.toString();
    }
}