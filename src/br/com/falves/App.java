/**
 * @author falvesmac
 */

package br.com.falves;

import br.com.falves.menu.MenuGeral;

public class App {
    private static final MenuGeral menuGeral = new MenuGeral();

    public static void main(String[] args) {
        menuGeral.acoes();
    }
}