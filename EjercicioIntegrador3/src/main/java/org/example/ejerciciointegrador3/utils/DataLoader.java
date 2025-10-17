package org.example.ejerciciointegrador3.utils;

import org.example.ejerciciointegrador3.mapper.EstudianteMapper;
import org.example.ejerciciointegrador3.repository.CarreraRepository;
import org.example.ejerciciointegrador3.repository.EstudianteRepository;
import org.example.ejerciciointegrador3.repository.InscripcionRepository;
import org.example.ejerciciointegrador3.service.CsvReaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final CsvReaderService csvReaderService;
    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;
    private final InscripcionRepository inscripcionRepository;
    private final EstudianteMapper estudianteMapper;

    public DataLoader(CsvReaderService csvReaderService,
                      EstudianteRepository estudianteRepository,
                      CarreraRepository carreraRepository, InscripcionRepository inscripcionRepository,
                      EstudianteMapper estudianteMapper) {
        this.csvReaderService = csvReaderService;
        this.estudianteRepository = estudianteRepository;
        this.carreraRepository = carreraRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.estudianteMapper = estudianteMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        csvReaderService.cargarDatosDesdeCsv();

        System.out.println("Carreras cargadas: " + carreraRepository.count());
        System.out.println("Estudiantes cargados: " + estudianteRepository.count());
        System.out.println("Inscripciones cargadas: " + inscripcionRepository.count());
        System.out.println("✅ Carga de datos desde CSV completada.");
    }
}
