package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.dto.CarreraDTO;
import org.example.dto.ReporteDTO;
import org.example.entity.Carrera;
import org.example.repository.CarreraRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CarreraRepositoryImpl implements CarreraRepository {
    private EntityManagerFactory emf;
    private static CarreraRepositoryImpl instance;

    private CarreraRepositoryImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static CarreraRepositoryImpl getInstance(EntityManagerFactory emf) {
        if(instance == null) {
            instance = new CarreraRepositoryImpl(emf);
        }
        return instance;
    }

    /* --------------------------- CRUD --------------------------- */
    // Create and update (preguntar si hay que dividirlo en dos metodos: crea y actualiza, o dejarlo asi)
    @Override
    public Carrera create(Carrera carrera) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if(carrera.getIdCarrera() == null){
                em.persist(carrera);
            } else {
                em.merge(carrera);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
        return carrera;
    }

    @Override
    public Carrera findById(Integer idCarrera) {
        EntityManager em = emf.createEntityManager();
        try{
            return em.find(Carrera.class, idCarrera);
        }  finally {
            em.close();
        }
    }


    @Override
    public void delete(Integer idCarrera) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Carrera carrera = em.find(Carrera.class, idCarrera);
            if (carrera != null) {
                em.remove(carrera);
            }

            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    // Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    @Override
    public List<CarreraDTO> findCarreraOrderByCantInscriptos() {
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery(
                    "SELECT new org.example.dto.CarreraDTO(c.nombre, COUNT(i)) " +
                            "FROM Carrera c JOIN c.alumnosInscriptos i "+
                            "GROUP BY c.nombre " +
                            "ORDER BY COUNT(i) DESC",
                            CarreraDTO.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    // Consultar si devolvemos cantidades o info de los estudiantes
    @Override
    public List<ReporteDTO> generarReporte() {
        EntityManager em = emf.createEntityManager();
        try {
            // Inscriptos por año
            // Para cada carrera y año de inscripción, cuento cuántos registros hay. El último parámetro se pone en 0L porque todavía no tengo egresados
            List<ReporteDTO> inscriptos = em.createQuery(
                    "SELECT new org.example.dto.ReporteDTO(c.nombre, YEAR(i.fechaInscripcion), COUNT(i), 0L) " +
                            "FROM Inscripcion i JOIN i.carrera c " +
                            "GROUP BY c.nombre, YEAR(i.fechaInscripcion) " +
                            "ORDER BY c.nombre ASC, YEAR(i.fechaInscripcion) ASC",
                    ReporteDTO.class
            ).getResultList();

            // Egresados por año
            /* Similar a la query anterior, pero ahora cuento las inscripciones con fecha de graduación no nula. El tercer parámetro se pone en 0L porque aún
            no tengo inscriptos en esta query */
            List<ReporteDTO> egresados = em.createQuery(
                    "SELECT new org.example.dto.ReporteDTO(c.nombre, YEAR(i.fechaGraduacion), 0L, COUNT(i)) " +
                            "FROM Inscripcion i JOIN i.carrera c " +
                            "WHERE i.fechaGraduacion IS NOT NULL " +
                            "GROUP BY c.nombre, YEAR(i.fechaGraduacion) " +
                            "ORDER BY c.nombre ASC, YEAR(i.fechaGraduacion) ASC",
                    ReporteDTO.class
            ).getResultList();

            // Combino resultados en un mapa donde la clave carrera_año
            Map<String, ReporteDTO> reporteMap = new LinkedHashMap<>();

            // Recorro la lista de inscriptos y los agrego al mapa. La clave permite identificar de manera única cada combinación carrera-año
            for (ReporteDTO r : inscriptos) {
                String key = r.getCarrera() + "_" + r.getAnio();
                reporteMap.put(key, r);
            }

            // Recorro la lista de egresados y los combino con los existentes en el mapa
            for (ReporteDTO r : egresados) {
                String key = r.getCarrera() + "_" + r.getAnio();
                if (reporteMap.containsKey(key)) {
                    // Si ya existe un registro para esa carrera y año, actualizo solo la cantidad de egresados
                    ReporteDTO existente = reporteMap.get(key);
                    existente.setEgresados(r.getEgresados()); // Modifico el objeto existente en el mapa directamente
                } else {
                    // Si no existe, agrego el registro de egresados como nuevo
                    reporteMap.put(key, r);
                }
            }
            // Convierto el mapa a lista para devolver la colección final de ReporteDTO. Cada elemento contiene carrera, año, cantidad de inscriptos y egresados
            return new ArrayList<>(reporteMap.values());
        } finally {
            em.close();
        }
    }

}