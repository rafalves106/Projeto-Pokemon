/**
 * @author falvesmac
 */

package br.com.falves;
import br.com.falves.menu.MenuPokemon;
import br.com.falves.menu.MenuTreinador;

import javax.swing.*;

public class App {
    private static final MenuTreinador menuTreinador = new MenuTreinador();
    private static final MenuPokemon menuPokemon = new MenuPokemon();

    public static void main(String[] args) {
        while (true) {
            String opcao = JOptionPane.showInputDialog(null,
                    "1 -> Opções de Treinador\n2 -> Opções de Pokémon\n3 -> Sair",
                    "Menu - Projeto Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (opcao == null){
                sair();
            }

            switch (opcao) {
                case "1":
                    acoesTreinador();
                    break;
                case "2":
                    acoesPokemon();
                    break;
                case "3":
                    sair();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }


    private static void acoesTreinador(){
        while (true) {
            String opcaoTreinador = JOptionPane.showInputDialog(null,
                    "1 -> Adicionar Treinador\n2 -> Editar Treinador\n3 -> Remover Treinador\n4 -> Gerenciar Pokémons\n5 -> Listar Treinadores\n6 -> Voltar",
                    "Menu - Treinador Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (opcaoTreinador == null){
                return;
            }

            switch (opcaoTreinador) {
                case "1":
                    menuTreinador.cadastrarTreinador();
                    break;
                case "2":
                    menuTreinador.alterarTreinador();
                    break;
                case "3":
                    menuTreinador.excluirTreinador();
                    break;
                case "4":
                    gerenciarPokemons();
                    break;
                case "5":
                    menuTreinador.listarTreinadores();
                    break;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    private static void acoesPokemon() {
        while (true) {
            String opcaoPokemon = JOptionPane.showInputDialog(null,
                    "1 -> Adicionar Pokémon\n2 -> Editar Pokémon\n3 -> Remover Pokémon\n4 -> Filtrar Pokémon\n5 -> Listar Pokémons\n6 -> Voltar",
                    "Menu - Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (opcaoPokemon == null) {
                return;
            }

            switch (opcaoPokemon) {
                case "1":
                    menuPokemon.cadastrarPokemon();
                    break;
                case "2":
                    menuPokemon.alterarPokemon();
                    break;
                case "3":
                    menuPokemon.excluirPokemon();
                    break;
                case "4":
                    filtrarPokemon();
                    break;
                case "5":
                    menuPokemon.listarPokemons();
                    break;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    private static void filtrarPokemon(){
        while (true) {
            String opcaoFiltro = JOptionPane.showInputDialog(null,
                    "1 -> Filtrar por Tipo\n2 -> Filtrar por Nível\n3 -> Filtrar por Espécie\n4 -> Voltar",
                    "Menu - Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (opcaoFiltro == null) {
                return;
            }

            switch (opcaoFiltro) {
                case "1":
                    menuPokemon.buscarPorTipo();
                    break;
                case "2":
                    menuPokemon.buscarPorNivel();
                    break;
                case "3":
                    menuPokemon.buscarPorEspecie();
                    break;
                case "4":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    private static void gerenciarPokemons(){
        while (true) {
            String opcaoFiltro = JOptionPane.showInputDialog(null,
                    "1 -> Adicionar ao Time\n2 -> Remover do Time\n3 -> Remover da Box\n4 ->Visualizar Time\n5 -> Visualizar Box\n6 -> Voltar",
                    "Menu - Gerenciar Pokémons do Treinador", JOptionPane.INFORMATION_MESSAGE);

            if (opcaoFiltro == null) {
                return;
            }

            switch (opcaoFiltro) {
                case "1":
                    menuTreinador.adicionarPokemonAoTime();
                    break;
                case "2":
                    menuTreinador.removerPokemonDoTime();
                    break;
                case "3":
                    menuTreinador.removerPokemonDaBox();
                    break;
                case "4":
                    menuTreinador.visualizarTime();
                    return;
                case "5":
                    menuTreinador.visualizarBox();
                    return;
                case "6":
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
    }

    private static void sair(){
        String sair = "Finalizando sistema...";

        JOptionPane.showMessageDialog(null, sair);
        System.exit(0);
    }
}