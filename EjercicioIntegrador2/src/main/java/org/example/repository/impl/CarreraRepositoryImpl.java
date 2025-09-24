package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.example.dto.CarreraDTO;
import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.repository.CarreraRepository;
import java.util.List;

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

    @Override
    public Carrera findById(Integer idCarrera) {
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Carrera.class, idCarrera);
        }  finally {
            em.close();
        }
    }

    @Override
    public Carrera findByNombre(String nom) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT c FROM Carrera c WHERE c.nombre = :nom", Carrera.class)
                    .setParameter("nom", nom)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }  finally {
            em.close();
        }
    }

    @Override
    public void delete(Carrera carrera) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if (!em.contains(carrera)) {
                em.merge(carrera);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    @Override
    public List<CarreraDTO> finCarreraOrderByCantInscriptos() {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery(
                    "SELECT new org.example.dto.CarreraDTO(c.nombre, COUNT(i)) " +
                            "FROM Carrera c JOIN c.alumnosInscriptos i "+
                            "GROUP BY c.nombre " +
                            "ORDER BY COUNT(i) DESC",
                            CarreraDTO.class
            ).getResultList();
        } finally {
            em.close();
        }
    }
}
