package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.example.dto.CarreraDTO;
import org.example.dto.EstudianteDTO;
import org.example.entity.Carrera;
import org.example.repository.CarreraRepository;

public class CarreraRepositoryImpl implements CarreraRepository {
    private EntityManagerFactory emf;
    private static CarreraRepositoryImpl instance;

    private CarreraRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static CarreraRepositoryImpl getInstance(EntityManagerFactory emf) {
        if(instance == null) {
            instance = new CarreraRepositoryImpl(emf);
        }
        return instance;
    }

    /* --------------------------- CRUD --------------------------- */
    // Create and update (preguntar si hay que dividirlo en dos metodos: crea y actualiza, o dejarlo asi)
    @Override
    public Carrera create(Carrera carrera) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if(carrera.getIdCarrera() == null){
                em.persist(carrera);
            } else {
                em.merge(carrera);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        return carrera;
    }

 /*   // Hay que hacer el find por cada uno de los atributos ???
    @Override
    public Carrera findById(Integer idCarrera) {
        return em.find(Carrera.class, idCarrera);
    }
*/
    public Carrera findByNombre(String nom) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Carrera c WHERE c.nombre = :nom", Carrera.class)
                    .setParameter("nom", nom)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // o lanzar excepción según tu lógica
        }  finally {
            em.close(); // Siempre cerramos el em
        }
    }


/*    @Override
    public void delete(Carrera carrera) {
        if(em.contains(carrera)){
            em.remove(carrera);
        } else {
            em.merge(carrera);
        }
    }*/
}
