package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.dto.CarreraDTO;
import org.example.dto.EstudianteDTO;
import org.example.entity.Carrera;
import org.example.repository.CarreraRepository;

public class CarreraRepositoryImpl implements CarreraRepository {
    private EntityManager em;
    private static CarreraRepositoryImpl instance;

    public CarreraRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    private static CarreraRepositoryImpl getInstance(EntityManager em) {
        if(instance == null) {
            instance = new CarreraRepositoryImpl(em);
        }
        return instance;
    }

    /* --------------------------- CRUD --------------------------- */
    // Create and update (preguntar si hay que dividirlo en dos metodos: crea y actualiza, o dejarlo asi)
    @Override
    public Carrera create(Carrera carrera) {
        if(carrera.getIdCarrera() == null){
            em.persist(carrera);
        } else {
            em.merge(carrera);
        }
        return carrera;
    }

    // Hay que hacer el find por cada uno de los atributos ???
    @Override
    public Carrera findById(Integer idCarrera) {
        return em.find(Carrera.class, idCarrera);
    }

    public Carrera findByNombre(String nom) {
        try {
            return em.createQuery(
                            "SELECT c FROM Carrera c WHERE c.nombre = :nom", Carrera.class)
                    .setParameter("nom", nom)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // o lanzar excepción según tu lógica
        }
    }


    @Override
    public void delete(Carrera carrera) {
        if(em.contains(carrera)){
            em.remove(carrera);
        } else {
            em.merge(carrera);
        }
    }
}
