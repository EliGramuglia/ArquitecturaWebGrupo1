package org.example.ejerciciointegrador3.repository;

import org.example.ejerciciointegrador3.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository: el primer parámetro es la entidad y el segundo, el tipo de la PK
public interface EstudianteRepository extends JpaRepository<Estudiante, Integer> {

    // Acá no hace falta implementar las querys del CRUD
    // (Spring se encarga automáticamente de save, findById, findAll, deleteById, etc.)
}
