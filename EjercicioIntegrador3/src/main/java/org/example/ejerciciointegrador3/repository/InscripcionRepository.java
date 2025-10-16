//package org.example.ejerciciointegrador3.repository;
//
//import org.example.ejerciciointegrador3.entity.Inscripcion;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
//    Inscripcion create(Integer idEstudiante, Integer idCarrera, LocalDate fechaInscripcion, LocalDate fechaGraduacion);
//    Inscripcion findById(int idCarrera, int luEstudiante);
//    void delete(Inscripcion inscripcionId);
//
//    void saveAll(List<Inscripcion> inscripciones);
//}
