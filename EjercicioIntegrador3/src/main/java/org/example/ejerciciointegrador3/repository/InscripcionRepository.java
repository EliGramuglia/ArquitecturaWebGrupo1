package org.example.ejerciciointegrador3.repository;

import org.example.ejerciciointegrador3.entity.Inscripcion;
import org.example.ejerciciointegrador3.entity.InscripcionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, InscripcionId> {

}
