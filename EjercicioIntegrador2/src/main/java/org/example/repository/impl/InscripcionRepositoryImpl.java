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
   /* @Override
    public Inscripcion create(Integer dniEstudiante, String nombreCarrera) {
        EntityManager em = emf.createEntityManager();
        Inscripcion inscripcion = null;
        try {
            em.getTransaction().begin();

            Estudiante e = em.createQuery("SELECT e FROM Estudiante e WHERE e.dni=:dni", Estudiante.class)
                    .setParameter("dni", dniEstudiante)
                    .getSingleResult();

            Carrera c = em.createQuery("SELECT c FROM Carrera c WHERE c.nombre=:nom", Carrera.class)
                    .setParameter("nom", nombreCarrera)
                    .getSingleResult();

            inscripcion = new Inscripcion();
            inscripcion.setEstudiante(e);
            inscripcion.setCarrera(c);
            inscripcion.setFechaInscripcion(LocalDate.now());
            inscripcion.setIdInscripcion(new InscripcionId(c.getIdCarrera(), e.getLU()));

            em.persist(inscripcion); // persist directamente, merge no es necesario aquí

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
    }*/

    @Override
    public Inscripcion create(Estudiante e, Carrera c) {
        EntityManager em = emf.createEntityManager();
        Inscripcion inscripcion = new Inscripcion();
        InscripcionId insId = new InscripcionId();
        inscripcion.setCarrera(c);
        inscripcion.setEstudiante(e);
        inscripcion.setFechaInscripcion(LocalDate.now());
        insId.setLU(e.getLU());
        insId.setIdCarrera(c.getIdCarrera());

        inscripcion.setIdInscripcion(insId);
        try {
            em.getTransaction().begin();
            if(c.getIdCarrera() == null && e.getLU() == null){
                em.persist(inscripcion);
            } else {
                em.merge(inscripcion);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        return inscripcion;
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