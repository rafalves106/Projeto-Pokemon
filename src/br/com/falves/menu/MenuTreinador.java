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

        String dados = JOptionPane.showInputDialog(null, "Digite os dados(ID,NOME,REGIÃO,IDADE,INSÍGNIAS) do treinador separados por vírgula.\nEX: 1,Ash,Kanto,13,8",
                "Cadastro de Treinador", JOptionPane.INFORMATION_MESSAGE);

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
                    .equipeVazia()
                    .boxVazia()
                    .build();

            Boolean isCadastrado = iTreinadorDAO.cadastrar(treinador);

            if(isCadastrado) {
                JOptionPane.showMessageDialog(null, "Treinador cadastrado com sucesso",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Treinador já se encontra cadastrado",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null,"Erro nos campos numéricos (ID, IDADE ou INSÍGNIAS).", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Erro inesperado: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // READ - CONSULTA UM TREINADOR COM O ID ESPECÍFICO
    public Treinador consultarTreinador(){
        // Inicializa tela esperando input que irá receber o id do treinador a ser consultado
        String idStr = JOptionPane.showInputDialog(null, "Digite o id do treinador a ser consultado.",
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
                return null;
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

        if(iTreinadorDAO.buscarTodos().isEmpty()){
            JOptionPane.showMessageDialog(null, "Nenhum treinador cadastrado.\nCadastre um novo treinador.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        iTreinadorDAO.buscarTodos().forEach(treinador -> sb.append(treinador.toString()).append("\n"));

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void visualizarTime(){
        StringBuilder sb = new StringBuilder();
        Treinador treinador = consultarTreinador();

        if(treinador == null){
            return;
        }

        if (treinador.getEquipe().isEmpty()){
            JOptionPane.showMessageDialog(null, "A Equipe do Treinador está vazia.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        sb.append("Listando os Pokémons da Equipe do treinador: \n");

        treinador.getEquipe().forEach(pokemon -> sb.append(pokemon.toString()).append("\n"));

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    public void visualizarBox(){
        StringBuilder sb = new StringBuilder();
        Treinador treinador = consultarTreinador();

        if (treinador.getBox().isEmpty()){
            JOptionPane.showMessageDialog(null, "A Box do Treinador está vazia.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        sb.append("Listando os Pokémons da Box do treinador: \n");

        treinador.getBox().forEach(pokemon -> sb.append(pokemon.toString()).append("\n"));

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // UPDATE - ALTERA OS DADOS DE UM TREINADOR
    public void alterarTreinador(){
        Treinador treinador = consultarTreinador();

        if (treinador == null){
            return;
        }

        String dados = JOptionPane.showInputDialog(null, "Digite os novos dados(NOME,REGIÃO,IDADE,INSÍGNIAS) do treinador separados por vírgula.\nEditando Treinador: ",
                "Edição de Treinador", JOptionPane.INFORMATION_MESSAGE);

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

            if(treinador.getBox() != null){
                treinadorEditado.setBox(treinador.getBox());
            }

            iTreinadorDAO.alterar(treinadorEditado);

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

        // Verifica se a equipe do treinador está completa
        if(treinador.getEquipe().size() >= 6){
            JOptionPane.showMessageDialog(null, "A equipe já está cheia! Adicionando a box",
                    "Equipe Cheia", JOptionPane.INFORMATION_MESSAGE);

            // Se estiver completa, adiciona o pokemon a box do treinador que tem limite infinito
            iTreinadorDAO.adicionarNaBox(pokemon, treinador);

            // Seta o treinador do Pokémon
            pokemon.setTreinador(treinador);
            return;
        }

        // Verifica se o Pokémon já possui um treinador
        if (pokemon.getTreinador() != null){
            JOptionPane.showMessageDialog(null, "O Pokémon já faz parte da equipe do treinador " + pokemon.getTreinador().getNome() + "!",
                    "Error", JOptionPane.ERROR_MESSAGE);

            // Se possuir treinador, retorna a tela de menu
            return;
        }

        // Caso nenhum dos impedimentos barrem o Pokémon de ser adicionado ao time, ele é adicionado
        iTreinadorDAO.adicionarAoTime(pokemon, treinador);

        // Seta o treinador do Pokémon
        pokemon.setTreinador(treinador);

        JOptionPane.showMessageDialog(null, pokemon.getEspeciePokemon() + " foi adicionado à equipe de " + treinador.getNome() + "!");
    }

    // UPDATE - ADICIONA UM POKÉMON A EQUIPE DO TREINADOR
    public void removerPokemonDoTime(){
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

        // Se o pokemon não fizer parte da equipe
        if (pokemon.getTreinador() != treinador) {
            JOptionPane.showMessageDialog(null,"O Pokémon não faz parte da equipe do treinador " + pokemon.getTreinador().getNome() + "!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        iTreinadorDAO.removerDoTime(pokemon, treinador);
        iTreinadorDAO.adicionarNaBox(pokemon, treinador);

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

        // Se o pokemon não for do treinador, cancela
        if (pokemon.getTreinador() != treinador) {
            JOptionPane.showMessageDialog(null, "O Pokémon não é do treinador " + pokemon.getTreinador().getNome() + "!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // se o pokemon fazer parte da equipe e não da box, cancela
        if(treinador.getEquipe().contains(pokemon)){
            JOptionPane.showMessageDialog(null, "O Pokémon faz parte da equipe do treinador " + pokemon.getTreinador().getNome() + "! Remova-o da equipe primeiro.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // se o pokemon for da box do treinador
        pokemon.setTreinador(null);
        iTreinadorDAO.removerDaBox(pokemon, treinador);
        JOptionPane.showMessageDialog(null, pokemon.getEspeciePokemon() + " foi removido da box do " + treinador.getNome() + "!");
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