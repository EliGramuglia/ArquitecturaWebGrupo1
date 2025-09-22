package org.example.repository.impl;

import jakarta.persistence.EntityManager;

import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Inscripcion;
import org.example.repository.InscripcionRepository;

import java.time.LocalDate;

public class InscripcionRepositoryImpl implements InscripcionRepository {
    private EntityManager em;
    private static InscripcionRepositoryImpl instance;
    private EstudianteRepositoryImpl estudiante;
    private CarreraRepositoryImpl carrera;


    private InscripcionRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    //debo pasarle por parametro los valores!
    public static InscripcionRepositoryImpl getInstance(EntityManager em,EstudianteRepositoryImpl es,CarreraRepositoryImpl cr) {
        if(instance == null) {
            instance = new InscripcionRepositoryImpl(em,es,cr);
        }
        return instance;
    }

    /* --------------------------- CRUD --------------------------- */
    @Override
    public Inscripcion create( String nombreEstudiante, String nombreCarrera ) {
        final Estudiante e = estudiante.findByNombre(nombreEstudiante);
        final Carrera c = carrera.findByNombre(nombreCarrera);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCarrera(c);
        inscripcion.setEstudiante(e);
        inscripcion.setFechaInscripcion(LocalDate.now());

        if(inscripcion.getIdInscripcion() == null){
            em.persist(inscripcion);
        } else {
            em.merge(inscripcion);
        }
        return inscripcion;
    }

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
