/**
 * @author falvesmac
 */

package br.com.falves.menu;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.Treinador;
import br.com.falves.service.TreinadorService;

import javax.swing.*;

public class MenuTreinador {
   TreinadorService treinadorService = new TreinadorService();

    // OPÇÕES DO MENU DE TREINADOR - CRUD
    // CREATE - CRIA UM NOVO TREINADOR
    public void cadastrarTreinador(){
        String dados = JOptionPane.showInputDialog(null, "Digite os dados...", "Cadastro", JOptionPane.INFORMATION_MESSAGE);

        if (dados == null) return;

        try {
            treinadorService.cadastrarTreinador(dados);
            JOptionPane.showMessageDialog(null, "Treinador cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro Técnico: " + e.getMessage());
        }
    }

    // READ - CONSULTA UM TREINADOR COM O ID ESPECÍFICO
    public Treinador consultarTreinador(){
        String idStr = JOptionPane.showInputDialog(null, "Digite o id do treinador:",
                "Busca de Treinador", JOptionPane.INFORMATION_MESSAGE);

        try {
            return treinadorService.consultarTreinador(idStr);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage());
            return null;
        }
    }

    // READ - LISTA TODOS OS TREINADORES
    public void listarTreinadores(){
        JOptionPane.showMessageDialog(null, treinadorService.listaTreinadores());
    }

    public void visualizarTime(){
        Treinador treinador = consultarTreinador();

        if (treinador.getEquipe().isEmpty()){
            JOptionPane.showMessageDialog(null, "A Equipe do Treinador está vazia.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, treinadorService.listaEquipe(treinador));
    }

    public void visualizarBox(){
        Treinador treinador = consultarTreinador();

        if (treinador.getBox().isEmpty()){
            JOptionPane.showMessageDialog(null, "A Box do Treinador está vazia.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, treinadorService.listaBox(treinador));
    }

    // UPDATE - ALTERA OS DADOS DE UM TREINADOR
    public void alterarTreinador(){
        Treinador treinador = consultarTreinador();

        if (treinador == null) return;

        String dados = JOptionPane.showInputDialog(null, "Digite os novos dados(NOME,REGIÃO,IDADE,INSÍGNIAS) do treinador separados por vírgula.\nEditando Treinador: ",
                "Edição de Treinador", JOptionPane.INFORMATION_MESSAGE);

        try {
            treinadorService.editarTreinador(treinador, dados);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // UPDATE - ADICIONA UM POKÉMON A EQUIPE DO TREINADOR
    public void adicionarPokemonAoTime(){
        // Instancia um menu de pokemon
        MenuPokemon menuPokemon = new MenuPokemon();

        // Chama a tela de consulta de treinador
        Treinador treinador = consultarTreinador();

        // Se o retorno do treinador for nulo ele volta a tela de menu
        if (treinador == null) return;

        // Chama a tela de consulta de pokémon
        Pokemon pokemon = menuPokemon.consultarPokemon();

        // Se o retorno do pokémon for nulo ele volta a tela de menu
        if (pokemon == null) return;

        try {
            String mensagemSucesso = treinadorService.capturarPokemon(treinador, pokemon);
            JOptionPane.showMessageDialog(null, mensagemSucesso, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage());
        }
    }

    // UPDATE - ADICIONA UM POKÉMON A EQUIPE DO TREINADOR
    public void removerPokemonDoTime(){
        MenuPokemon menuPokemon = new MenuPokemon();

        Treinador treinador = consultarTreinador();

        if (treinador == null) return;

        Pokemon pokemon = menuPokemon.consultarPokemon();

        if (pokemon == null) return;

        treinadorService.removerPokemonDoTime(treinador, pokemon);

        JOptionPane.showMessageDialog(null, pokemon.getEspeciePokemon() + " foi removido da equipe e adicionado na box de " + treinador.getNome() + "!");
    }

    // UPDATE - REMOVE UM POKEMON DA BOX DO TREINADOR "SELVAGEM"
    public void removerPokemonDaBox(){
        // Instancia um menu de pokemon
        MenuPokemon menuPokemon = new MenuPokemon();

        // Chama a tela de consulta de treinador
        Treinador treinador = consultarTreinador();

        // Se o retorno do treinador for nulo ele volta a tela de menu
        if (treinador == null) return;

        // Chama a tela de consulta de pokémon
        Pokemon pokemon = menuPokemon.consultarPokemon();

        // Se o retorno do pokémon for nulo ele volta a tela de menu
        if (pokemon == null) return;

        treinadorService.removePokemonDaBox(treinador, pokemon);
        JOptionPane.showMessageDialog(null, pokemon.getEspeciePokemon() + " foi removido da box do " + treinador.getNome() + "!");
    }

    // DELETE - EXCLUI UM TREINADOR ESPECÍFICO
    public void excluirTreinador(){
        Treinador treinador = consultarTreinador();

        if (treinador == null) return;

        try {
            treinadorService.excluirTreinador(treinador);
            JOptionPane.showMessageDialog(null, "Treinador excluído com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void acoesTreinador(){
        while (true) {
            String opcaoTreinador = JOptionPane.showInputDialog(null,
                    "1 -> Adicionar Treinador\n2 -> Editar Treinador\n3 -> Remover Treinador\n4 -> Gerenciar Pokémons\n5 -> Listar Treinadores\n6 -> Voltar",
                    "Menu - Treinador Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (opcaoTreinador == null){
                return;
            }

            switch (opcaoTreinador) {
                case "1":
                    cadastrarTreinador();
                    break;
                case "2":
                    alterarTreinador();
                    break;
                case "3":
                    excluirTreinador();
                    break;
                case "4":
                    gerenciarPokemons();
                    break;
                case "5":
                    listarTreinadores();
                    break;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    public void gerenciarPokemons(){
        while (true) {
            String opcaoFiltro = JOptionPane.showInputDialog(null,
                    "1 -> Adicionar ao Time\n2 -> Remover do Time\n3 -> Remover da Box\n4 -> Visualizar Time\n5 -> Visualizar Box\n6 -> Voltar",
                    "Menu - Gerenciar Pokémons do Treinador", JOptionPane.INFORMATION_MESSAGE);

            if (opcaoFiltro == null) {
                return;
            }

            switch (opcaoFiltro) {
                case "1":
                    adicionarPokemonAoTime();
                    break;
                case "2":
                    removerPokemonDoTime();
                    break;
                case "3":
                    removerPokemonDaBox();
                    break;
                case "4":
                    visualizarTime();
                    return;
                case "5":
                    visualizarBox();
                    return;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
}