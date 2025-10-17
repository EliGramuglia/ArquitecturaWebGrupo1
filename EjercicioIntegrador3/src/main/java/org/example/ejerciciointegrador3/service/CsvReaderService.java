package org.example.ejerciciointegrador3.service;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.ejerciciointegrador3.entity.Carrera;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.example.ejerciciointegrador3.entity.Inscripcion;
import org.example.ejerciciointegrador3.entity.InscripcionId;
import org.example.ejerciciointegrador3.repository.CarreraRepository;
import org.example.ejerciciointegrador3.repository.EstudianteRepository;
import org.example.ejerciciointegrador3.repository.InscripcionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CsvReaderService {

    private final EstudianteRepository estudianteRepo;
    private final CarreraRepository carreraRepo;
    private final InscripcionRepository inscripcionRepo;

    public CsvReaderService(EstudianteRepository estudianteRepo, CarreraRepository carreraRepo, InscripcionRepository inscripcionRepo) {
        this.estudianteRepo = estudianteRepo;
        this.carreraRepo = carreraRepo;
        this.inscripcionRepo = inscripcionRepo;
    }

    @Transactional
    public void cargarDatosDesdeCsv() throws IOException {
        addCarreras();
        addEstudiantes();
        addInscripciones();
        System.out.println("✅ Datos cargados correctamente desde los CSV");
    }


    public void addEstudiantes() throws IOException {
        List<Estudiante> listEstudiantes = new ArrayList<>();

        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(
                        Objects.requireNonNull(CsvReaderService.class.getResourceAsStream("/estudiantes.csv"))
                ))) {

            for (CSVRecord record : parser) {
                try {
                    // Leer campos
                    Long dni = Long.parseLong(record.get("DNI").trim());
                    String nombre = record.get("nombre");
                    String apellido = record.get("apellido");
                    int edad = Integer.parseInt(record.get("edad").trim());
                    String genero = record.get("genero");
                    String ciudad = record.get("ciudad");
                    Integer lu = Integer.parseInt(record.get("LU").trim());

                    // Calcular fecha de nacimiento
                    LocalDate fechaNacimiento = LocalDate.now().minusYears(edad);

                    // Crear estudiante
                    Estudiante e = new Estudiante(
                            dni.intValue(), // si tu entidad usa Integer; si cambias a Long, usa dni
                            nombre,
                            apellido,
                            fechaNacimiento,
                            genero,
                            ciudad,
                            lu
                    );

                    listEstudiantes.add(e);
                } catch (Exception ex) {
                    // Si hay problema con algún registro, lo ignoramos y mostramos mensaje
                    System.out.println("Registro ignorado: " + record);
                }
            }

            // Guardar todos los estudiantes en la base
            estudianteRepo.saveAll(listEstudiantes);
            System.out.println("Estudiantes cargados: " + listEstudiantes.size());
        }
    }

    private void addCarreras() throws IOException {
        List<Carrera> listCarreras = new ArrayList<>();

        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream("/carreras.csv"))
                ))) {

            for (CSVRecord record : parser) {
                try {

                    String nombre = record.get("carrera");
                    int duracion = Integer.parseInt(record.get("duracion").trim());

                    Carrera c = new Carrera();
                    c.setNombre(nombre);
                    c.setDuracion(duracion);

                    listCarreras.add(c);
                } catch (Exception ex) {
                    System.out.println("Registro de carrera ignorado: " + record);
                }
            }

            carreraRepo.saveAll(listCarreras);
            System.out.println("🎓 Carreras cargadas: " + listCarreras.size());
        }
    }

    private void addInscripciones() throws IOException {
        List<Inscripcion> listInscripciones = new ArrayList<>();

        try (CSVParser parser = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream("/estudianteCarrera.csv"))
                ))) {

            for (CSVRecord record : parser) {
                try {
                    // Leer columnas del CSV
                    int dniEstudiante = Integer.parseInt(record.get("id_estudiante").trim());
                    int idCarrera = Integer.parseInt(record.get("id_carrera").trim());
                    int fechaInscripcion = Integer.parseInt(record.get("inscripcion").trim());
                    int fechaGraduacion = Integer.parseInt(record.get("graduacion").trim());


                    // Buscar entidades
                    Estudiante estudiante = estudianteRepo.findById(dniEstudiante)
                            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado: " + dniEstudiante));
                    Carrera carrera = carreraRepo.findById(idCarrera)
                            .orElseThrow(() -> new RuntimeException("Carrera no encontrada: " + idCarrera));

                    // Crear ID compuesto
                    InscripcionId id = new InscripcionId(dniEstudiante, idCarrera);

                    // Crear Inscripcion
                    Inscripcion inscripcion = new Inscripcion();
                    inscripcion.setIdInscripcion(id);
                    inscripcion.setEstudiante(estudiante);
                    inscripcion.setCarrera(carrera);
                    inscripcion.setFechaInscripcion(LocalDate.of(fechaInscripcion, 1, 1));
                    inscripcion.setFechaGraduacion(LocalDate.of(fechaGraduacion, 1, 1));

                    listInscripciones.add(inscripcion);

                } catch (Exception ex) {
                    System.out.println("Registro de inscripción ignorado: " + record);
                }
            }

            inscripcionRepo.saveAll(listInscripciones);
            System.out.println("📝 Inscripciones cargadas: " + listInscripciones.size());
        }
    }

}
