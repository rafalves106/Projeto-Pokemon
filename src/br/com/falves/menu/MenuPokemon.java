/**
 * @author falvesmac
 */

package br.com.falves.menu;
import br.com.falves.domain.Pokemon;
import br.com.falves.service.PokemonService;

import javax.swing.*;
/**
public class MenuPokemon {
    PokemonService pokemonService = new PokemonService();

    public void cadastrarPokemon(){
        String message = "Digite os dados(NÚMERO,ESPÉCIE,NÍVEL) do Pokémon separados por vírgula.\nEX: 1,MOLTRES,60\n*Digite 'LISTAR' para ver todas as espécies*";

        String dados = JOptionPane.showInputDialog(null,
                message, "Cadastro de Pokémon", JOptionPane.INFORMATION_MESSAGE);

        if (dados == null) return;

        while(dados.trim().equalsIgnoreCase("LISTAR")){
            listarEspecies();

            dados = JOptionPane.showInputDialog(null,
                    message, "Cadastro de Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (dados == null) return;
        }

        try {
            pokemonService.cadastrarPokemon(dados);
            JOptionPane.showMessageDialog(null, "Pokémon cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro Técnico: " + e.getMessage());
        }
    }

    public Pokemon consultarPokemon(){
        // Inicializa tela esperando input que irá receber o numero do pokemon a ser consultado
        String numStr = JOptionPane.showInputDialog(null,
                "Digite o número do pokémon a ser consultado.",
                "Busca de Pokémon", JOptionPane.INFORMATION_MESSAGE);

        // Se o numero inserido for nulo ou vazio, retorna nulo
        if (numStr == null || numStr.trim().isEmpty()) {
            return null;
        }

        try {
            return pokemonService.consultarPokemon(numStr);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage());
            return null;
        }
    }

    public void alterarPokemon(){
        Pokemon pokemon = consultarPokemon();

        if (pokemon == null){
            return;
        }

        String message = "Digite os NOVOS dados (ESPÉCIE, NÍVEL) separados por vírgula." + "\nEditando Pokémon: " + pokemon.getEspeciePokemon() + " | " + pokemon.getNivel();

        String dados = JOptionPane.showInputDialog(null,
                message, "Edição de Pokémon", JOptionPane.INFORMATION_MESSAGE);

        if (dados == null) return;

        try {
            pokemonService.editarPokemon(pokemon, dados);
            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void excluirPokemon(){
        Pokemon pokemon = consultarPokemon();

        if (pokemon == null) return;

        try {
            pokemonService.excluirPokemon(pokemon);
            JOptionPane.showMessageDialog(null, "Pokémon excluído com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void listarPokemons(){
        JOptionPane.showMessageDialog(null, pokemonService.listaPokemons());
    }

    public void buscarPorNivel(){
        String message = "Digite o NÍVEL MÍNIMO c/s o NÍVEL MÁXIMO dos Pokémons que deseja ver (1 a 100)";

        String niveisStr = JOptionPane.showInputDialog(null,
                message, "Procura de Pokémon por Nível", JOptionPane.INFORMATION_MESSAGE);

        if (niveisStr == null) return;

        try {
            JOptionPane.showMessageDialog(null, pokemonService.buscarPorNivel(niveisStr));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, digite apenas números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarPorEspecie(){
        String message = "Digite a espécie do Pokémon que deseja ver (Ex: Pikachu)";

        String especieStr = JOptionPane.showInputDialog(null,
                message, "Procura por Espécie", JOptionPane.INFORMATION_MESSAGE);

        if (especieStr == null) return;

        while(especieStr.trim().equalsIgnoreCase("LISTAR")){
            listarEspecies();
            especieStr = JOptionPane.showInputDialog(null, message, "Procura por Espécie", JOptionPane.INFORMATION_MESSAGE);
            if (especieStr == null) return;
        }

        try {
            JOptionPane.showMessageDialog(null, pokemonService.buscarPorEspecie(especieStr));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Espécie não encontrada! Verifique a digitação ou use 'LISTAR'.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void buscarPorTipo(){
        String message = "Digite o tipo do Pokémon que deseja ver (Ex: Fogo, Agua)";

        String tipoStr = JOptionPane.showInputDialog(null,
                message, "Procura por Tipo", JOptionPane.INFORMATION_MESSAGE);

        if (tipoStr == null) return;

        while(tipoStr.trim().equalsIgnoreCase("LISTAR")){
            listarTipos();
            tipoStr = JOptionPane.showInputDialog(null, message, "Procura por Tipo", JOptionPane.INFORMATION_MESSAGE);
            if (tipoStr == null) return;
        }

        try {

            JOptionPane.showMessageDialog(null, pokemonService.buscarPorTipo(tipoStr));
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Tipo não encontrado! Verifique a digitação ou use 'LISTAR'.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + e.getMessage());

        }
    }

    private void listarEspecies(){
        JTextArea textArea = new JTextArea(pokemonService.listarEspecies());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Espécies Disponíveis", JOptionPane.INFORMATION_MESSAGE);

    }

    private void listarTipos(){
        JTextArea textArea = new JTextArea(pokemonService.listarTipos());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Tipos Disponíveis", JOptionPane.INFORMATION_MESSAGE);

    }

    public void acoesPokemon() {
        while (true) {
            String opcaoPokemon = JOptionPane.showInputDialog(null,
                    "1 -> Adicionar Pokémon\n2 -> Editar Pokémon\n3 -> Remover Pokémon\n4 -> Filtrar Pokémon\n5 -> Listar Pokémons\n6 -> Voltar",
                    "Menu - Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (opcaoPokemon == null) {
                return;
            }

            switch (opcaoPokemon) {
                case "1":
                    cadastrarPokemon();
                    break;
                case "2":
                    alterarPokemon();
                    break;
                case "3":
                    excluirPokemon();
                    break;
                case "4":
                    filtrarPokemon();
                    break;
                case "5":
                    listarPokemons();
                    break;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    public void filtrarPokemon(){
        while (true) {
            String opcaoFiltro = JOptionPane.showInputDialog(null,
                    "1 -> Filtrar por Tipo\n2 -> Filtrar por Nível\n3 -> Filtrar por Espécie\n4 -> Voltar",
                    "Menu - Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (opcaoFiltro == null) {
                return;
            }

            switch (opcaoFiltro) {
                case "1":
                    buscarPorTipo();
                    break;
                case "2":
                    buscarPorNivel();
                    break;
                case "3":
                    buscarPorEspecie();
                    break;
                case "4":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }
}
 **/