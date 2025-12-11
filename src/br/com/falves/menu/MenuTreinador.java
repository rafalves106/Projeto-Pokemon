/**
 * @author falvesmac
 */

package br.com.falves.menu;
import br.com.falves.dao.ITreinadorDAO;
import br.com.falves.dao.TreinadorMapDAO;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Regioes;
import br.com.falves.domain.Treinador;

import javax.swing.*;

public class MenuTreinador {
   ITreinadorDAO iTreinadorDAO = TreinadorMapDAO.getInstance();

    // OPÇÕES DO MENU DE TREINADOR - CRUD
    // CREATE - CRIA UM NOVO TREINADOR
    public void cadastrarTreinador(){
        String message = "Digite os dados(ID,NOME,REGIÃO,IDADE,INSÍGNIAS) do treinador separados por vírgula.\nEX: 1,Ash,Kanto,13,8";

        String dados = JOptionPane.showInputDialog(null,
                message, "Cadastro de Treinador", JOptionPane.INFORMATION_MESSAGE);

        if (dados == null) return;

        try {
            String[] dadosSeparados = dados.split(",");

            if (dadosSeparados.length < 5){
                JOptionPane.showMessageDialog(null, "Dados Insuficientes! Padrão: ID,NOME,REGIÃO,IDADE,INSÍGNIAS",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dadosSeparados.length > 5){
                JOptionPane.showMessageDialog(null, "Dados Excessivos! Padrão: ID,NOME,REGIÃO,IDADE,INSÍGNIAS",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long id = Long.parseLong(dadosSeparados[0]);
            String nome = dadosSeparados[1].trim();
            String regiao = dadosSeparados[2].trim();
            Integer idade = Integer.parseInt(dadosSeparados[3].trim());
            Integer insignias = Integer.parseInt(dadosSeparados[4].trim());

            Regioes regiaoFormatada = Regioes.valueOf(regiao.toUpperCase());

            Treinador treinador = Treinador.builder()
                    .id(id)
                    .nome(nome)
                    .regiao(regiaoFormatada)
                    .idade(idade)
                    .insignias(insignias)
                    .equipe()
                    .build();

            Boolean isCadastrado = iTreinadorDAO.cadastrar(treinador);

            if(isCadastrado) {
                JOptionPane.showMessageDialog(null, "Treinador cadastrado com suceso ",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Treinador já se encontra cadastrado",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Erro nos campos numéricos (ID, IDADE ou INSÍGNIAS).", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // READ - CONSULTA UM TREINADOR COM O ID ESPECÍFICO
    public Treinador consultarTreinador(){
        // Inicializa tela esperando input que irá receber o id do treinador a ser consultado
        String idStr = JOptionPane.showInputDialog(null,
                "Digite o id do treinador a ser consultado.",
                "Busca de Treinador", JOptionPane.INFORMATION_MESSAGE);

        // Se o nid inserido for nulo ou vazio, retorna nulo
        if (idStr == null || idStr.trim().isEmpty()){
            return null;
        }

        try {
            // Transforma o valor inserido em Long
            Long id = Long.parseLong(idStr);

            // Consulta um treinador passando o Long
            Treinador treinador = iTreinadorDAO.consultar(id);

            // Se o retorno for nulo, inicializa tela informando
            if(treinador == null){
                JOptionPane.showMessageDialog(null, "Treinador não encontrado!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            // Se encontrar um treinador, o retorna
            return treinador;
        } catch (NumberFormatException e){
            // Caso não consiga converter o String para Long, inicializa tela informando e retorna nulo
            JOptionPane.showMessageDialog(null, "ID inválido! Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // READ - LISTA TODOS OS TREINADORES
    public void listarTreinadores(){
        StringBuilder sb = new StringBuilder();
        sb.append("Listando todos os treinadores: \n");

        for (Treinador treinador : iTreinadorDAO.buscarTodos()){
            sb.append(treinador.toString());
            sb.append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // UPDATE - ALTERA OS DADOS DE UM TREINADOR
    public void alterarTreinador(){
        Treinador treinador = consultarTreinador();

        if (treinador == null){
            return;
        }

        String message = "Digite os novos dados(NOME,REGIÃO,IDADE,INSÍGNIAS) do treinador separados por vírgula.\nEditando Treinador: " + treinador.getNome() + " | " + treinador.getRegiao() + " | " + treinador.getIdade() + " | " + treinador.getInsignias();

        String dados = JOptionPane.showInputDialog(null,
                message, "Edição de Treinador", JOptionPane.INFORMATION_MESSAGE);

        if (dados == null) return;

        try {
            String[] dadosSeparados = dados.split(",");

            if(dadosSeparados.length < 4){
                JOptionPane.showMessageDialog(null, "Informe: NOME,REGIÃO,IDADE,INSÍGNIAS",
                        "Dados Incompletos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(dadosSeparados.length > 4){
                JOptionPane.showMessageDialog(null, "Informe: NOME,REGIÃO,IDADE,INSÍGNIAS",
                        "Dados excessivos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long idOriginal = treinador.getId();
            String nome = dadosSeparados[0].trim();
            String regiao = dadosSeparados[1].trim();
            Integer idade = Integer.parseInt(dadosSeparados[2].trim());
            Integer insignias = Integer.parseInt(dadosSeparados[3].trim());

            Regioes regiaoFormatada = Regioes.valueOf(regiao.toUpperCase());

            Treinador treinadorEditado = Treinador.builder()
                    .id(idOriginal)
                    .nome(nome)
                    .regiao(regiaoFormatada)
                    .idade(idade)
                    .insignias(insignias)
                    .build();

            if(treinador.getEquipe() != null){
                treinadorEditado.setEquipe(treinador.getEquipe());
            }

            iTreinadorDAO.alterar(treinadorEditado);

            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // UPDATE - ADICIONA UM POKÉMON A EQUIPE DO TREINADOR
    public void adicionarPokemonAoTime(){
        MenuPokemon menuPokemon = new MenuPokemon();
        Treinador treinador = consultarTreinador();
        if (treinador == null) return;

        Pokemon pokemon = menuPokemon.consultarPokemon();
        if (pokemon == null) return;

        if(treinador.getEquipe().size() >= 6){
            JOptionPane.showMessageDialog(null, "A equipe já está cheia!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pokemon.getTreinador() != null){
            JOptionPane.showMessageDialog(null, "O Pokémon já faz parte da equipe do treinador " + pokemon.getTreinador().getNome() + "!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        iTreinadorDAO.adicionarPokemon(pokemon, treinador);

        pokemon.setTreinador(treinador);

        JOptionPane.showMessageDialog(null, pokemon.getEspeciePokemon() + " foi adicionado à equipe de " + treinador.getNome() + "!");
    }

    // DELETE - EXCLUI UM TREINADOR ESPECÍFICO
    public void excluirTreinador(){
        Treinador treinador = consultarTreinador();
        if (treinador == null){
            return;
        }

        try {
            iTreinadorDAO.excluir(treinador.getId());
            JOptionPane.showMessageDialog(null, "Treinador excluído com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}