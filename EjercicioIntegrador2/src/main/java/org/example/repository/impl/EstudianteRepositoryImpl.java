package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.dto.EstudianteDTO;
import org.example.entity.Estudiante;
import org.example.repository.EstudianteRepository;
import java.util.List;

// Hay que encerrar los metodos en un bloque try-catch

public class EstudianteRepositoryImpl implements EstudianteRepository {
    private EntityManager em;
    private static EstudianteRepositoryImpl instance;

    public EstudianteRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public static EstudianteRepositoryImpl getInstance(EntityManager em) {
        if(instance == null) {
            instance = new EstudianteRepositoryImpl(em);
        }
        return instance;
    }


    /* --------------------------- CRUD --------------------------- */
    // Create and update (preguntar si hay que dividirlo en dos metodos: crea y actualiza, o dejarlo asi)
    @Override
    public Estudiante create(Estudiante estudiante) {
        if(estudiante.getLU() == null){
            em.persist(estudiante);
        } else {
            em.merge(estudiante);
        }
        return estudiante;
    }

    //recuperar un estudiante, en base a su número de libreta universitaria.
    @Override
    public Estudiante findByLU(Integer LU) {
        return em.find(Estudiante.class, LU);
    }
//query
    @Override
    public Estudiante findByNombre(String nom) {
        try {
            return em.createQuery(
                            "SELECT e FROM Estudiante e WHERE e.nombre = :nom", Estudiante.class)
                    .setParameter("nom", nom)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    @Override
    public void delete(Estudiante estudiante) {
        if(em.contains(estudiante)){
            em.remove(estudiante);
        } else {
            em.merge(estudiante);
        }
    }
    //recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple.
    @Override
    public List<EstudianteDTO> findAllOrderByDniAsc() {
        return em.createQuery(
                "SELECT new org.example.dto.EstudianteDTO(e.LU, e.nombre, e.apellido, e.genero, e.dni) " +
                        "FROM Estudiante e ORDER BY e.dni ASC",
                EstudianteDTO.class
        ).getResultList();
    }

    //recuperar todos los estudiantes, en base a su género.
    @Override
    public List<EstudianteDTO> findAllByGenero(String gene) {
        return em.createQuery(
                "SELECT new org.example.dto.EstudianteDTO(e.LU, e.nombre, e.apellido, e.genero, e.dni) " +
                        "FROM Estudiante e WHERE e.genero = :gene",
                EstudianteDTO.class
        )
                .setParameter("gene", gene)
                .getResultList();
    }

}
