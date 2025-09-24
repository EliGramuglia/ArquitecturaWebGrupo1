package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.example.dto.EstudianteCarreraDTO;
import org.example.dto.EstudianteDTO;
import org.example.entity.Estudiante;
import org.example.repository.EstudianteRepository;
import java.util.List;


public class EstudianteRepositoryImpl implements EstudianteRepository {
    private EntityManagerFactory emf;
    private static EstudianteRepositoryImpl instance;

    private EstudianteRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static EstudianteRepositoryImpl getInstance(EntityManagerFactory emf) {
        if(instance == null) {
            instance = new EstudianteRepositoryImpl(emf);
        }
        return instance;
    }


    /* --------------------------- CRUD --------------------------- */
    @Override
    public Estudiante create(Estudiante estudiante) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if(estudiante.getLU() == null){
                em.persist(estudiante);
            } else {
                em.merge(estudiante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
            return estudiante;
    }

    // Recuperar un estudiante, en base a su número de libreta universitaria.
    @Override
    public Estudiante findByLU(Integer LU) {
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Estudiante.class, LU);
        }  finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer LU) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Estudiante estudiante = em.find(Estudiante.class, LU);
            if (estudiante != null) {
                em.remove(estudiante);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple:
    // Se los ordena por DNI de forma ascendente
    @Override
    public List<EstudianteDTO> findAllOrderByDniAsc() {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery(
                    "SELECT new org.example.dto.EstudianteDTO(e.LU, e.nombre, e.apellido, e.genero, e.dni) " +
                            "FROM Estudiante e ORDER BY e.dni ASC",
                    EstudianteDTO.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    // Recuperar todos los estudiantes, en base a su género.
    @Override
    public List<EstudianteDTO> findAllByGenero(String gene) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT new org.example.dto.EstudianteDTO(e.LU, e.nombre, e.apellido, e.genero, e.dni) " +
                                    "FROM Estudiante e WHERE e.genero = :gene",
                            EstudianteDTO.class
                    )
                    .setParameter("gene", gene)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // Recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
    @Override
    public List<EstudianteCarreraDTO> findAllEstudianteByCarreraAndCiudad(String nombreCarrera, String ciudad) {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery("SELECT new org.example.dto.EstudianteCarreraDTO(e.LU, e.nombre, e.apellido, e.ciudadResidencia, c.nombre) "+
                                    "FROM Inscripcion i JOIN i.estudiante e JOIN i.carrera c "+
                                    "WHERE c.nombre = :carrera AND e.ciudadResidencia =:ciudad",
                                    EstudianteCarreraDTO.class
            )
            .setParameter("carrera", nombreCarrera)
                    .setParameter("ciudad", ciudad)
                    .getResultList();
        } finally {
            em.close();
        }
    }

}
