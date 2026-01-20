/**
 * @author falvesmac
 */

package br.com.falves.repository;

import br.com.falves.dao.IPokemonDAO;
import br.com.falves.domain.EspeciePokemon;
import br.com.falves.domain.Pokemon;
import br.com.falves.domain.TipoPokemon;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PokemonRepository implements IPokemonDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("projeto-pokemon");

    private EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    @Override
    public Boolean cadastrar(Pokemon pokemon) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(pokemon);
            em.getTransaction().commit();
            return true;
        } catch (Exception error){
            em.getTransaction().rollback();
            System.out.println(error.getMessage());
            return false;
        } finally {
            em.close();
        }
    }

    @Override
    public void excluir(Long numero) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Pokemon p = em.find(Pokemon.class, numero);
            if (p != null) {
                em.remove(p);
            }
            em.getTransaction().commit();
        } catch (Exception error){
            em.getTransaction().rollback();
            System.out.println(error.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void alterar(Pokemon pokemon) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(pokemon);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Pokemon consultar(Long numero) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pokemon.class, numero);
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Pokemon> buscarTodos() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pokemon p", Pokemon.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Pokemon> buscarPorTipo(TipoPokemon tipo) {
        EntityManager em = getEntityManager();
        try {
            List<EspeciePokemon> especiePokemons = Arrays.stream(EspeciePokemon.values())
                    .filter(e -> e.getTipo().equals(tipo))
                    .collect(Collectors.toList());

            if(especiePokemons.isEmpty()){
                return new ArrayList<>();
            }

            return em.createQuery("SELECT p FROM Pokemon p WHERE p.especiePokemon IN :listaEspecies", Pokemon.class)
                    .setParameter("listaEspecies", especiePokemons)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Pokemon> buscarPorNivel(int nivelMinimo) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pokemon p WHERE p.nivel >= :nivelMinimo ORDER BY p.nivel ASC", Pokemon.class)
                    .setParameter("nivelMinimo", nivelMinimo)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Pokemon> buscarPorNivel(int nivelMinimo, int nivelMaximo) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pokemon p WHERE p.nivel >= :nivelMinimo AND p.nivel <= :nivelMaximo ORDER BY p.nivel ASC", Pokemon.class)
                    .setParameter("nivelMinimo", nivelMinimo)
                    .setParameter("nivelMaximo", nivelMaximo)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Pokemon> buscarPorEspecie(EspeciePokemon especie) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Pokemon p WHERE p.especiePokemon = :especie", Pokemon.class)
                    .setParameter("especie", especie)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public void limparTabela() {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM tb_pokemon").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}