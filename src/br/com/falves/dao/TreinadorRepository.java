/**
 * @author falvesmac
 */

package br.com.falves.dao;

import br.com.falves.domain.Treinador;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Collection;

public class TreinadorRepository implements ITreinadorDAO {
    private EntityManagerFactory emf;

    public TreinadorRepository(){
        this.emf = Persistence.createEntityManagerFactory("projeto-pokemon");
    }

    public TreinadorRepository(String persistenceUnitName){
        this.emf = Persistence.createEntityManagerFactory(persistenceUnitName);
    }

    @Override
    public Boolean cadastrar(Treinador treinador) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(treinador);
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
    public void excluir(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Treinador t = em.find(Treinador.class, id);
            if (t != null) {
                em.remove(t);
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
    public void alterar(Treinador treinador) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(treinador);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void adicionarAoTime(Treinador treinador) {
        alterar(treinador);
    }

    @Override
    public void removerDoTime(Treinador treinador) {
        alterar(treinador);

    }

    @Override
    public void adicionarNaBox(Treinador treinador) {
        alterar(treinador);
    }

    @Override
    public void removerDaBox(Treinador treinador) {
        alterar(treinador);
    }

    @Override
    public Treinador consultar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Treinador.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Collection<Treinador> buscarTodos() {
        EntityManager em = emf.createEntityManager();

        try {
            return em.createQuery("SELECT t FROM Treinador t", Treinador.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void limparTabela() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM tb_pokemon").executeUpdate();
            em.createNativeQuery("DELETE FROM tb_treinador").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}