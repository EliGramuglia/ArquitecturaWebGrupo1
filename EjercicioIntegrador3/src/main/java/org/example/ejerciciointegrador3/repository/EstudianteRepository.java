package org.example.ejerciciointegrador3.repository;

import org.example.ejerciciointegrador3.dto.EstudianteCarreraDTO;
import org.example.ejerciciointegrador3.dto.response.EstudianteResponseDTO;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

// JpaRepository: el primer parámetro es la entidad y el segundo, el tipo de la PK
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {
    // Acá no hace falta implementar las querys del CRUD
    // (Spring se encarga automáticamente de save, findById, findAll, deleteById, etc.)

    // Obtiene un estudiante por su numero de libreta (LU)
    Optional<Estudiante> findByLU(Integer LU);

    // Obtiene todos los estudiantes, en base a su género
    // (ignorecase para que no importe si por ejemplo es femenino o Femenino)
    List<Estudiante> findByGeneroIgnoreCase(String genero);

    // Obtiene todos los estudiantes ordenados por su dni de forma ascendente
    List<Estudiante> findAllByOrderByDniAsc();

    // Recuperar los estudiantes de una determinada carrera, filtrando por ciudad de residencia.
    @Query("SELECT new org.example.dto.EstudianteCarreraDTO(e.LU, e.nombre, e.apellido, e.ciudadResidencia, c.nombre) "+
            "FROM Inscripcion i JOIN i.estudiante e JOIN i.carrera c "+
            "WHERE c.nombre =:carrera AND e.ciudadResidencia =:ciudad")
    List<EstudianteCarreraDTO> findAllEstudianteByCarreraAndCiudad(String carrera, String ciudad);
}
