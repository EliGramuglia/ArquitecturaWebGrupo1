package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Inscripcion;
import org.example.entity.InscripcionId;
import org.example.repository.InscripcionRepository;

import java.time.LocalDate;

public class InscripcionRepositoryImpl implements InscripcionRepository {
    private EntityManagerFactory emf;
    private static InscripcionRepositoryImpl instance;
    private EstudianteRepositoryImpl estudiante;
    private CarreraRepositoryImpl carrera;

    private InscripcionRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
        this.estudiante = EstudianteRepositoryImpl.getInstance(emf);
        this.carrera = CarreraRepositoryImpl.getInstance(emf);
    }

    public static InscripcionRepositoryImpl getInstance(EntityManagerFactory emf) {
        if(instance == null) {
            instance = new InscripcionRepositoryImpl(emf);
        }
        return instance;
    }


    /* --------------------------- CRUD --------------------------- */
    @Override
    public Inscripcion create(Integer idEstudiante, Integer idCarrera) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Estudiante e = em.find(Estudiante.class, idEstudiante);
            Carrera c = em.find(Carrera.class, idCarrera);

            Inscripcion inscripcion = new Inscripcion(e, c);

            em.persist(inscripcion);
            em.getTransaction().commit();
            return inscripcion;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public Inscripcion findById(int idCarrera, int luEstudiante) {
        EntityManager em = emf.createEntityManager();
        try {
            InscripcionId id = new InscripcionId(luEstudiante, idCarrera);
            return em.find(Inscripcion.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Inscripcion inscripcion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Inscripcion ins = em.find(Inscripcion.class, inscripcion.getIdInscripcion());
            if (ins != null) {
                em.remove(ins);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}