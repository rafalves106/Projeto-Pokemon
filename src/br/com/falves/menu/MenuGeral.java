/**
 * @author falvesmac
 */

package br.com.falves.menu;

import javax.swing.*;
/**
public class MenuGeral {
    private static final MenuPokemon menuPokemon = new MenuPokemon();
    private static final MenuTreinador menuTreinador = new MenuTreinador();

    public void acoes() {
        while (true) {
            String opcao = JOptionPane.showInputDialog(null,
                    "1 -> Opções de Treinador\n2 -> Opções de Pokémon\n3 -> Sair",
                    "Menu - Projeto Pokémon", JOptionPane.INFORMATION_MESSAGE);

            if (opcao == null){
                sair();
            }

            switch (opcao) {
                case "1":
                    menuTreinador.acoesTreinador();
                    break;
                case "2":
                    menuPokemon.acoesPokemon();
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

    public static void sair(){
        String sair = "Finalizando sistema...";

        JOptionPane.showMessageDialog(null, sair);
        System.exit(0);
    }
}**/