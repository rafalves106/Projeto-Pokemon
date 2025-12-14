/**
 * @author falvesmac
 */

package br.com.falves.menu;

import br.com.falves.builder.PokemonBuilder;
import br.com.falves.dao.IPokemonDAO;
import br.com.falves.dao.PokemonMapDAO;
import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.TipoPokemon;

import javax.swing.*;

public class MenuPokemon {
    IPokemonDAO iPokemonDAO = PokemonMapDAO.getInstance();

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
        };

        try {
            String[] dadosSeparados = dados.split(",");

            if (dadosSeparados.length < 3){
                JOptionPane.showMessageDialog(null, "Dados Insuficientes! Padrão: NÚMERO, ESPÉCIE, NÍVEL",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dadosSeparados.length > 3){
                JOptionPane.showMessageDialog(null, "Dados Excessivos! Padrão: NÚMERO, ESPÉCIE, NÍVEL",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long num = Long.parseLong(dadosSeparados[0]);
            String especieStr = dadosSeparados[1].trim().toUpperCase();
            EspeciePokemon especie = EspeciePokemon.valueOf(especieStr);
            Integer nivel = Integer.parseInt(dadosSeparados[2]);

            Pokemon pokemon = Pokemon.builder()
                    .numero(num)
                    .especie(especie)
                    .nivel(nivel)
                    .build();

            Boolean isCadastrado = iPokemonDAO.cadastrar(pokemon);

            if(isCadastrado) {
                JOptionPane.showMessageDialog(null, "Pokémon cadastrado com suceso ",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Pokémon já se encontra cadastrado",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Erro no campo numérico número).", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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
            // Transforma o valor inserido em Long
            Long num = Long.parseLong(numStr);

            // Consulta um Pokémon passando o Long
            Pokemon pokemon = iPokemonDAO.consultar(num);

            // Se o retorno for nulo, inicializa tela informando
            if(pokemon == null){
                JOptionPane.showMessageDialog(null, "Pokemon não encontrado!",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            }

            // Se encontrar um Pokémon, o retorna
            return pokemon;
        } catch (NumberFormatException e){
            // Caso não consiga converter o String para Long, inicializa tela informando e retorna nulo
            JOptionPane.showMessageDialog(null, "Número inválido! Digite apenas números.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
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
            String[] dadosSeparados = dados.split(",");

            if(dadosSeparados.length < 2){
                JOptionPane.showMessageDialog(null, "Informe: ESPÉCIE, NÍVEL",
                        "Dados Incompletos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(dadosSeparados.length > 2){
                JOptionPane.showMessageDialog(null, "Informe: ESPÉCIE, NÍVEL",
                        "Dados Excessivos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Long numOriginal = pokemon.getNumero();
            String especieStr = dadosSeparados[0].trim().toUpperCase();
            EspeciePokemon especie = EspeciePokemon.valueOf(especieStr);
            Integer nivel = Integer.parseInt(dadosSeparados[1].trim());

            PokemonBuilder builder = Pokemon.builder()
                    .numero(numOriginal)
                    .especie(especie)
                    .nivel(nivel);

            Pokemon pokemonAntigo = iPokemonDAO.consultar(numOriginal);

            if (pokemonAntigo != null && pokemonAntigo.getTreinador() != null) {
                builder.setTreinador(pokemonAntigo.getTreinador());
            }

            Pokemon pokemonAlterado = builder.build();

            iPokemonDAO.alterar(pokemonAlterado);

            JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso!");
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void excluirPokemon(){
        Pokemon pokemonParaExcluir = consultarPokemon();

        if (pokemonParaExcluir == null) {
            JOptionPane.showMessageDialog(null, "Pokémon não encontrado para exclusão.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (pokemonParaExcluir.getTreinador() != null){
            JOptionPane.showMessageDialog(null,
                    "Não é possível excluir: Este Pokémon pertence a " + pokemonParaExcluir.getTreinador().getNome() + ".\nRemova-o da equipe do treinador primeiro.",
                    "Operação Negada", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                iPokemonDAO.excluir(pokemonParaExcluir.getNumero());
                JOptionPane.showMessageDialog(null, "Pokémon excluído com sucesso!");
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, "Erro ao excluir: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void listarPokemons(){
        StringBuilder sb = new StringBuilder();
        sb.append("Listando todos os Pokémons: \n");

        iPokemonDAO.buscarTodos().forEach(pokemon -> sb.append(pokemon.toString()).append("\n"));

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void buscarPorNivel(){
        String message = "Digite o nível MÍNIMO dos Pokémons que deseja ver (1 a 100)";

        String nivelStr = JOptionPane.showInputDialog(null,
                message, "Procura de Pokémon por Nível", JOptionPane.INFORMATION_MESSAGE);

        if (nivelStr == null) return;

        try {
            int nivelMinimo = Integer.parseInt(nivelStr.trim());

            StringBuilder sb = new StringBuilder();
            sb.append("Listando todos os Pokémons de nível ").append(nivelMinimo).append(" ou maior: \n");

            if (nivelMinimo >= 1 && nivelMinimo <= 100){
                iPokemonDAO.buscarPorNivel(nivelMinimo).forEach(p -> sb.append(p.toString()).append("\n"));
                JOptionPane.showMessageDialog(null, sb.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Nível inválido! Tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
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
        };

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Listando todos os Pokémons da espécie: ").append(especieStr).append("\n");

            iPokemonDAO.buscarPorEspecie(EspeciePokemon.valueOf(especieStr)).forEach(p -> sb.append(p.toString()).append("\n"));

            JOptionPane.showMessageDialog(null, sb.toString());

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
        };

        try {
            String tipoFormatado = tipoStr.trim().substring(0, 1).toUpperCase() +
                    tipoStr.trim().substring(1).toLowerCase();

            StringBuilder sb = new StringBuilder();
            sb.append("Listando todos os Pokémons do tipo: ").append(tipoFormatado).append("\n");

            iPokemonDAO.buscarPorTipo(TipoPokemon.valueOf(tipoFormatado)).forEach(p -> sb.append(p.toString()).append("\n"));

            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Tipo não encontrado! Verifique a digitação ou use 'LISTAR'.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar: " + e.getMessage());
        }
    }

    private static void listarEspecies(){
        StringBuilder listarEspecies = new StringBuilder();
        int contador = 0;

        for (EspeciePokemon especiePokemon : EspeciePokemon.values()){
            listarEspecies.append(especiePokemon).append(" | ");
            contador++;

            if (contador % 3 == 0) listarEspecies.append("\n");
        }

        JTextArea textArea = new JTextArea(listarEspecies.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Espécies Disponíveis", JOptionPane.INFORMATION_MESSAGE);

    }

    private static void listarTipos(){
        StringBuilder listarTipos = new StringBuilder();
        int contador = 0;
        for (TipoPokemon tipoPokemon : TipoPokemon.values()){
            listarTipos.append(tipoPokemon).append(" | ");
            contador++;

            if (contador % 3 == 0) listarTipos.append("\n");
        }

        JTextArea textArea = new JTextArea(listarTipos.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));

        JOptionPane.showMessageDialog(null, scrollPane, "Tipos Disponíveis", JOptionPane.INFORMATION_MESSAGE);

    }
}