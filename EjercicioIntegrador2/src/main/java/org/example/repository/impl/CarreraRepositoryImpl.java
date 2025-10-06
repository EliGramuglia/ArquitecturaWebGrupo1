package org.example.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.dto.CarreraDTO;
import org.example.dto.ReporteDTO;
import org.example.entity.Carrera;
import org.example.repository.CarreraRepository;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<ReporteDTO> generarReporte() {
        EntityManager em = emf.createEntityManager();
        try {
            String sql =
                    "SELECT c.nombre AS carrera, " +
                            "       anios.anio, " +
                            "       SUM(CASE WHEN anios.tipo = 'I' THEN 1 ELSE 0 END) AS inscriptos , " +
                            "       SUM(CASE WHEN anios.tipo = 'E' THEN 1 ELSE 0 END) AS egresados " +
                            "FROM Carrera c " +
                            "JOIN Inscripcion i ON i.id_carrera = c.id_carrera " +
                            "JOIN (" +
                            "       SELECT dni, id_carrera, YEAR(fecha_inscripcion) AS anio, 'I' AS tipo " +
                            "       FROM Inscripcion WHERE fecha_inscripcion IS NOT NULL " +
                            "       AND fecha_graduacion IS NULL " +
                            "       UNION ALL " +
                            "       SELECT dni, id_carrera, YEAR(fecha_graduacion) AS anio, 'E' AS tipo " +
                            "       FROM Inscripcion WHERE fecha_graduacion IS NOT NULL " +
                            "     ) anios ON anios.dni = i.dni AND anios.id_carrera = i.id_carrera " +
                            "GROUP BY c.nombre, anios.anio " +
                            "ORDER BY c.nombre ASC, anios.anio ASC";

            List<Object[]> results = em.createNativeQuery(sql).getResultList();

            // Mapeo manual al DTO
            List<ReporteDTO> reporte = new ArrayList<>();
            for (Object[] row : results) {
                String carrera = (String) row[0];
                Integer anio = ((Number) row[1]).intValue();
                Long inscriptos = ((Number) row[2]).longValue();
                Long egresados = ((Number) row[3]).longValue();
                reporte.add(new ReporteDTO(carrera, anio, inscriptos, egresados));
            }

            return reporte;
        } finally {
            em.close();
        }
    }

}