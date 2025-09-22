package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.entity.Carrera;
import org.example.entity.Inscripcion;
import org.example.repository.InscripcionRepository;

public class InscripcionRepositoryImpl implements InscripcionRepository {
    private EntityManager em;

    public InscripcionRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    /* --------------------------- CRUD --------------------------- */
    // Create and update (preguntar si hay que dividirlo en dos metodos: crea y actualiza, o dejarlo asi)
    @Override
    public Inscripcion create(Inscripcion inscripcion) {
        if(inscripcion.getIdInscripcion() == null){
            em.persist(inscripcion);
        } else {
            em.merge(inscripcion);
        }
        return inscripcion;
    }

    // Hay que hacer el find por cada uno de los atributos ???
    @Override
    public Inscripcion findById(Integer idInscripcion) {
        return em.find(Inscripcion.class, idInscripcion);
    }

    @Override
    public void delete(Inscripcion inscripcion) {
        if(em.contains(inscripcion)){
            em.remove(inscripcion);
        } else {
            em.merge(inscripcion);
        }
    }
}
