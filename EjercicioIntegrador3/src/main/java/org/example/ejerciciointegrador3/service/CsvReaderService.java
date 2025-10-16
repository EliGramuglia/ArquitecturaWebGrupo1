package org.example.ejerciciointegrador3.service;

import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.ejerciciointegrador3.entity.Estudiante;
import org.example.ejerciciointegrador3.repository.EstudianteRepository;
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

    public CsvReaderService(EstudianteRepository estudianteRepo) {
        this.estudianteRepo = estudianteRepo;
    }

    @Transactional
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
}
