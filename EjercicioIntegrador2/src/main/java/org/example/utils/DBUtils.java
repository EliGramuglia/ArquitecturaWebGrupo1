package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.entity.Carrera;
import org.example.entity.Estudiante;
import org.example.entity.Inscripcion;
import org.example.repository.CarreraRepository;
import org.example.repository.EstudianteRepository;
import org.example.repository.InscripcionRepository;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBUtils {

    public DBUtils() {
    }

    /* ------------------------ MÉTODOS PARA CARGAR LOS CSV ----------------------- */
    public void addCarrera(CarreraRepository carrera) throws IOException {
        List<Carrera> listCarreras = new ArrayList<>();
        try (CSVParser carreras = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(DBUtils.class.getResourceAsStream("/carreras.csv"))))) {

            for (CSVRecord record : carreras) {
                String nombre = record.get("carrera");
                String duracion = record.get("duracion");

                listCarreras.add(new Carrera(nombre, Integer.valueOf(duracion)));
            }

            for (Carrera c : listCarreras) {
                carrera.create(c);
            }
        }
    }

    public void addEstudiante(EstudianteRepository estudiante) throws IOException {
        List<Estudiante> listEstudiantes = new ArrayList<>();
        try (CSVParser estudiantes = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(Objects.requireNonNull(DBUtils.class.getResourceAsStream("/estudiantes.csv"))))) {

            for (CSVRecord record : estudiantes) {
                String dni = record.get("DNI");
                String nombre = record.get("nombre");
                String apellido = record.get("apellido");
                String edad = record.get("edad");
                String genero = record.get("genero");
                String ciudad = record.get("ciudad");
                String lu = record.get("LU");

                LocalDate hoy = LocalDate.now();
                LocalDate fechaNacimiento = hoy.minusYears(Long.parseLong(edad));

                listEstudiantes.add(new Estudiante(Integer.valueOf(dni), nombre, apellido, fechaNacimiento, genero, ciudad, Integer.valueOf(lu)));
            }

            for (Estudiante e : listEstudiantes) {
                estudiante.create(e);
            }
        }
    }

    public void addInscripcion(InscripcionRepository inscripcionRepo) throws IOException {
        try (CSVParser inscripciones = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .build()
                .parse(new InputStreamReader(
                        Objects.requireNonNull(DBUtils.class.getResourceAsStream("/estudianteCarrera.csv"))
                ))) {

            for (CSVRecord record : inscripciones) {
                int idEstudiante = Integer.parseInt(record.get("id_estudiante"));
                int idCarrera = Integer.parseInt(record.get("id_carrera"));
                String inscripcionStr = record.get("inscripcion");
                String graduacionStr = record.get("graduacion");

                // Convertimos los años a LocalDate (1° de enero)
                LocalDate fechaInscripcion = LocalDate.of(Integer.parseInt(inscripcionStr), 1, 1);

                LocalDate fechaGraduacion = null;
                if (!graduacionStr.equals("0")) { // 0 significa que no se graduó
                    fechaGraduacion = LocalDate.of(Integer.parseInt(graduacionStr), 1, 1);
                }

                // Creamos la inscripción
                Inscripcion nueva = inscripcionRepo.create(idEstudiante, idCarrera, fechaInscripcion, fechaGraduacion);

                if (nueva == null) {
                    System.out.println("No se pudo crear inscripción para estudiante " + idEstudiante +
                            " y carrera " + idCarrera);
                }
            }
        }
    }
}
