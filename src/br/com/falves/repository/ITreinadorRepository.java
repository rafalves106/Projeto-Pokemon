package br.com.falves.repository;

import br.com.falves.domain.Treinador;

public interface ITreinadorRepository {
    void adicionarTreinador(Treinador treinador);

    void editarTreinador(Treinador treinador);

    void deletarTreinaor(Treinador treinador);

    Treinador encontrarTreinadorPorId(Long id);
}
