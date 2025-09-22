package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import org.example.dto.EstudianteDTO;
import org.example.entity.Estudiante;
import org.example.repository.EstudianteRepository;
import java.util.List;

// Hay que encerrar los metodos en un bloque try-catch

public class EstudianteRepositoryImpl implements EstudianteRepository {
    private EntityManager em;

    public EstudianteRepositoryImpl(EntityManager em) {
        this.em = em;
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

    // Hay que hacer el find por cada uno de los atributos ???
    @Override
    public Estudiante findByLU(Integer LU) {
        return em.find(Estudiante.class, LU);
    }

    @Override
    public void delete(Estudiante estudiante) {
        if(em.contains(estudiante)){
            em.remove(estudiante);
        } else {
            em.merge(estudiante);
        }
    }

    @Override
    public List<EstudianteDTO> findAllOrderByDniAsc() {
        return em.createQuery(
                "SELECT new org.example.dto.EstudianteDTO(e.nombre, e.apellido, e.genero, e.dni) " +
                        "FROM Estudiante e ORDER BY e.dni ASC",
                EstudianteDTO.class
        ).getResultList();
    }


}
