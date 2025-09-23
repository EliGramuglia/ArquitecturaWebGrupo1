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
    public Inscripcion create( String nombreEstudiante, String nombreCarrera ) {
        EntityManager em = emf.createEntityManager();
        Inscripcion inscripcion = null;
        try {
            em.getTransaction().begin();

            //Estudiante e = estudiante.findByNombre(nombreEstudiante); // venian de otra sesión de entityManager, y Hibernate no te deja acceder porque ya finalizo
            //Carrera c = carrera.findByNombre(nombreCarrera);
            Estudiante e = em.createQuery("SELECT e FROM Estudiante e WHERE e.nombre=:nom", Estudiante.class)
                    .setParameter("nom", "Pepe")
                    .getSingleResult();

            Carrera c = em.createQuery("SELECT c FROM Carrera c WHERE c.nombre=:nom", Carrera.class)
                    .setParameter("nom", "TUDAI")
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
    }
/*
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
    }*/
}
