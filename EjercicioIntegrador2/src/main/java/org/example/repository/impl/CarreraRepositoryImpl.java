package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.entity.Carrera;
import org.example.repository.CarreraRepository;

public class CarreraRepositoryImpl implements CarreraRepository {
    private EntityManager em;

    public CarreraRepositoryImpl(EntityManager em) {
        this.em = em;
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

    @Override
    public void delete(Carrera carrera) {
        if(em.contains(carrera)){
            em.remove(carrera);
        } else {
            em.merge(carrera);
        }
    }
}
